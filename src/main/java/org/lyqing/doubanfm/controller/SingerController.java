package org.lyqing.doubanfm.controller;

import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class SingerController {

    @Autowired
    private SingerService singerService;

    @GetMapping("/user-guide")
    public String myMhz(Model model) {

        List<Singer> singers = randomSingers();
        model.addAttribute("singers", singers);

        return "userguide";
    }

    @GetMapping("/singer/random")
    @ResponseBody
    public List<Singer> randomSingers() {

        List<Singer> singerList = singerService.getAll();
        List<Singer> randomSingers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            randomSingers.add(singerList.get(random.nextInt(singerList.size())));
        }

        return randomSingers;
    }
}
