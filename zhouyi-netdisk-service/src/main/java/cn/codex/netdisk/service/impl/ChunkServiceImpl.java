package cn.codex.netdisk.service.impl;

import cn.codex.netdisk.dao.ChunkMapper;
import cn.codex.netdisk.model.entity.Chunk;
import cn.codex.netdisk.service.IChunkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 存储已经上传的分片 服务实现类
 * </p>
 *
 * @author CodeX
 * @since 2021-05-11 11:07:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChunkServiceImpl extends ServiceImpl<ChunkMapper, Chunk> implements IChunkService {
}
