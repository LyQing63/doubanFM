package org.lyqing.doubanfm.util;

import org.springframework.stereotype.Component;

@Component
public class SubjectUtil {

    /**
     * 兆赫
     * */
    public static final String TYPE_MHZ = "mhz";

    /**
     * 歌单
     * */
    public static final String TYPE_COLLECTION = "collection";

    /**
     * 子分类
     * 艺术家
     * */
    public static final String TYPE_SUB_ARTIST = "artist";

    /**
     * 子分类
     * 心情/场景
     * */
    public static final String TYPE_SUB_MOOD = "mood";

    /**
     * 子分类
     * 语言/年代
     * */
    public static final String TYPE_SUB_AGE = "age";

    /**
     * 子分类
     * 风格/流派
     * */
    public static final String TYPE_SUB_STYLE = "style";
}
