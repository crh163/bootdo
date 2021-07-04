package com.bootdo.api.entity.db;

import com.bootdo.common.domain.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PsyAudio extends BaseModel implements Serializable {

    @ApiModelProperty("音频名称")
    private String audioName;

    @ApiModelProperty("音频作者")
    private String audioAuthor;

    @ApiModelProperty("音频头像url")
    private String audioAvatarUrl;

    @ApiModelProperty("音频地址url")
    private String audioUrl;

    @ApiModelProperty("短文章")
    private String audioShortArticle;

}
