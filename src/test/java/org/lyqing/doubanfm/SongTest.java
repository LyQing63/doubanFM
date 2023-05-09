package org.lyqing.doubanfm;

import com.github.pagehelper.Page;
import org.junit.Test;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongTest {

    @Autowired
    private SongService songService;

    @Test
    public Song testAddSong() {
        List<String> singers = new ArrayList<>();
        singers.add("002");
        singers.add("003");
        Song song = new Song("001", LocalDateTime.now(), LocalDateTime.now(), "qwer", "asdfsa", "asdf", "ss", singers);
        songService.addSong(song);
        System.out.println("-----------测试成功-----------");
        System.out.println(song);
        return song;
    };

    @Test
    public List<Song> testGetAllSong(int num) {
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(num);
        Page<Song> songPage = songService.list(songQueryParam);
        System.out.println("-----------测试成功-----------");
        System.out.println(songPage);
        return songPage;
    };

    @Test
    public Song testGetSong(String id) {
        return songService.getSong(id);
    };

    @Test
    public Song testModifySong() {
        List<String> similar = new ArrayList<>();
        Collections.addAll(similar, "002", "003");
        Song song = new Song("001", LocalDateTime.now(),LocalDateTime.now(), "cxk", "223", "113", "url",similar);
        songService.modify(song);
        System.out.println("-----------测试成功-----------");
        return song;
    };

    @Test
    public void testDeleteSong(String id) {
        songService.delete(id);
        System.out.println("-----------测试成功-----------");
    };


}
