package com.bootdo.common.domain.page;

import lombok.Data;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-03-22 18:13
 */
@Data
public class ItemsDto<T> {

    private List<T> items;

    private PageDto page;

}
