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
import cn.codex.netdisk.model.entity.Files;
import cn.codex.netdisk.model.entity.Folders;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFileAndFolderService;
import cn.codex.netdisk.service.IFilesService;
import cn.codex.netdisk.service.IFoldersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
            if (Const.ROOT_DIR.equals(folderAndFileVo.getPath())) {
                folderAndFileVo.setPath(Const.ROOT_DIR + folderAndFileVo.getName());
            } else {
                folderAndFileVo.setPath(folderAndFileVo.getPath() + Const.ROOT_DIR + folderAndFileVo.getName());
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
    @Transactional(rollbackFor = Exception.class)
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
            ServerResponse renameResponse = foldersService.rename(id, dto);
            if (renameResponse.isSuccess()) {
                String oldName = dto.getOldName();
                String newName = dto.getNewName();
                if (Const.NEW_COPY.equals(dto.getType())) {
                    // 如果是第二次重命名，说明名称有重复，需要获取新的文件夹
                    newName = ((String) renameResponse.getData());
                }
                String oldDir = dto.getDir() + "/" + oldName;
                String newDir = dto.getDir() + "/" + newName;
                // 更新子目录
                updateChildFolders(oldDir, newDir);
                // 更新子文件
                updateChildFiles(oldDir, newDir);
            }
            return renameResponse;
        }

        if (isDir == 0) {
            return filesService.rename(id, dto);
        }

        return ServerResponse.createByErrorMessage(ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }

    @ApiOperation(value = "根据后缀名获取图标")
    @GetMapping("/{extension}")
    public ServerResponse getIcon(@PathVariable String extension) {
        String fileIcon = FileUtil.getFileIcon(extension);
        return ServerResponse.createBySuccess(fileIcon);
    }

    /**
     * 批量更新子文件的路径
     *
     * @param oldDir 旧目录路径
     * @param newDir 新目录路径
     */
    private void updateChildFiles(String oldDir, String newDir) {
        // 批量更新文件路径 tb_files
        List<Files> childFiles = filesService.list(new QueryWrapper<Files>()
                .likeRight(Files.DIR, oldDir)
                .select(Files.FILE_ID, Files.DIR));
        if (!childFiles.isEmpty()) {
            childFiles.forEach(childFile -> {
                String childDir = childFile.getDir().replaceFirst(oldDir, newDir);
                childFile.setDir(childDir);
            });

            filesService.updateBatchById(childFiles);
        }
    }

    /**
     * 批量更新子目录的路径
     *
     * @param oldDir 旧目录路径
     * @param newDir 新目录路径
     * @return 更新是否成功
     */
    private void updateChildFolders(String oldDir, String newDir) {
        // 重命名成功，更新子目录
        List<Folders> childFolders = foldersService.list(new QueryWrapper<Folders>()
                .likeRight(Folders.DIR, oldDir)
                .select(Folders.FOLDER_ID, Folders.DIR));
        // 批量更新所有子目录的路径。
        if (!childFolders.isEmpty()) {
            // 更新子文件夹的所在目录
            childFolders.forEach(childFolder -> {
                String childDir = childFolder.getDir().replaceFirst(oldDir, newDir);
                childFolder.setDir(childDir);
            });
            foldersService.updateBatchById(childFolders);
        }
    }
}
