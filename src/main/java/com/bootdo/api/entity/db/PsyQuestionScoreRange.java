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
public class PsyQuestionScoreRange extends BaseModel implements Serializable {

    @ApiModelProperty("问卷id")
    private Long questionId;

    @ApiModelProperty("分数区间-低分")
    private Integer scoreLow;

    @ApiModelProperty("分数区间-高分")
    private Integer scoreHigh;

    @ApiModelProperty("评分结果")
    private String result;

    @ApiModelProperty("评分建议")
    private String advice;

}
