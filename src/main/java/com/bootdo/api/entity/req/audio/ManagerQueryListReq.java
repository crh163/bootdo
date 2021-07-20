package com.bootdo.api.entity.req.audio;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManagerQueryListReq {

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("每页个数")
    private Integer pageSize;

    @ApiModelProperty("微信名称")
    private String nickName;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("打卡时间")
    private String submitDate;

    @ApiModelProperty("ids")
    private String recordIds;

}
