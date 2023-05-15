package org.lyqing.doubanfm.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SingerService;
import org.lyqing.doubanfm.service.SongService;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.HttpUtil;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SubjectSpider {

    private static Logger logger = LoggerFactory.getLogger(SubjectSpider.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SingerService singerService;

    @Autowired
    private SongService songService;

    private static final String MHZ_URL = "https://fm.douban.com/j/v2/rec_channels?specific=all";
    private static final String MHZ_REFERER = "https://fm.douban.com/";
    private static final String HOST = "fm.douban.com";

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private static final String SD_URL =
            "https://fm.douban.com/j/v2/playlist?channel={0}&kbps=128&client=s%3Amainsite%7Cy%3A3.0&app_name=radio_website&version=100&type=n";

    private static final String CO_URL = "https://fm.douban.com/j/v2/songlist/explore?type=hot&genre=0&limit=20&sample_cnt=5";

    //@PostConstruct
    public void init() {
        doExcute();
        logger.info("spider end...");
    }

    public void doExcute() {
        getSubjectData();
        getCollectionsData();
    }

    public void getSubjectData() {
        //获取数据
        String cookie = "bid=00nhyiqHWn8; _pk_ref.100001.f71f=%5B%22%22%2C%22%22%2C1683714782%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_id.100001.f71f=5f711492ca4fe663.1683714782.; _pk_ses.100001.f71f=1; _pk_ref.100002.f71f=%5B%22%22%2C%22%22%2C1683714950%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_id.100002.f71f=602c4a26d328c50b.1683714950.1.1683714950.1683714950.; _pk_ses.100002.f71f=*";
        Map<String, String> headers = HttpUtil.buildHeaderData(MHZ_REFERER, HOST, cookie);

        String content = HttpUtil.getContent(MHZ_URL, headers);

        //判断是否为空
        if (!StringUtils.hasText(content)) {
            return;
        }

        JSONObject jsonObject = JSON.parseObject(content);
        JSONObject data = (JSONObject) jsonObject.get("data");

        if (data == null) {
            return;
        }

        JSONObject channels = (JSONObject) data.get("channels");
        JSONArray artists = (JSONArray) channels.get("artist");
        JSONArray languages = (JSONArray) channels.get("language");
        JSONArray genres = (JSONArray) channels.get("genre");
        JSONArray scenarios = (JSONArray) channels.get("scenario");

        for (Object artist : artists) {
            Map<String, Object> artistData = (Map<String, Object>) artist;
            if (subjectService.getSubject(artistData.get("id").toString()) == null) {
                Subject subject = creatArtistSubject(artistData);
                subjectService.addSubject(subject);
                getSubjectSongData(subject);

                List<Singer> singerData = getSingerData(artistData);
                for (Singer singerDatum : singerData) {
                    if (singerService.getSinger(singerDatum.getId()) == null) {
                        singerService.addSinger(singerDatum);
                    }
                }

            }
        }

        for (Object language : languages) {
            Map<String, Object> artistData = (Map<String, Object>) language;
            if (subjectService.getSubject(artistData.get("id").toString()) == null) {
                Subject subject = creatLanguageSubject(artistData);
                subjectService.addSubject(subject);
                getSubjectSongData(subject);
            }
        }

        for (Object genre : genres) {
            Map<String, Object> genreData = (Map<String, Object>) genre;
            if (subjectService.getSubject(genreData.get("id").toString()) == null) {
                Subject subject = creatGenreSubject(genreData);
                subjectService.addSubject(subject);
                getSubjectSongData(subject);
            }
        }

        for (Object scenario : scenarios) {
            Map<String, Object> scenarioData = (Map<String, Object>) scenario;
            if (subjectService.getSubject(scenarioData.get("id").toString()) == null) {
                Subject subject = creatScenarioSubject(scenarioData);
                subjectService.addSubject(subject);
                getSubjectSongData(subject);
            }
        }

    }

    public void getSubjectSongData(Subject subject) {
        String subjectId = subject.getId();
        String songDataUrl = MessageFormat.format(SD_URL, subjectId);
        String cookie = "bid=00nhyiqHWn8; _pk_ref.100001.f71f=%5B%22%22%2C%22%22%2C1683714782%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_ref.100002.f71f=%5B%22%22%2C%22%22%2C1683714950%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_id.100002.f71f=602c4a26d328c50b.1683714950.1.1683714950.1683714950.; _pk_id.100001.f71f=5f711492ca4fe663.1683714782..1683714953.undefined.";
        Map<String, String> headers = HttpUtil.buildHeaderData(null, HOST, cookie);

        String content = HttpUtil.getContent(songDataUrl, headers);
        JSONObject songJSON = JSONObject.parseObject(content);
        JSONArray songs = (JSONArray) songJSON.get("song");
        //songList储存歌曲id，用于填入subject_songs表数据
        List<String> songList = new ArrayList<>();
        //获取歌曲信息
        for (Object song : songs) {
            Map<String, Object> songMap = (Map<String, Object>) song;

            String id = (String) songMap.get("sid");
            songList.add(id);

            String cover = (String) songMap.get("picture");
            String songUrl = (String) songMap.get("url");
            Integer gmtCreatedLong = (Integer) songMap.get("update_time");
            LocalDateTime gmtCreated = new Date(gmtCreatedLong).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String name = (String) songMap.get("title");

            JSONArray singers = (JSONArray) songMap.get("singers");
            //获取歌手信息并填入数据库中
            List<String> singerList = new ArrayList<>();
            for (Object singer : singers) {
                Map<String, Object> singerMap = (Map<String, Object>) singer;
                String singerId = singerMap.get("id").toString();
                String avatar = singerMap.get("avatar").toString();
                String singerName = singerMap.get("name").toString();

                singerList.add(singerId);
                Singer singerData = new Singer(singerId, LocalDateTime.now(), singerName, avatar, null, null);
                singerService.addSinger(singerData);
            }
            //将歌曲信息填入数据库
            Song songData = new Song(id, gmtCreated, LocalDateTime.now(), name, null, cover, songUrl, singers.toJavaList(String.class));
            songService.addSong(songData);
            //跟新subject数据
            subject.setSongIds(songList);
            subjectService.modify(subject);
        }

    }

    public List<Singer> getSingerData(Map<String, Object> artistData) {
        List<Singer> singers= new ArrayList<>();
        for (String relatedArtists : Arrays.asList(artistData.get("related_artists").toString())) {
            List<Map<String, Object>> relatedArtistsList = (List<Map<String, Object>>) JSONArray.parse(relatedArtists);
            for (Map<String, Object> relatedArtist : relatedArtistsList) {
                Singer singer = new Singer(
                        (String) relatedArtist.get("id"),
                        LocalDateTime.now(),
                        (String) relatedArtist.get("name"),
                        (String) relatedArtist.get("cover"),
                        null, null
                );
                singers.add(singer);
            }


        }

        return singers;
    }

    public void getCollectionsData() {

        String cookie = "bid=00nhyiqHWn8; _pk_ref.100001.f71f=%5B%22%22%2C%22%22%2C1683714782%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_ref.100002.f71f=%5B%22%22%2C%22%22%2C1683714950%2C%22https%3A%2F%2Flearn.youkeda.com%2F%22%5D; _pk_id.100002.f71f=602c4a26d328c50b.1683714950.1.1683714950.1683714950.; _pk_id.100001.f71f=5f711492ca4fe663.1683714782..1683714953.undefined.";
        Map<String, String> headers = HttpUtil.buildHeaderData(MHZ_REFERER, HOST, cookie);
        String content = HttpUtil.getContent(CO_URL, headers);
        List<Map> collectionMapList = JSON.parseArray(content, Map.class);

        for (Map collectionMap : collectionMapList) {
            String cover = collectionMap.get("cover").toString();
            LocalDateTime publishedTime = LocalDateTime.parse(collectionMap.get("created_time").toString(), df);
            LocalDateTime createdTime = LocalDateTime.parse(collectionMap.get("updated_time").toString(), df);
            String description = collectionMap.get("intro").toString();
            String name = collectionMap.get("title").toString();
            String id = collectionMap.get("id").toString();
            List<Map> songMapList = (List<Map>) collectionMap.get("sample_songs");

            List<String> songs = new ArrayList<>();
            for (Map songMap : songMapList) {

                String songId = songMap.get("sid").toString();
                songs.add(songId);

            }

            Map creator = (Map) collectionMap.get("creator");
            String singerId = creator.get("id").toString();
            String singerName = creator.get("name").toString();
            String created_time = creator.get("create_time").toString();
            LocalDateTime singerCreatedTime = LocalDate.parse(created_time, df2).atStartOfDay();

            String avatar = creator.get("picture").toString();
            String homepage = creator.get("url").toString();
            Singer singer = new Singer(singerId, singerCreatedTime, singerName, avatar, homepage, null);
            if (singerService.getSinger(singerId) != null) {
                singerService.modify(singer);
            } else {
                singerService.addSinger(singer);
            }

            Subject subject = new Subject(id, createdTime, LocalDateTime.now(), publishedTime, name, description, cover, singerName, songs, null, null);

            if (subjectService.getSubject(id) != null) {
                subjectService.modify(subject);
            }

        }

    }


    private Subject creatArtistSubject(Map<String, Object> artistData) {

        Subject subject = new Subject(artistData.get("id").toString(),
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                (String) artistData.get("name"),
                (String) artistData.get("intro"),
                (String) artistData.get("cover"), "",
                Arrays.asList(artistData.get("hot_songs").toString()),
                SubjectUtil.TYPE_MHZ,
                SubjectUtil.TYPE_SUB_ARTIST);

        return subject;
    }

    private Subject creatLanguageSubject(Map<String, Object> languageData) {

        Subject subject = new Subject(languageData.get("id").toString(),
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                (String) languageData.get("name"),
                (String) languageData.get("intro"),
                (String) languageData.get("cover"), "",
                Arrays.asList(languageData.get("hot_songs").toString()),
                SubjectUtil.TYPE_MHZ,
                SubjectUtil.TYPE_SUB_AGE);

        return subject;
    }

    private Subject creatGenreSubject(Map<String, Object> genreData) {

        Subject subject = new Subject(genreData.get("id").toString(),
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                (String) genreData.get("name"),
                (String) genreData.get("intro"),
                (String) genreData.get("cover"), "",
                Arrays.asList(genreData.get("hot_songs").toString()),
                SubjectUtil.TYPE_MHZ,
                SubjectUtil.TYPE_SUB_AGE);

        return subject;
    }

    private Subject creatScenarioSubject(Map<String, Object> scenarioData) {

        Subject subject = new Subject(scenarioData.get("id").toString(),
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                (String) scenarioData.get("name"),
                (String) scenarioData.get("intro"),
                (String) scenarioData.get("cover"), "",
                Arrays.asList(scenarioData.get("hot_songs").toString()),
                SubjectUtil.TYPE_MHZ,
                SubjectUtil.TYPE_SUB_AGE);

        return subject;
    }

}
