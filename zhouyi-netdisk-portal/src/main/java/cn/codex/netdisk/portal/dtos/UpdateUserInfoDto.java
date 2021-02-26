package cn.codex.netdisk.portal.dtos;

import cn.codex.netdisk.common.constants.Const;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 更新个人信息对象
 *
 * @author CodeX
 * @since 2021-02-26 09:36:28
 */
@Getter
@Setter
@ApiModel(value = "更新个人信息对象", description = "UpdateUserInfoDto")
public class UpdateUserInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", position = 1)
    private Long userId;

    @ApiModelProperty(value = "用户昵称", position = 2)
    private String nickname;

    @ApiModelProperty(value = "真实姓名", position = 3)
    private String realName;

    @ApiModelProperty(value = "性别（0-男 1-女 2-未知）", position = 4)
    private String sex;

    @ApiModelProperty(value = "生日", position = 5)
    private Date birthday;

    public String getSex() {
        return Strings.isNullOrEmpty(sex) ? Const.DEFAULT_SEX : sex;
    }
}
