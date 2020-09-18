package cn.org.zmabel.admin.util;

import cn.hutool.json.JSONObject;

/**
 * 默认成功请求 errorcode == 100
 */
public class JsonResult {

    public static JSONObject success(Object data) {
        JSONObject json = new JSONObject();
        json.set("success", true);
        json.set("errorCode", "100");
        json.set("errorMessage", "success");
        if (null != data) {
            json.set("data", data);
        }
        return json;
    }

    public static JSONObject fail(String errorCode, String errorMessage) {
        JSONObject json = new JSONObject();
        json.set("success", true);
        json.set("errorCode", errorCode);
        json.set("errorMessage", errorMessage);
        return json;
    }

    public static JSONObject other(String errorCode, String errorMessage, Object data) {
        JSONObject json = new JSONObject();
        json.set("success", true);
        json.set("errorCode", errorCode);
        json.set("errorMessage", errorMessage);
        if (null != data) {
            json.set("data", data);
        }
        return json;
    }
}
