package com.bootdo.api.entity.res.question.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetQuestionTopic {

    @ApiModelProperty("题目id")
    private Long topicId;

    @ApiModelProperty("题目内容")
    private String topicName;

    @ApiModelProperty("题目类型 1 选择题 2 填空题")
    private Integer topicType;

    @ApiModelProperty("选择题的选项列表（填空题为null）")
    private List<GetQuestionTopicOptions> options;

}
