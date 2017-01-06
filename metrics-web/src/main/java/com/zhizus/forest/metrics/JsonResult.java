package com.zhizus.forest.metrics;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Dempe on 2017/1/5 0005.
 */
public class JsonResult extends JSONObject {
    public static JsonResult successResult() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", 0);
        return jsonResult;
    }

    public static JsonResult failedResult(String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", 1);
        jsonResult.put("msg", msg);
        return jsonResult;
    }

    public static JsonResult failedWithUnauthorized() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", -1);
        jsonResult.put("msg", "unauthorized");
        return jsonResult;
    }

    public JsonResult setData(Object data) {
        put("data", data);
        return this;
    }
}
