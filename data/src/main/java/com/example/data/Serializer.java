
package com.example.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 */
@Singleton
public class Serializer implements SerializerFacade {

    @Inject
    public Serializer() {
    }

    public String serialize(Object object, Class clazz) {
        return JSON.toJSONString(object);
    }

    public String serialize(Object object) {
        return serialize(object, null);
    }

    public <T> T deserialize(String string, Class<T> clazz) {
        return JSON.parseObject(string, clazz);
    }

    public Map<String, String> deserializeMap(String string) {
        return (string == null || string
            .length() == 0) ? new HashMap<String, String>() : JSONObject
            .parseObject(string, new TypeReference<Map<String, String>>() {
            });

    }

    public List<String> deserializeList(String string) {
        List<String> arrayList = new ArrayList<>();
        if (string == null || string.length() == 0) {
            return arrayList;
        } else {
            JSONArray jsonArray = JSONArray.parseArray(string);

            for (int i = 0; i < jsonArray.size(); ++i) {
                arrayList.add(jsonArray.getString(i));
            }

            return arrayList;
        }
    }

    /**
     * check string isBlank
     */
    private boolean isBlank(String string) {
        return null == string || string.length() <= 0;
    }
}