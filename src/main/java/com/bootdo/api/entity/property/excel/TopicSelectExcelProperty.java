package com.bootdo.api.entity.property.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 选择题导出
 */
@Data
public class TopicSelectExcelProperty extends BaseRowModel {

    @ExcelProperty(value = "序号", index = 0)
    private String order;

    @ExcelProperty(value = "题目名称", index = 1)
    private String questionTopicText;

    @ExcelProperty(value = "所选答案", index = 2)
    private String questionOptionText;

    @ExcelProperty(value = "所选答案得分", index = 3)
    private String questionOptionScore;

}
