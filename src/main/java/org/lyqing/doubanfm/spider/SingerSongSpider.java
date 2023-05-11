package org.lyqing.doubanfm.spider;

import com.alibaba.fastjson.JSONObject;
import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SingerService;
import org.lyqing.doubanfm.service.SongService;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingerSongSpider {

    private static Logger logger = LoggerFactory.getLogger(SubjectSpider.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SingerService singerService;

    @Autowired
    private SongService songService;

    private static final String SINGER_REFERER = "https://fm.douban.com/";
    private static final String HOST = "fm.douban.com";

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private static final String SD_URL =
            "https://fm.douban.com/j/v2/artist/{0}/";


    @PostConstruct
    public void init() {
        doExcute();
        logger.info("spider end...");
    }

    public void doExcute() {
        List<Singer> singers = singerService.getAll();
        for (Singer singer : singers) {
            getSongDataBySingers(singer);
        }
    }

    public void getSongDataBySingers(Singer singer) {

        String id = singer.getId();

        String cookie = "bid=00nhyiqHWn8; _pk_ref.100001.f71f=%5B%22%22%2C%22%22%2C1683714782%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_ref.100002.f71f=%5B%22%22%2C%22%22%2C1683714950%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_id.100002.f71f=602c4a26d328c50b.1683714950.1.1683714950.1683714950.; _pk_id.100001.f71f=5f711492ca4fe663.1683714782..1683714953.undefined.";
        Map<String, String> headers = HttpUtil.buildHeaderData(SINGER_REFERER, HOST, cookie);
        String url = MessageFormat.format(SD_URL, id);
        String content = HttpUtil.getContent(url, headers);
        Map singerMap = JSONObject.parseObject(content, Map.class);
        List<Map> songlist = (List<Map>) singerMap.get("songlist");
        for (Map songsMap : songlist) {
            List<Map> songList = (List<Map>) songsMap.get("songs");
            for (Map songMap :songList){
                String songId = songMap.get("sid").toString();
                String name = songMap.get("title").toString();
                String songUrl = songMap.get("url").toString();
                String cover = songMap.get("picture").toString();
                List<Map> singers = (List<Map>) songMap.get("singers");
                List<String> singerIds = getSingerIds(singers);
                Song song = new Song(songId, LocalDateTime.now(), LocalDateTime.now(), name, null, cover, songUrl, singerIds);
                if (songService.getSong(songId) != null) {
                    songService.modify(song);
                } else {
                    songService.addSong(song);
                }
            }
        }
    }

    public List<String> getSingerIds(List<Map> singers) {
        List<String> singerIds = new ArrayList<String>();
        for (Map singer : singers) {
            String singerId = singer.get("id").toString();
            singerIds.add(singerId);
        }

        return singerIds;
    };

}
