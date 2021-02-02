package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.FilesMapper;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

}
