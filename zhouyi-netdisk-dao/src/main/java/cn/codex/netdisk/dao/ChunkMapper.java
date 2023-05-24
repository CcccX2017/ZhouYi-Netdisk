package cn.codex.netdisk.dao;

import cn.codex.netdisk.model.entity.Chunk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 存储已经上传的分片 Mapper 接口
 * </p>
 * @author CodeX
 * @since 2021-05-11 11:05:10
 */
@Mapper
public interface ChunkMapper extends BaseMapper<Chunk> {
}
