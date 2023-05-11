package org.lyqing.doubanfm;

import com.github.pagehelper.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoubanFmApplication.class)
public class SongTest {

    @Autowired
    private SongService songService;

    @Test
    public void testAddSong() {
        List<String> singers = new ArrayList<>();
        singers.add("002");
        singers.add("003");
        Song song = new Song("001", LocalDateTime.now(), LocalDateTime.now(), "qwer", "asdfsa", "asdf", "ss", singers);
        songService.addSong(song);
        System.out.println("-----------测试成功-----------");
        System.out.println(song);
    };

    @Test
    public void testGetAllSong() {
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(1);
        Page<Song> songPage = songService.list(songQueryParam);
        System.out.println("-----------测试成功-----------");
        System.out.println(songPage);
    };

    @Test
    public void testGetSong() {
        System.out.println(songService.getSong("001"));
    };

    @Test
    public void testModifySong() {
        List<String> similar = new ArrayList<>();
        Collections.addAll(similar, "002", "003");
        Song song = new Song("001", LocalDateTime.now(),LocalDateTime.now(), "cxk", "223", "113", "url",similar);
        songService.modify(song);
        System.out.println("-----------测试成功-----------");
    };

    @Test
    public void testDeleteSong() {
        songService.delete("001");
        System.out.println("-----------测试成功-----------");
    };


}
