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
public class PsyAudioIndex extends BaseModel implements Serializable {

    @ApiModelProperty("音频栏目名称")
    private String indexAudioName;

    @ApiModelProperty("图片url")
    private String photoUrl;

    @ApiModelProperty("排序")
    private Integer orderNum;

}
