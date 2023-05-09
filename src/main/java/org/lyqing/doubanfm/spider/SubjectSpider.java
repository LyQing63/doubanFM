package org.lyqing.doubanfm.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SingerService;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.HttpUtil;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SubjectSpider {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SingerService singerService;

    @PostConstruct
    public void init() {
        doExcute();
    }

    public void doExcute() {
        getSubjectData();
    }

    public void getSubjectData() {
        //获取数据
        String url = "https://fm.douban.com/j/v2/rec_channels?specific=all";
        Map<String, String> headers = new HashMap<>();
        String content = HttpUtil.getContent(url, headers);
        JSONObject jsonObject = JSON.parseObject(content);
        JSONObject data = (JSONObject) jsonObject.get("data");
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
            }
        }

        for (Object genre : genres) {
            Map<String, Object> genreData = (Map<String, Object>) genre;
            if (subjectService.getSubject(genreData.get("id").toString()) == null) {
                Subject subject = creatGenreSubject(genreData);
                subjectService.addSubject(subject);
            }
        }

        for (Object scenario : scenarios) {
            Map<String, Object> scenarioData = (Map<String, Object>) scenario;
            if (subjectService.getSubject(scenarioData.get("id").toString()) == null) {
                Subject subject = creatScenarioSubject(scenarioData);
                subjectService.addSubject(subject);
            }
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
