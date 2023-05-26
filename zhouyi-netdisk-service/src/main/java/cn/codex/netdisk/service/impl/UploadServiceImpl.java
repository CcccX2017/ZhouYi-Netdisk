package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FilesMapper;
import cn.codex.netdisk.dao.FoldersMapper;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.dtos.UploadDto;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IUploadService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * 上传文件服务实现类
 *
 * @author CodeX
 * @since 2021-05-11 15:03:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UploadServiceImpl implements IUploadService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private FilesMapper filesMapper;

    /**
     * 文件上传
     *
     * @param file           文件
     * @param dto            文件上传数据传输对象
     * @param storeDirectory 文件要存放的根目录
     * @return 上传结果
     */
    @Override
    public ServerResponse upload(MultipartFile file, UploadDto dto, String storeDirectory) {
        // 判断文件大小是否超过最大上传大小
        Long maxFileSize = SecurityUtil.getLoginUser().getUser().getUserGroups().getMaxFileSize();
        if (dto.getTotalSize() > maxFileSize) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.FILE_SIZE_OUT_OF_LIMIT.getCode(),
                    ResponseCode.FILE_SIZE_OUT_OF_LIMIT.getDesc());
        }
        
        if (uploadToDisk(file, dto, storeDirectory)) {
            // 判断用户是否有足够的可用空间
            if (isExcessStorageSpace(dto.getTotalSize())) {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.FILE_SIZE_OUT_OF_LIMIT.getCode(), ReturnMessage.INSUFFICIENT_SPACE);
            }
            
            // 更新已用空间
            updateUsedStorageSpace(dto.getTotalSize());
            
            // 文件扩展名 不带 "."
            String extName = FileNameUtil.extName(file.getOriginalFilename());
            
            // 新文件名
            String newFileName = dto.getIdentifier() + "." + extName;
            
            String path = storeDirectory + genericPath(newFileName, storeDirectory);
            
            // 将文件信息保存到数据库中
            Files files = new Files();
            files.setRealName(dto.getFilename());
            files.setEncryptionName(newFileName);
            files.setStoragePath(genericPath(newFileName, storeDirectory) + "/" + newFileName);
            files.setExtension(extName);
            files.setShortUrl("");
            files.setSize(dto.getTotalSize());
            try {
                files.setFileType(FileUtil.getFileType(new FileInputStream(new File(path, newFileName)), dto.getFilename()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            files.setIcon(FileUtil.getFileIcon(extName));
            files.setDir(dto.getTargetPath());
            files.setHidden(false);
            files.setDeleted(false);
            files.setCreator(SecurityUtil.getUsername());
            filesMapper.insert(files);
        }
    
        return ServerResponse.createBySuccessMessage(ReturnMessage.UPLOAD_SUCCESS);
    }
    
    /**
     * 秒传
     *
     * @param files    文件表
     * @param filename 文件名
     * @param targetDir 目标目录
     */
    @Override
    public boolean skipUpload(Files files, String filename, String targetDir) {
        if (isExcessStorageSpace(files.getSize())){
            return false;
        }

        // 判断目标目录下是否有相同文件名的文件
        QueryWrapper<Files> queryWrapper = new QueryWrapper<Files>().eq(Files.REAL_NAME, filename).eq(Files.DIR, targetDir);
        if (filesMapper.exists(queryWrapper)) {
            String extName = FileNameUtil.extName(filename);
            String suffix = DateUtil.format(new Date(), "_yyyyMMdd_HHmmss");
            files.setRealName(FileNameUtil.mainName(filename) + suffix + "." + extName);
        } else {
            files.setRealName(filename);
        }

        files.setFileId(null);
        files.setGmtCreate(null);
        files.setGmtModified(null);
        files.setDir(targetDir);
        filesMapper.insert(files);
        // 更新已用空间
        updateUsedStorageSpace(files.getSize());
        return true;
    }
    
    /**
     * 将文件写道磁盘
     *
     * @param file           文件
     * @param dto            文件上传数据传输对象
     * @param storeDirectory 文件要存放的根目录
     */
    private boolean uploadToDisk(MultipartFile file, UploadDto dto, String storeDirectory) {
        OutputStream os = null;
        try {
            // 文件扩展名 不带 "."
            String extName = FileNameUtil.extName(file.getOriginalFilename());
            
            // 新文件名
            String newFileName = dto.getIdentifier() + "." + extName;
            
            // 计算文件的存放目录
            String path = storeDirectory + genericPath(newFileName, storeDirectory);
            
            File targetFile = new File(path, newFileName);
            
            if (dto.getTotalChunks() == 1) {
                if (!targetFile.exists()){
                    file.transferTo(targetFile);
                }
                return true;
            } else {
                String tempPath = storeDirectory + "/tempDir/" + dto.getIdentifier();
                File tempDir = new File(tempPath);
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
                
                for (int i = 0; i < dto.getTotalChunks(); i++) {
                    String tempFileName = dto.getChunkNumber() + "_" + dto.getIdentifier() + ".part";
                    File tempFile = new File(tempPath, tempFileName);
                    if (!tempFile.exists()) {
                        file.transferTo(tempFile);
                    }
                }
                if (dto.getChunkNumber().equals(dto.getTotalChunks())) {
                    os = new BufferedOutputStream(new FileOutputStream(targetFile));
                    for (int i = 0; i < dto.getTotalChunks(); i++) {
                        String tempFileName = (i + 1) + "_" + dto.getIdentifier() + ".part";
                        File tempFile = new File(tempPath, tempFileName);
                        while (!tempFile.exists()) {
                            Thread.sleep(100);
                        }
                        byte[] bytes = FileUtils.readFileToByteArray(tempFile);
                        os.write(bytes);
                        os.flush();
                        tempFile.delete();
                    }
                    os.flush();
                    tempDir.delete();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     * 判断文件大小是否超过剩余空间
     *
     * @param size 文件大小
     * @return true - 超过， false - 未超过
     */
    private boolean isExcessStorageSpace(Long size) {
        User user = userMapper.selectByUsername(SecurityUtil.getUsername());
        Long usedStorageSpace = user.getUsedStorageSpace();
        Long maxStorageSpace = SecurityUtil.getLoginUser().getUser().getUserGroups().getMaxStorageSpace();
        return (usedStorageSpace + size) > maxStorageSpace;
    }
    
    /**
     * 更新已用空间
     *
     * @param size 文件大小
     */
    private void updateUsedStorageSpace(Long size) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        User user = userMapper.selectById(loginUser.getUser().getUserId());
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setUsedStorageSpace(user.getUsedStorageSpace() + size);
        userMapper.updateById(updateUser);
    }
    
    /**
     * 计算文件的存放目录
     *
     * @param fileName 处理后的唯一的文件名
     * @return 存放目录
     */
    private String genericPath(String fileName, String storeDirectory) {
        int hashCode = fileName.hashCode();
        // 一级目录
        int dir1 = hashCode & 0xf;
        // 二级目录
        int dir2 = (hashCode & 0xf0) >> 4;
        
        String dir = "/" + dir1 + "/" + dir2;
        
        File file = new File(storeDirectory, dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        
        return dir;
    }
}
