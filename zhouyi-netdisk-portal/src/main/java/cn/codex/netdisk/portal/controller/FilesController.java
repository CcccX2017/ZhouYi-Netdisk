package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/files")
@Api(tags = "文件资源管理")
public class FilesController {
    
    @Autowired
    private IFilesService filesService;

    @ApiOperation(value = "获取文件列表")
    @GetMapping("/{fileType}")
    public ServerResponse getList(@PathVariable String fileType, FolderAndFileQueryDto dto) {
        return filesService.getList(fileType, dto);
    }
}