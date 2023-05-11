package org.lyqing.doubanfm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.SubjectUtil;
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
public class SubjectTest {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void testAddSubject() {
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
    }

    @Test
    public void testGetSubject() {
        Subject subject = subjectService.getSubject("001");
        System.out.println("-----------测试成功-----------");
        System.out.println(subject);
    };


    @Test
    public void testDeleteSubject() {
        subjectService.deleteSubject("001");
        System.out.println("-----------测试成功-----------");
    };

    @Test
    public void testGetSubjectByType() {
        List<Subject> subjects = subjectService.getSubjects("mhz");
        System.out.println("-----------测试成功-----------");
        System.out.println(subjects);
    }

    @Test
    public void testGetSubjectBySubType() {
        List<Subject> subjects = subjectService.getSubjects("mhz", "artist");
        System.out.println("-----------测试成功-----------");
        System.out.println(subjects);
    };

}
