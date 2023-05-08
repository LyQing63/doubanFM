package org.lyqing.doubanfm.service;

import org.lyqing.doubanfm.pojo.Subject;

import java.util.List;

public interface SubjectService {

    /**
     * 添加主题
     * */
    void addSubject(Subject subject);

    /**
     * 获得主题
     * */
    Subject getSubject(String subjectId);

    /**
     * 一级主题搜索
     * */
    List<Subject> getSubjects(String type);

    /**
     * 二级主题搜索
     * */
    List<Subject> getSubjects(String type, String subType);

    /**
     * 删除主题
     * */
    void deleteSubject(String subjectId);
}
