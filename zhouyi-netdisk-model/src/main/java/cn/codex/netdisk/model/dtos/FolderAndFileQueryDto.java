package cn.codex.netdisk.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 文件夹和文件查询实体类
 *
 * @author codex
 * @since 2021-03-27
 */
@ApiModel(value = "文件夹和文件查询实体类", description = "FolderAndFileQueryDto")
public class FolderAndFileQueryDto implements Serializable {
    private static final long serialVersionUID = -4529087909682362550L;
    
    @ApiModelProperty("当前页")
    private Long page;
    
    @ApiModelProperty("页面条数")
    private Long limit;
    
    @ApiModelProperty("所属目录")
    private String dir;
    
    @ApiModelProperty("排序")
    private String order;
    
    @ApiModelProperty("是否降序 1-降序，0-升序")
    private Integer desc;

    @ApiModelProperty("关键字搜索")
    private String keyword;

    @ApiModelProperty("是否搜索")
    private Integer isSearch;
    
    public Long getPage() {
        if (page == null || page <= 0) {
            page = 1L;
        }
        return page;
    }
    
    public void setPage(Long page) {
        this.page = page;
    }
    
    public Long getLimit() {
        if (limit == null || limit <= 0) {
            limit = 100L;
        }
        return limit;
    }
    
    public void setLimit(Long limit) {
        this.limit = limit;
    }
    
    public String getDir() {
        return dir;
    }
    
    public void setDir(String dir) {
        this.dir = dir;
    }
    
    public String getOrder() {
        return order;
    }
    
    public void setOrder(String order) {
        this.order = order;
    }
    
    public Integer getDesc() {
        return desc;
    }
    
    public void setDesc(Integer desc) {
        this.desc = desc;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(Integer isSearch) {
        this.isSearch = isSearch;
    }
}
