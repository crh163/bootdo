package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysWxUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 6809392313564681945L;

    @ApiModelProperty("微信openid")
    private String openId;

    @ApiModelProperty("微信session_key")
    private String sessionKey;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
    private String gender;

    @ApiModelProperty("微信用户绑定的手机号")
    private String phone;

    @ApiModelProperty("用户所在城市")
    private String city;

    @ApiModelProperty("用户所在省份")
    private String province;

    @ApiModelProperty("用户所在国家")
    private String country;

    @ApiModelProperty("用户的语言，简体中文为zh_CN")
    private String language;

}
