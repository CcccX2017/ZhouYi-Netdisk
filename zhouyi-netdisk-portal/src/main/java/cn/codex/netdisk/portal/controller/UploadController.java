package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.dtos.UploadDto;
import cn.codex.netdisk.model.entity.Chunk;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.portal.config.PortalConfig;
import cn.codex.netdisk.service.IChunkService;
import cn.codex.netdisk.service.IFilesService;
import cn.codex.netdisk.service.IUploadService;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件上传控制器
 *
 * @author CodeX
 * @since 2021-05-11 10:22:12
 */
@RestController
@RequestMapping("/portal/upload")
@Api(tags = "文件上传")
public class UploadController {
    
    @Autowired
    private IFilesService filesService;
    
    @Autowired
    private IChunkService chunkService;
    
    @Autowired
    private IUploadService uploadService;
    
    @ApiOperation(value = "上传文件")
    @PostMapping("/")
    public ServerResponse upload(MultipartFile file, UploadDto dto) {
        return uploadService.upload(file, dto, PortalConfig.getUploadPath());
    }
    
    @ApiOperation(value = "上传文件")
    @GetMapping("/")
    public ServerResponse upload(UploadDto dto) {
        
        String extName = FileNameUtil.extName(dto.getFilename());
        
        // 校验文件是否已经存在
        List<Files> filesList = filesService.list(new QueryWrapper<Files>().eq(Files.ENCRYPTION_NAME,
                dto.getIdentifier() + "." + extName));
        Map<String, Object> map = Maps.newHashMap();
        if (filesList.size() > 0) {
            // 文件已经完全上传，秒传
            Files files = filesList.get(0);
            map.put("skipUpload", true);
            if (uploadService.skipUpload(files, dto.getFilename())) {
                map.put("url", files.getStoragePath());
            } else {
                map.put("msg", ReturnMessage.INSUFFICIENT_SPACE);
            }
        } else {
            // 文件还未完全，获取已经上传的分片
            List<Chunk> list = chunkService.list(new QueryWrapper<Chunk>().eq(Chunk.MD5, dto.getIdentifier()));
            if (list.size() > 0) {
                // 存在已经上传的分片，返回给前端
                map.put("skipUpload", false);
                List<Integer> chunks = list.stream().map(Chunk::getChunk).collect(Collectors.toList());
                map.put("uploaded", chunks);
            } else {
                // 全新的文件，从头开始上传
                map.put("skipUpload", false);
            }
        }
        return ServerResponse.createBySuccess(map);
    }
}
