package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Date submitDate;

    @ApiModelProperty("提交时间(年月日-时分秒)")
    private Date submitDateFull;

    @ApiModelProperty("问卷提交得分")
    private String submitScore;

    @ApiModelProperty("问卷总分")
    private String sumScore;

}
