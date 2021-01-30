package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.dao.FoldersMapper;
import cn.codex.netdisk.service.IFoldersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 目录表 服务实现类
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@Service
public class FoldersServiceImpl extends ServiceImpl<FoldersMapper, Folders> implements IFoldersService {

}
