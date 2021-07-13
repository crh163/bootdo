package com.bootdo.api.entity.req.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManagerQueryListReq {

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("每页个数")
    private Integer pageSize;

    @ApiModelProperty("问卷id")
    private Long questionId;

    @ApiModelProperty("微信名称")
    private String nickName;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("问卷得分")
    private String submitScore;

    @ApiModelProperty("提交时间")
    private String submitDate;

}
