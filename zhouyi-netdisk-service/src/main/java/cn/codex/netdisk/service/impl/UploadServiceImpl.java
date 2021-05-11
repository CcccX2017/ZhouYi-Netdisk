package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.LoginUser;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.dao.UserMapper;
import cn.codex.netdisk.model.dtos.UploadDto;
import cn.codex.netdisk.model.entity.User;
import cn.codex.netdisk.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

        if (dto.getTotalChunks() == 1) {
            // 判断文件大小是否超过剩余空间
            if (isExcessStorageSpace(dto.getTotalSize())) {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.FILE_SIZE_OUT_OF_LIMIT.getCode(),
                        ReturnMessage.INSUFFICIENT_SPACE);
            }

            // 更新已用空间
            updateUsedStorageSpace(dto.getTotalSize());
            return ServerResponse.createBySuccessMessage("上传成功");
        } else {

        }

        return null;
    }

    /**
     * 判断文件大小是否超过剩余空间
     *
     * @param size 文件大小
     * @return true - 超过， false - 未超过
     */
    private boolean isExcessStorageSpace(Long size) {
        Long usedStorageSpace = SecurityUtil.getLoginUser().getUser().getUsedStorageSpace();
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
        if (!file.exists()){
            file.mkdirs();
        }

        return storeDirectory + dir;
    }
}
