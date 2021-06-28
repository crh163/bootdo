package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rory.chen
 * @date 2021/6/23
 */
@Data
public class PsyQuestionRecord extends BaseModel implements Serializable {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("wx openid")
    private String openId;

    @ApiModelProperty("问卷id")
    private Long questionId;

    @ApiModelProperty("提交时间(年月日)")
    private String submitDate;

    @ApiModelProperty("提交时间(年月日-时分秒)")
    private String submitDateFull;

    @ApiModelProperty("问卷提交得分")
    private String submitScore;

    @ApiModelProperty("问卷总分")
    private String sumScore;

    @ApiModelProperty("请求json数据")
    private String requestJson;

    @ApiModelProperty("响应json数据")
    private String responseJson;

}
