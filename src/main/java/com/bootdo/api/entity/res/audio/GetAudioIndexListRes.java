package com.bootdo.api.entity.res.audio;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rory.chen
 * @date 2021/6/22
 */
@Data
public class GetAudioIndexListRes {

    @ApiModelProperty("音频栏目id")
    private Long id;

    @ApiModelProperty("音频栏目名称")
    private String indexAudioName;

    @ApiModelProperty("图片url")
    private String photoUrl;

    @ApiModelProperty("排序")
    private Integer orderNum;

}
