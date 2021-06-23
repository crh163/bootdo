package com.bootdo.common.utils;

import com.bootdo.api.entity.req.common.CommonOpenIdReq;

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
    public static boolean judgeClassHasNull(Object obj) {
        if (obj == null) {
            return true;
        }
        List<String> ignoreFields = Arrays.asList("id", "createId", "createDate", "updateId", "updateDate");
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (ignoreFields.contains(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CommonOpenIdReq req = new CommonOpenIdReq();
        System.out.println(judgeClassHasNull(req));
    }

}
