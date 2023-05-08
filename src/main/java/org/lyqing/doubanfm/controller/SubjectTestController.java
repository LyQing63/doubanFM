package org.lyqing.doubanfm.controller;

import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController("/subject")
public class SubjectTestController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/add")
    public Subject testAddSubject() {
        List<String> songs = new ArrayList<>();
        Collections.addAll(songs, "001", "002");
        Subject subject = new Subject(
                "001",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                "abcd", "asdf", "url1", "cxk", songs,
                SubjectUtil.TYPE_MHZ, SubjectUtil.TYPE_SUB_MOOD
        );
        subjectService.addSubject(subject);
        return subject;
    }

    @GetMapping("/get/id/{id}")
    public Subject testGetSubject(@PathVariable("id") String id) {
        return subjectService.getSubject(id);
    };


    @GetMapping("/delete/{id}")
    public void testDeleteSubject(@PathVariable("id") String id) {
        subjectService.deleteSubject(id);
    };

    @GetMapping("/get/type/{type}")
    public List<Subject> testGetSubjectByType(@PathVariable("type") String type) {
        List<Subject> subjects = subjectService.getSubjects(type);
        return subjects;
    }

    @GetMapping("/get/type/{type}/subType/{subType}")
    public List<Subject> testGetSubjectBySubType(@PathVariable("type") String type, @PathVariable("subType") String subType) {
        return subjectService.getSubjects(type, subType);
    };

}
