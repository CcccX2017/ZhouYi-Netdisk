package cn.codex.netdisk.service;

import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.dtos.FileCopyDto;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
public interface IFilesService extends IService<Files> {
    
    /**
     * 复制到
     *
     * @param fileId      被复制的文件ID
     * @param fileCopyDto 文件复制数据传输对象
     * @return 复制结果
     */
    ServerResponse copy(Long fileId, FileCopyDto fileCopyDto);
    
    /**
     * 重命名文件
     *
     * @param fileId        文件ID
     * @param fileRenameDto 文件重命名数据传输对象
     * @return 重命名结果
     */
    ServerResponse rename(Long fileId, FileRenameDto fileRenameDto);
    
    /**
     * 获取文件列表
     *
     * @param fileType 文件类型
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    ServerResponse getList(String fileType, FolderAndFileQueryDto dto);
}
