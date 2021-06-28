package com.bootdo.api.entity.res.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetQuestion {

    @ApiModelProperty("问卷id")
    private Long questionId;

    @ApiModelProperty("问卷指导语")
    private String questionGuide;

    @ApiModelProperty("问卷评分方式（无区间判断）")
    private String questionRemark;

    @ApiModelProperty("问卷总分")
    private String questionSumScore;

    @ApiModelProperty("问卷问题")
    private List<GetQuestionTopic> topics;

}
