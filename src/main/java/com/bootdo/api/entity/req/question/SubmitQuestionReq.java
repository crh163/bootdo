package com.bootdo.api.entity.req.question;

import com.bootdo.api.entity.req.question.info.TopicGapFill;
import com.bootdo.api.entity.req.question.info.TopicSelect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class SubmitQuestionReq {

    @ApiModelProperty("问卷id")
    private Long questionId;

    @ApiModelProperty("选择题选项")
    private List<TopicSelect> topicSelects;

    @ApiModelProperty("填空题内容")
    private List<TopicGapFill> topicGapFills;

}
