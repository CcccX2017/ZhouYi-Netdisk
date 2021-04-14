package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.FilesMapper;
import cn.codex.netdisk.model.dtos.FileCopyDto;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.dtos.PageResult;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFilesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {
    
    /**
     * 复制到
     *
     * @param fileId      被复制的文件ID
     * @param fileCopyDto 文件复制数据传输对象
     * @return 复制结果
     */
    @Override
    public ServerResponse copy(Long fileId, FileCopyDto fileCopyDto) {
        if (Strings.isNullOrEmpty(fileCopyDto.getPath())) {
            throw new ErrorException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        
        // 根据ID查询文件信息
        Files files = baseMapper.selectById(fileId);
        if (files == null) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILE_NOT_EXIST);
        }
        files.setFileId(null);
        files.setDir(fileCopyDto.getPath());
        // 第一次尝试复制
        if (Strings.isNullOrEmpty(fileCopyDto.getType())) {
            return firstCopy(fileCopyDto, files);
        } else {
            // 出现同名文件时，用户选择替换或保留两者之后的第二次尝试复制
            return retryCopy(fileCopyDto, files);
        }
    }
    
    /**
     * 重命名文件
     *
     * @param fileId        文件ID
     * @param fileRenameDto 文件重命名数据传输对象
     * @return 重命名结果
     */
    @Override
    public ServerResponse rename(Long fileId, FileRenameDto fileRenameDto) {
        // 第一次重命名
        if (Strings.isNullOrEmpty(fileRenameDto.getType())) {
            return firstRename(fileId, fileRenameDto);
        } else if (Const.NEW_COPY.equals(fileRenameDto.getType())) {
            // 出现同名文件时，用户选择保留两者之后的第二次尝试重命名
            return retryRename(fileId, fileRenameDto);
        } else {
            return ServerResponse.createByErrorMessage(ReturnMessage.ILLEGAL_REQUEST);
        }
    }


    /**
     * 获取文件列表
     *
     * @param fileType 文件类型
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    @Override
    public ServerResponse getList(String fileType, FolderAndFileQueryDto dto) {
        if (dto == null) {
            throw new CustomException(ReturnMessage.ILLEGAL_REQUEST);
        }
        
        Page<Files> page = new Page<>(dto.getPage(), dto.getLimit());
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq(Files.DIR, dto.getDir())
                .eq(Files.CREATOR, SecurityUtil.getUsername())
                .eq(Files.FILE_TYPE, fileType);
        if (dto.getDesc() == 1) {
            wrapper.orderByDesc(dto.getOrder());
        } else {
            wrapper.orderByAsc(dto.getOrder());
        }
        Page<Files> filesPage = baseMapper.selectPage(page, wrapper);
        List<FolderAndFileVo> list = Lists.newArrayList();
        filesPage.getRecords().forEach(files -> {
            FolderAndFileVo folderAndFileVo = new FolderAndFileVo();
            folderAndFileVo.setId(files.getFileId());
            folderAndFileVo.setName(files.getRealName());
            folderAndFileVo.setSize(files.getSize());
            folderAndFileVo.setSizeStr(FileUtil.byteCountToDisplaySize(files.getSize()));
            folderAndFileVo.setIsDir(0);
            folderAndFileVo.setGmtModified(files.getGmtModified());
            if ("/".equals(dto.getDir())) {
                folderAndFileVo.setPath(dto.getDir() + files.getRealName());
            } else {
                folderAndFileVo.setPath(dto.getDir() + "/" + files.getRealName());
            }
            list.add(folderAndFileVo);
        });
        PageResult pageResult = new PageResult();
        pageResult.setList(list);
        // 已加载数 = 当前查询到的记录数 + 之前已经加载的记录数 (page - 1) * limit
        pageResult.setCount(filesPage.getRecords().size() + (int) ((dto.getPage() - 1) * dto.getLimit()));
        // 是否全部加载 = 已加载数 == 总记录数 ? 1 : 0
        pageResult.setIsAll(pageResult.getCount() == filesPage.getTotal() ? 1 : 0);
        return ServerResponse.createBySuccess(pageResult);
    }
    
    /**
     * 出现同名文件时，用户选择保留两者之后的第二次尝试重命名
     */
    private ServerResponse<?> retryRename(Long fileId, FileRenameDto fileRenameDto) {
        // 获取重名的最大数，如123.zip 123(3).zip 则返回3
        Integer count = baseMapper.selectRepeatNameCount(fileRenameDto.getNewName(), fileRenameDto.getDir(),
                SecurityUtil.getUsername());
        if (count == null) {
            count = 0;
        }
        Files files = new Files();
        files.setFileId(fileId);
        String newName = fileRenameDto.getNewName().substring(0, fileRenameDto.getNewName().lastIndexOf('.'));
        String suffix = fileRenameDto.getNewName().substring(fileRenameDto.getNewName().lastIndexOf('.'));
        files.setRealName(newName + "(" + (count + 1) + ")" + suffix);
        // 重命名文件
        return baseMapper.updateById(files) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.RENAME_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
    }
    
    /**
     * 第一次重命名
     */
    private ServerResponse<?> firstRename(Long fileId, FileRenameDto fileRenameDto) {
        // 判断当前文件夹下是否有重名文件
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Files.DIR, fileRenameDto.getDir()).eq(Files.REAL_NAME, fileRenameDto.getNewName()).eq(Files.CREATOR, SecurityUtil.getUsername()).ne(Files.FILE_ID, fileId);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            // 存在重名文件，提示用户是否保留两者
            return ServerResponse.createByErrorCodeMeaage(ResponseCode.FOLDER_NAME_REPEAT.getCode(),
                    ResponseCode.FOLDER_NAME_REPEAT.getDesc());
        }
        // 不存在重名文件，重命名文件
        Files files = new Files();
        files.setFileId(fileId);
        files.setRealName(fileRenameDto.getNewName());
        return baseMapper.updateById(files) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.RENAME_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.RENAME_ERROR);
    }
    
    /**
     * 出现同名文件时，用户选择替换或保留两者之后的第二次尝试复制
     */
    private ServerResponse<?> retryCopy(FileCopyDto fileCopyDto, Files files) {
        switch (fileCopyDto.getType()) {
            case Const.NEW_COPY:
                // 保留两者
                // 获取重名的最大数，如123.zip 123(3).zip 则返回3
                Integer count = baseMapper.selectRepeatNameCount(files.getRealName(), fileCopyDto.getPath(),
                        SecurityUtil.getUsername());
                if (count == null) {
                    count = 0;
                }
                files.setRealName(files.getRealName() + "(" + (count + 1) + ")");
                // 复制文件
                return baseMapper.insert(files) > 0
                        ? ServerResponse.createBySuccessMessage(ReturnMessage.COPY_FILE_SUCCESS)
                        : ServerResponse.createByErrorMessage(ReturnMessage.COPY_FILE_ERROR);
            case Const.OVERWRITE:
                // 替换同名文件
                // 物理删除重名文件
                baseMapper.physicalDelete(files.getRealName(), fileCopyDto.getPath(), SecurityUtil.getUsername());
                // 复制文件
                return baseMapper.insert(files) > 0
                        ? ServerResponse.createBySuccessMessage(ReturnMessage.COPY_FILE_SUCCESS)
                        : ServerResponse.createByErrorMessage(ReturnMessage.COPY_FILE_ERROR);
            default:
                return ServerResponse.createByErrorMessage(ReturnMessage.ILLEGAL_REQUEST);
        }
    }
    
    /**
     * 第一次复制
     */
    private ServerResponse<?> firstCopy(FileCopyDto fileCopyDto, Files files) {
        // 判断目标目录下是否有同名文件存在
        Files selectOne = baseMapper.selectOne(new QueryWrapper<Files>().eq(Files.REAL_NAME, files.getRealName()).eq(Files.DIR,
                fileCopyDto.getPath()));
        if (selectOne != null) {
            // 存在同名文件，保存失败，并返回同名文件信息
            return ServerResponse.createByError(ReturnMessage.COPY_FILE_ERROR, selectOne);
        }
        
        // 不存在同名文件
        return baseMapper.insert(files) > 0
                ? ServerResponse.createBySuccessMessage(ReturnMessage.COPY_FILE_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.COPY_FILE_ERROR);
    }
}
