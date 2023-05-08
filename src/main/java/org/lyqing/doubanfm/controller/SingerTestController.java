package org.lyqing.doubanfm.controller;

import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController("/singer")
public class SingerTestController {

    @Autowired
    private SingerService singerService;

    @GetMapping("/add")
    public Singer testAddSinger() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<String> similar = new ArrayList<>();
        similar.add("002");
        similar.add("003");
        Singer singer = new Singer("004", dateTime, "cxk2",
                "123",
                "123", similar);
        singerService.addSinger(singer);
        return singer;
    };

    @GetMapping("/getAll")
    public List<Singer> testGetAll() {
        return singerService.getAll();
    };

    @GetMapping("/get/id/{id}")
    public Singer testGetSinger(@PathVariable("id") String id) {
        return singerService.getSinger(id);
    };

    @GetMapping("/modify")
    public Singer testModifySinger() {
        List<String> similar = new ArrayList<>();
        similar.add("002");
        similar.add("003");
        Singer singer = new Singer("001", LocalDateTime.now(), "cxk", "223", "113", similar);
        singerService.modify(singer);
        return singer;
    };

    @GetMapping("/delete/id/{id}")
    public void testDeleteSinger(@PathVariable("id") String id) {
        singerService.delete(id);
    };

}
