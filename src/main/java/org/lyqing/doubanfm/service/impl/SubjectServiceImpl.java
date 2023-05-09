package org.lyqing.doubanfm.service.impl;

import org.lyqing.doubanfm.mapper.SubjectMapper;
import org.lyqing.doubanfm.pojo.Subject;
import org.lyqing.doubanfm.service.SubjectService;
import org.lyqing.doubanfm.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void addSubject(Subject subject) {
        if (subjectMapper.getSubject(subject.getId()) == null) {
            subjectMapper.addSubject(subject);
            List<String> subjectSingerIds = subjectMapper.getSubjectSongs(subject.getId());
            for (String subjectSingerId : subjectSingerIds) {
                subjectMapper.addSubjectSongs(subject.getId(), subjectSingerId);
            }
        }
    }

    @Override
    public Subject getSubject(String subjectId) {
        Subject subject = subjectMapper.getSubject(subjectId);
        if (subject == null) {
            return null;
        }
        List<String> subjectSongs = subjectMapper.getSubjectSongs(subjectId);
        subject.setSongIds(subjectSongs);
        return subject;
    }

    @Override
    public List<Subject> getSubjects(String type) {
        return subjectMapper.getSubjects(type);
    }

    @Override
    public List<Subject> getSubjects(String type, String subType) {
        return subjectMapper.getSubjectsSub(type, subType);
    }

    @Override
    public void deleteSubject(String subjectId) {
        if (subjectMapper.getSubject(subjectId) != null) {
            subjectMapper.deleteSubject(subjectId);
            subjectMapper.deleteSubjectSongIds(subjectId);
        }
    }
}
