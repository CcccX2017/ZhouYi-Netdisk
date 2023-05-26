package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.dtos.UploadDto;
import cn.codex.netdisk.model.entity.Files;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件服务类
 *
 * @author CodeX
 * @since 2021-05-11 15:02:42
 */
public interface IUploadService {
    
    /**
     * 文件上传
     *
     * @param file           文件
     * @param dto            文件上传数据传输对象
     * @param storeDirectory 文件要存放的根目录
     * @return 上传结果
     */
    ServerResponse upload(MultipartFile file, UploadDto dto, String storeDirectory);
    
    /**
     * 秒传
     *
     * @param files    文件表
     * @param filename 文件名
     * @param targetDir 目标目录
     *
     * @return 是否能进行秒传 true - 是， false - 否
     */
    boolean skipUpload(Files files, String filename, String targetDir);
}
