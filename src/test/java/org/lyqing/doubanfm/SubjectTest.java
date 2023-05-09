package org.lyqing.doubanfm;

import org.junit.Test;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectTest {

    @Autowired
    private SubjectService subjectService;

    @Test
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
        System.out.println("-----------测试成功-----------");
        System.out.println(subject);
        return subject;
    }

    @Test
    public Subject testGetSubject(@PathVariable("id") String id) {
        Subject subject = subjectService.getSubject(id);
        System.out.println("-----------测试成功-----------");
        return subject;
    };


    @Test
    public void testDeleteSubject(@PathVariable("id") String id) {
        subjectService.deleteSubject(id);
        System.out.println("-----------测试成功-----------");
    };

    @Test
    public List<Subject> testGetSubjectByType(@PathVariable("type") String type) {
        List<Subject> subjects = subjectService.getSubjects(type);
        System.out.println("-----------测试成功-----------");
        System.out.println(subjects);
        return subjects;
    }

    @Test
    public List<Subject> testGetSubjectBySubType(@PathVariable("type") String type, @PathVariable("subType") String subType) {
        List<Subject> subjects = subjectService.getSubjects(type, subType);
        System.out.println("-----------测试成功-----------");
        return subjects;
    };

}
