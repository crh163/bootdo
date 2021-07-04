package com.bootdo.api.entity.res.question.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetQuestionTopicOptions {

    @ApiModelProperty("选项id")
    private Long optionId;

    @ApiModelProperty("选项内容")
    private String optionName;

    @ApiModelProperty("选项对应分数")
    private String optionScore;

}
