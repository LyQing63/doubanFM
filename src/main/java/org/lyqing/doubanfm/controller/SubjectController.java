package org.lyqing.doubanfm.controller;

import org.apache.ibatis.annotations.Delete;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/artist/{id}")
    public String mhzDetail(Model model, @PathVariable("id") String subjectId) {
        Subject subject = subjectService.getSubject(subjectId);

        model.addAttribute("subject", subject);
        model.addAttribute("songs", subject.getSongIds());

        return "mhzdetail";
    }

    @GetMapping("/collection")
    @ResponseBody
    public Map mhzCollection(Model model) {

        Map result = new HashMap<>();

        List<Subject> subjects = subjectService.getAll();
        result.put("subjects", subjects);

        return result;

    }

    @GetMapping("/collectionDetail/{id}")
    @ResponseBody
    public Map collectionDetail(Model model, @PathVariable("id") String subjectId) {

        Map result = new HashMap<>();

        Subject subject = subjectService.getSubject(subjectId);
        result.put("subject", subject);

        return result;

    }

}
