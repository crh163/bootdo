package com.bootdo.api.entity.res.audio;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetAudioListRes {

    @ApiModelProperty("音频id")
    private Long id;

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
