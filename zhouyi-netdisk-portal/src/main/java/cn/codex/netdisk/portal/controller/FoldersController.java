package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
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
    @GetMapping("/")
    public ServerResponse<List<Folders>> getFolders(String dir) {
        if (Strings.isNullOrEmpty(dir)) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Folders> list =
                foldersService.list(new QueryWrapper<Folders>().eq(Folders.DIR, dir.trim()).eq(Folders.CREATOR,
                        SecurityUtil.getUsername()));
        return ServerResponse.createBySuccess(list);
    }

    @ApiOperation("新建文件夹")
    @PostMapping("/")
    public ServerResponse addFolder(@RequestBody Folders folders) {
        return foldersService.addFolder(folders.getFolderName(), folders.getDir());
    }

    @ApiOperation("移动文件夹")
    @PostMapping("/move")
    public ServerResponse move(Long[] folderId, String dir) {
        return foldersService.move(folderId, dir);
    }
}

