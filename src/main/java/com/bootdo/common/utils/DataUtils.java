package com.bootdo.common.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static <T> List<T> coverList(List list, Class<T> clazz) {
        if (list == null) {
            return null;
        }
        List<T> datas = new ArrayList<>();
        Gson gson = new Gson();
        for (Object o : list) {
            datas.add(gson.fromJson(gson.toJson(o), clazz));
        }
        return datas;
    }

    public static <T> T coverData(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), clazz);
    }
}
