package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.FileAndFolderMapper;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFileAndFolderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件和文件夹
 *
 * @author codex
 * @since 2021-03-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileAndFolderServiceImpl implements IFileAndFolderService {

    @Autowired
    private FileAndFolderMapper fileAndFolderMapper;

    /**
     * 获取文件夹和文件列表
     *
     * @param username 用户名
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    @Override
    public IPage<FolderAndFileVo> getList(String username, FolderAndFileQueryDto dto) {
        Page<FolderAndFileVo> page = new Page<>(dto.getPage(), dto.getLimit());
        return fileAndFolderMapper.getFolderAndFileVo(page, username, dto);
    }

    /**
     * 条件查询
     *
     * @param username 用户名
     * @param dto      文件夹和文件查询实体类
     * @return 查询结果
     */
    @Override
    public IPage<FolderAndFileVo> search(String username, FolderAndFileQueryDto dto) {
        Page<FolderAndFileVo> page = new Page<>(dto.getPage(), dto.getLimit());
        return fileAndFolderMapper.search(page, username, dto);
    }
}
