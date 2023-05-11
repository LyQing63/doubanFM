package org.lyqing.doubanfm.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 日志功能
     * */
    @PostConstruct
    public void init() {
        logger.info("okHttpClient init successful");
    }

    /**
     * 构建header
     * */
    public static Map<String, String> buildHeaderData(String referer, String host, String cookie) {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");

        if (referer != null) {
            headers.put("Referer", referer);
        }

        if (host != null) {
            headers.put("Host", host);
        }

        if (cookie != null) {
            headers.put("Cookie", cookie);
        }

        return headers;
    }

    /**
     * 获取页面内容
     * */
    public static String getContent(String url, Map<String, String> headers) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                builder.addHeader("key", headers.get(key));
            }
        }

        Request request = builder.build();
        String result = null;
        Call call = client.newCall(request);
        try {
            logger.info("request" + url + "begin.");
            result = call.execute().body().string();
        } catch (IOException e) {
            logger.error("request" + url + " exception.", e);
        }finally  {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
        }

        return result;
    }

}
