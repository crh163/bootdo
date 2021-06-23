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
public class PsyQuestionTopicRecord extends BaseModel implements Serializable {

    @ApiModelProperty("问卷提交记录id")
    private Long questionRecordId;

    @ApiModelProperty("题目id")
    private Long questionTopicId;

    @ApiModelProperty("题目内容")
    private String questionTopicText;

    @ApiModelProperty("选项id")
    private Long questionOptionId;

    @ApiModelProperty("选项内容")
    private String questionOptionText;

}
