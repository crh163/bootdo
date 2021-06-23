package com.bootdo.api.entity.req.question;

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

    @ApiModelProperty("题目选择结果")
    private List<TopicSelect> topicSelects;

}
