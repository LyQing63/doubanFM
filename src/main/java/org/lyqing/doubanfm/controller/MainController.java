package org.lyqing.doubanfm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageParams;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.MhzViewModel;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SongService;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private SongService songService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/index")
    public String index(Model model) {

        List<MhzViewModel> mhzViewModels = new ArrayList<>();

        MhzViewModel artistData = new MhzViewModel();
        MhzViewModel moodData = new MhzViewModel();
        MhzViewModel ageData = new MhzViewModel();
        MhzViewModel styleData = new MhzViewModel();

        artistData.setTitle(SubjectUtil.TYPE_SUB_ARTIST);
        moodData.setTitle(SubjectUtil.TYPE_SUB_MOOD);
        ageData.setTitle(SubjectUtil.TYPE_SUB_AGE);
        styleData.setTitle(SubjectUtil.TYPE_SUB_STYLE);

        mhzViewModels.add(getMhzData(artistData));
        mhzViewModels.add(getMhzData(moodData));
        mhzViewModels.add(getMhzData(ageData));
        mhzViewModels.add(getMhzData(styleData));

        model.addAttribute("mhzViewModels", mhzViewModels);

        return "index";
    }

    @GetMapping("/index/mhzData")
    @ResponseBody
    private MhzViewModel getMhzData(MhzViewModel mhzViewModel) {

        List<Subject> subjects = subjectService.getSubjects(SubjectUtil.TYPE_MHZ, mhzViewModel.getTitle());
        mhzViewModel.setSubjects(subjects);
        return mhzViewModel;
    }

    @GetMapping("search")
    public String search(Model model) {

        return "search";

    }

    @GetMapping("/searchContent/{keyword}")
    @ResponseBody
    public Map search(@PathVariable("keyword") String keyword) {

        Map result = new HashMap();
        List<Song> songList = songService.getAll();
        List<Song> songResults = new ArrayList<>();
        for (Song song : songList) {
            if (song.getName().equals(keyword)) {
                songResults.add(song);
            }
        }
        result.put("songs", songResults);

        return result;
    }

}
