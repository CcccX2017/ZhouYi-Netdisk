package cn.codex.netdisk.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author codex
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;
    
    @ApiModelProperty(value = "菜单标题")
    private String menuTitle;
    
    @ApiModelProperty(value = "菜单图标")
    private String iconClass;
    
    @ApiModelProperty(value = "菜单路径")
    private String path;
    
    @ApiModelProperty(value = "菜单层级")
    private Integer level;
    
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;
    
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date gmtCreate;
    
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date gmtModified;
    
    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;
    
    public static final String MENU_ID = "menu_id";
    
    public static final String MENU_TITLE = "menu_title";
    
    public static final String ICON_CLASS = "icon_class";
    
    public static final String PATH = "path";
    
    public static final String PARENT_ID = "parent_id";
    
    public static final String GMT_CREATE = "gmt_create";
    
    public static final String GMT_MODIFIED = "gmt_modified";
    
}
