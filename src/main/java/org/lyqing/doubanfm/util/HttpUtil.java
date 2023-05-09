package org.lyqing.doubanfm.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.ibatis.annotations.Mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    /**
     * 构建header
     * */
    public static Map<String, String> buildHeaderData(String referer, String host, String cookie) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Referer", referer);
        headers.put("Host", host);
        headers.put("Cookie", cookie);
        return headers;
    }

    /**
     * 获取页面内容
     * */
    public static String getContent(String url, Map<String, String> headers) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
           e.printStackTrace();
            return "Error";
        }
    }

}
