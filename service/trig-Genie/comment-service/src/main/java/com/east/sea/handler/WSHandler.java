package com.east.sea.handler;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WSHandler {

    protected Map<String, String> getQueryMap(String queryStr) {
        Map<String, String> queryMap = new HashMap<>(4);
        if (StrUtil.isNotBlank(queryStr)) {
            String[] queryParam = queryStr.split("&");
            Arrays.stream(queryParam).forEach(s -> {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            });
        }
        return queryMap;
    }

}
