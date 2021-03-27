package cn.codex.netdisk.portal.controller;


import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.exception.ErrorException;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.dtos.FileCopyDto;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFilesService;
import cn.codex.netdisk.service.IFoldersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
    
    @Autowired
    private IFoldersService foldersService;
    
    // TODO 上传文件、下载文件、在线预览
    
    @ApiOperation(value = "获取文件夹和文件列表")
    @GetMapping("/")
    public ServerResponse getFolderAndFilesList(FolderAndFileQueryDto dto) {
        if (dto == null) {
            throw new CustomException(ReturnMessage.ILLEGAL_REQUEST);
        }
        List<FolderAndFileVo> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        // 分页条件
        Long page = dto.getPage();
        if (page == null || page == 0) {
            page = Const.DEFAULT_PAGE;
        }
        Long limit = dto.getLimit();
        if (limit == null || limit == 0) {
            limit = Const.DEFAULT_LIMIT;
        }
        // 文件夹列表
        Page<Folders> foldersPage = new Page<>(page, limit);
        QueryWrapper<Folders> wrapper = new QueryWrapper<>();
        wrapper.eq(Folders.DIR, dto.getDir()).eq(Folders.CREATOR, SecurityUtil.getUsername());
        if (dto.getDesc() == 1) {
            wrapper.orderByDesc(dto.getOrder());
        } else {
            wrapper.orderByAsc(dto.getOrder());
        }
        Page<Folders> foldersPages = foldersService.page(foldersPage, wrapper);
        List<Folders> foldersList = foldersPages.getRecords();
        // 组装文件夹显示列表
        foldersList.forEach(folders -> {
            FolderAndFileVo folderAndFileVo = new FolderAndFileVo();
            folderAndFileVo.setId(folders.getFolderId());
            folderAndFileVo.setName(folders.getFolderName());
            folderAndFileVo.setIsDir(1);
            folderAndFileVo.setSize(null);
            folderAndFileVo.setSizeStr(null);
            folderAndFileVo.setGmtModified(folders.getGmtModified());
            if ("/".equals(dto.getDir())) {
                folderAndFileVo.setPath(dto.getDir() + folders.getFolderName());
            } else {
                folderAndFileVo.setPath(dto.getDir() + "/" + folders.getFolderName());
            }
            list.add(folderAndFileVo);
        });
        // 文件列表
        if (foldersList.size() < limit) {
            QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
            filesQueryWrapper.eq(Files.DIR, dto.getDir()).eq(Files.CREATOR, SecurityUtil.getUsername());
            if (dto.getDesc() == 1) {
                filesQueryWrapper.orderByDesc(dto.getOrder());
            } else {
                filesQueryWrapper.orderByAsc(dto.getOrder());
            }
            Page<Files> filesPage = new Page<>(page, limit - (long) foldersList.size());
            Page<Files> filesPages = filesService.page(filesPage, filesQueryWrapper);
            List<Files> filesList = filesPages.getRecords();
            // 组装文件显示列表
            filesList.forEach(files -> {
                FolderAndFileVo folderAndFileVo = new FolderAndFileVo();
                folderAndFileVo.setId(files.getFileId());
                folderAndFileVo.setName(files.getRealName());
                folderAndFileVo.setIsDir(0);
                folderAndFileVo.setSize(files.getSize());
                folderAndFileVo.setSizeStr(FileUtil.byteCountToDisplaySize(files.getSize()));
                folderAndFileVo.setGmtModified(files.getGmtModified());
                if ("/".equals(dto.getDir())) {
                    folderAndFileVo.setPath(dto.getDir() + files.getRealName());
                } else {
                    folderAndFileVo.setPath(dto.getDir() + "/" + files.getRealName());
                }
                list.add(folderAndFileVo);
            });
            map.put("list", list);
            map.put("count", foldersList.size() + filesList.size());
            map.put("isAll", (foldersPage.getTotal() + filesPage.getTotal()) <= limit ? 1 : 0);
        } else {
            map.put("list", list);
            map.put("count", foldersList.size());
            // 查询该目录下文件总数
            int fileCount = filesService.count(new QueryWrapper<Files>().eq(Files.DIR, dto.getDir()).eq(Files.CREATOR, SecurityUtil.getUsername()));
            map.put("isAll", (foldersPage.getTotal() + fileCount) <= limit ? 1 : 0);
        }
        
        return ServerResponse.createBySuccess(map);
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