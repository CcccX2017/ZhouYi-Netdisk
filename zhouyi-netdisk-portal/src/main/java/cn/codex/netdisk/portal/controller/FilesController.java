package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.model.dtos.FileCopyDto;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.service.IFilesService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    
    // TODO 上传文件、下载文件、在线预览
    
    @ApiOperation(value = "获取文件列表")
    @GetMapping("/{fileType}")
    public ServerResponse getList(@PathVariable String fileType, FolderAndFileQueryDto dto) {
        return filesService.getList(fileType, dto);
    }
    
    @ApiOperation(value = "移动文件")
    @PostMapping("/remove/{fileId}")
    public ServerResponse remove(@PathVariable Long fileId, String dir) {
        if (Strings.isNullOrEmpty(dir)) {
            throw new ErrorException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        
        Files files = new Files();
        files.setFileId(fileId);
        files.setDir(dir);
        
        return filesService.updateById(files)
                ? ServerResponse.createBySuccessMessage(ReturnMessage.MOVE_FOLDER_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.MOVE_FOLDER_ERROR);
    }
    
    @ApiOperation(value = "复制文件")
    @PostMapping("/copy/{fileId}")
    public ServerResponse copy(@PathVariable Long fileId, @RequestBody FileCopyDto fileCopyDto) {
        return filesService.copy(fileId, fileCopyDto);
    }
    
    @ApiOperation(value = "重命名文件")
    @PostMapping("/rename/{fileId}")
    public ServerResponse rename(@PathVariable Long fileId, @RequestBody FileRenameDto fileRenameDto) {
        if (Strings.isNullOrEmpty(fileRenameDto.getDir())) {
            return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        
        if (Strings.isNullOrEmpty(fileRenameDto.getNewName())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILENAME_NOT_BE_EMPTY);
        }
        
        if (fileRenameDto.getNewName().length() > Const.MAX_FILE_NAME_LENGTH) {
            return ServerResponse.createByErrorMessage("文件名称长度不能超过" + Const.MAX_FILE_NAME_LENGTH);
        }
        
        return filesService.rename(fileId, fileRenameDto);
    }
    
    @ApiOperation(value = "删除文件")
    @DeleteMapping("/{fileId}")
    public ServerResponse delete(@PathVariable Long fileId) {
        return filesService.removeById(fileId)
                ? ServerResponse.createBySuccessMessage(ReturnMessage.DELETE_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.DELETE_ERROR);
    }
    
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public ServerResponse batchDelete(@RequestBody List<Long> fileIds) {
        return filesService.removeByIds(fileIds)
                ? ServerResponse.createBySuccessMessage(ReturnMessage.DELETE_SUCCESS)
                : ServerResponse.createByErrorMessage(ReturnMessage.DELETE_ERROR);
    }
}