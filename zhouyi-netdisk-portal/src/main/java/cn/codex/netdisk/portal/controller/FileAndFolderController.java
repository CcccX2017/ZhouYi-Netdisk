package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.Const;
import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.enums.ResponseCode;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.RegexUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.dtos.FileRenameDto;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.dtos.PageResult;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFileAndFolderService;
import cn.codex.netdisk.service.IFilesService;
import cn.codex.netdisk.service.IFoldersService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文件和文件夹管理
 *
 * @author codex
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/portal/list")
@Api(tags = "文件和文件夹管理")
@Transactional(rollbackFor = Exception.class)
public class FileAndFolderController {

    @Autowired
    private IFileAndFolderService fileAndFolderService;

    @Autowired
    private IFilesService filesService;

    @Autowired
    private IFoldersService foldersService;

    @ApiOperation(value = "获取文件夹和文件列表")
    @GetMapping("/")
    public ServerResponse getFolderAndFilesList(FolderAndFileQueryDto dto) {
        if (dto == null) {
            throw new CustomException(ReturnMessage.ILLEGAL_REQUEST);
        }
        PageResult pageResult = new PageResult();
        // 分页条件
        Long page = dto.getPage();
        Long limit = dto.getLimit();
        if (dto.getIsSearch() == null) {
            throw new CustomException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        IPage<FolderAndFileVo> pageData;
        if (dto.getIsSearch() == 0) {
            pageData = fileAndFolderService.getList(SecurityUtil.getUsername(), dto);
        } else if (dto.getIsSearch() == 1) {
            pageData = fileAndFolderService.search(SecurityUtil.getUsername(), dto);
        } else {
            throw new CustomException(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<FolderAndFileVo> records = pageData.getRecords();
        records.forEach(folderAndFileVo -> {
            folderAndFileVo.setDir(folderAndFileVo.getPath());
            if ("/".equals(folderAndFileVo.getPath())) {
                folderAndFileVo.setPath("/" + folderAndFileVo.getName());
            } else {
                folderAndFileVo.setPath(folderAndFileVo.getPath() + "/" + folderAndFileVo.getName());
            }
            if (folderAndFileVo.getIsDir() == 0) {
                folderAndFileVo.setSizeStr(FileUtil.byteCountToDisplaySize(folderAndFileVo.getSize()));
            }
        });

        pageResult.setList(records);
        // 已加载数 = 当前查询到的记录数 + 之前已经加载的记录数 (page - 1) * limit
        pageResult.setCount(records.size() + (int) ((page - 1) * limit));
        // 是否全部加载 = 已加载数 == 总记录数 ? 1 : 0
        pageResult.setIsAll(pageResult.getCount() == pageData.getTotal() ? 1 : 0);

        return ServerResponse.createBySuccess(pageResult);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public ServerResponse batchDelete(@RequestBody Map<String, List<Long>> map) {
        try {
            List<Long> fileIds = map.get("fileIds");
            List<Long> folderIds = map.get("folderIds");
            // 删除文件
            if (fileIds != null) {
                if (fileIds.size() == 1) {
                    // 删除单个
                    filesService.removeById(fileIds.get(0));
                } else if (fileIds.size() > 1) {
                    // 批量删除
                    filesService.removeByIds(fileIds);
                }
            }

            // 删除文件夹
            if (folderIds != null) {
                if (folderIds.size() == 1) {
                    // 删除单个
                    foldersService.removeById(folderIds.get(0));
                } else if (folderIds.size() > 1) {
                    // 批量删除
                    foldersService.removeByIds(folderIds);
                }
            }

            return ServerResponse.createBySuccessMessage(ReturnMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(ReturnMessage.DELETE_ERROR);
        }
    }

    @ApiOperation(value = "重命名")
    @PutMapping("/{id}/{isDir}")
    public ServerResponse rename(@PathVariable Long id,
                                 @PathVariable Integer isDir,
                                 @RequestBody FileRenameDto dto) {

        if (Strings.isNullOrEmpty(dto.getNewName().trim())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILENAME_NOT_BE_EMPTY);
        }

        if (dto.getDir() == null) {
            return ServerResponse.createByErrorMessage(ReturnMessage.DIR_NOT_BE_EMPTY);
        }

        // 判断文件夹名称是否超长
        if (dto.getNewName().getBytes().length > Const.MAX_FILE_NAME_LENGTH) {
            return ServerResponse.createByErrorMessage("文件(夹)名称不能超过" + Const.MAX_FILE_NAME_LENGTH + "字节");
        }

        // 判断文件名是否合法
        if (!RegexUtil.isFileNameHaveSpecialCharacters(dto.getNewName())) {
            return ServerResponse.createByErrorMessage(ReturnMessage.FILE_NAME_ILLEGAL);
        }

        if (isDir == 1) {
            return foldersService.rename(id, dto);
        }

        if (isDir == 0) {
            return filesService.rename(id, dto);
        }

        return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
}
