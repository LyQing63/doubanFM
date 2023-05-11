package org.lyqing.doubanfm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DoubanFmApplication.class)
public class SingerTest {

    @Autowired
    private SingerService singerService;

    @Test
    public void testAddSinger() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<String> similar = new ArrayList<>();
        similar.add("002");
        similar.add("003");
        Singer singer = new Singer("004", dateTime, "cxk2",
                "123",
                "123", similar);
        singerService.addSinger(singer);
        System.out.println("-----------测试成功-----------");
        System.out.println(singer);
    };

    @Test
    public void testGetAll() {
        List<Singer> singers = singerService.getAll();
        System.out.println("-----------测试成功-----------");
        System.out.println(singers);
    };

    @Test
    public void testGetSinger() {
        Singer singer = singerService.getSinger("004");
        System.out.println("-----------测试成功-----------");
        System.out.println(singer);
    };

    @Test
    public void testModifySinger() {
        List<String> similar = new ArrayList<>();
        similar.add("002");
        similar.add("003");
        Singer singer = new Singer("001", LocalDateTime.now(), "cxk", "223", "113", similar);
        singerService.modify(singer);
        System.out.println("-----------测试成功-----------");
        System.out.println(singer);
    };

    @Test
    public void testDeleteSinger() {
        singerService.delete("004");
        System.out.println("-----------测试成功-----------");
    };

}
