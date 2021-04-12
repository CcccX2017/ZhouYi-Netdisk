package cn.codex.netdisk.portal.controller;

import cn.codex.netdisk.common.constants.ReturnMessage;
import cn.codex.netdisk.common.dtos.ServerResponse;
import cn.codex.netdisk.common.exception.CustomException;
import cn.codex.netdisk.common.utils.FileUtil;
import cn.codex.netdisk.common.utils.SecurityUtil;
import cn.codex.netdisk.model.dtos.FolderAndFileQueryDto;
import cn.codex.netdisk.model.dtos.PageResult;
import cn.codex.netdisk.model.vo.FolderAndFileVo;
import cn.codex.netdisk.service.IFileAndFolderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件和文件夹管理
 *
 * @author codex
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/portal/list")
@Api(tags = "文件和文件夹管理")
public class FileAndFolderController {
    
    @Autowired
    private IFileAndFolderService fileAndFolderService;
    
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
        IPage<FolderAndFileVo> pageData = fileAndFolderService.getList(SecurityUtil.getUsername(), dto);
        List<FolderAndFileVo> records = pageData.getRecords();
        records.forEach(folderAndFileVo -> {
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
}
