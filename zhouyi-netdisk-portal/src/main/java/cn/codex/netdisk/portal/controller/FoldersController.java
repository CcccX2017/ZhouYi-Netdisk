package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.service.IFoldersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 目录表 前端控制器
 * </p>
 *
 * @author codex
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/folders")
@Api(tags = "文件夹管理")
public class FoldersController {
    
    @Autowired
    private IFoldersService foldersService;
    
    @ApiOperation("获取文件夹列表")
    @GetMapping("/{parentId}")
    public ServerResponse<List<Folders>> getFolders(@PathVariable Long parentId) {
        List<Folders> folders;
        if (parentId == 0L) {
            folders =
                    foldersService.list(new QueryWrapper<Folders>().eq(Folders.PARENT_ID, parentId).eq(Folders.CREATOR, SecurityUtil.getUsername()));
        } else {
            folders =
                    foldersService.list(new QueryWrapper<Folders>().eq(Folders.PARENT_ID, parentId));
        }
        return ServerResponse.createBySuccess(folders);
    }
    
    @ApiOperation("新建文件夹")
    @PostMapping("/")
    public ServerResponse addFolder(@RequestBody Folders folders) {
        return foldersService.addFolder(folders);
    }
    
    @ApiOperation("重命名文件夹")
    @PutMapping("/{folderId}")
    public ServerResponse rename(@PathVariable Long folderId, Long parentId, String newFolderName) {
        return foldersService.rename(folderId, parentId, newFolderName);
    }
}

