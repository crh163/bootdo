package com.bootdo.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldUtil {

    /**
     * 判断对象属性是否有null值
     *
     * @param obj
     * @return
     */
    public static String judgeClassHasNull(Object obj) {
        if (obj == null) {
            return "未填写";
        }
        List<String> ignoreFields = Arrays.asList("id", "createId", "createDate", "updateId",
                "updateDate", "diagMedicineInfo", "diagEducationYear", "diagChildRank");
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (ignoreFields.contains(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    return "待完善";
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "已完善";
    }

}
