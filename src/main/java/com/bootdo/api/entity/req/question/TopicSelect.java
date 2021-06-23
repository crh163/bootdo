package com.bootdo.api.entity.req.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class TopicSelect {

    @ApiModelProperty("题目id")
    private Long topicId;

    @ApiModelProperty("选项id")
    private Long optionId;

}
