package com.bootdo.common.domain.page;

import com.bootdo.common.constant.ResponseCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rory.chen
 * @date 2021-01-07 14:39
 */
@Data
@NoArgsConstructor
public class PageableItemsDto<T> extends ItemsDto<T>{

    private Integer code;

    private String msg;

    public PageableItemsDto(ResponseCodeEnum responseCodeEnum){
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getMsg();
    }

}
