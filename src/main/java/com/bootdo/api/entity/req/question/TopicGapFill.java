package com.bootdo.api.entity.req.question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class TopicGapFill {

    @ApiModelProperty("填空题题目id")
    private Long topicId;

    @ApiModelProperty("填空题内容")
    private String gapFillText;

}
