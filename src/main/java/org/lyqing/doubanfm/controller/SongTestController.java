package org.lyqing.doubanfm.controller;

import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController("/song")
public class SongTestController {

    @Autowired
    private SongService songService;

    @GetMapping("/add")
    public Song testAddSong() {
        List<String> singers = new ArrayList<>();
        singers.add("002");
        singers.add("003");
        Song song = new Song("001", LocalDateTime.now(), LocalDateTime.now(), "qwer", "asdfsa", "asdf", "ss", singers);
        songService.addSong(song);
        return song;
    };

    @GetMapping("/getAll/num/{num}")
    public List<Song> testGetAllSong(@PathVariable("num") int num) {
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(num);
        return songService.list(songQueryParam);
    };

    @GetMapping("/get/id/{id}")
    public Song testGetSong(@PathVariable("id") String id) {
        return songService.getSong(id);
    };

    @GetMapping("/modify")
    public Song testModifySong() {
        List<String> similar = new ArrayList<>();
        Collections.addAll(similar, "002", "003");
        Song song = new Song("001", LocalDateTime.now(),LocalDateTime.now(), "cxk", "223", "113", "url",similar);
        songService.modify(song);
        return song;
    };

    @GetMapping("/delete/id/{id}")
    public void testDeleteSong(@PathVariable("id") String id) {
        songService.delete(id);
    };


}
