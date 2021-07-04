package com.bootdo.api.entity.res.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexInfoRes {

    @ApiModelProperty("个人信息完善程度")
    private String userInfoStatus;

    @ApiModelProperty("问卷本次最后截止时间")
    private String questionLastTime;

    @ApiModelProperty("问卷最新提交时间")
    private String questionSubmitNewTime;

    @ApiModelProperty("上一次打卡时间")
    private String beforeClockTime;

}
