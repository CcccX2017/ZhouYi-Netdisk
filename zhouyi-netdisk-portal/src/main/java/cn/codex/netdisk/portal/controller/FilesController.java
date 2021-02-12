package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


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

    @ApiOperation(value = "查询所有文件")
    @GetMapping("/")
    public ServerResponse findAll(){
        return ServerResponse.createBySuccess(filesService.list());
    }
    
}

