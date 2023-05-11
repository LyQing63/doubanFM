package org.lyqing.doubanfm.mapper;

import org.apache.ibatis.annotations.*;
import org.lyqing.doubanfm.pojo.Subject;

import java.util.List;

@Mapper
public interface SubjectMapper {

    /**
     * 添加主题
     * */
    @Insert(
            "INSERT INTO subject VALUES (#{subject.id}, #{subject.gmtCreated}, #{subject.gmtModified}, #{subject.publishedDate}, #{subject.name}, #{subject.description}, #{subject.cover}, #{subject.master}, #{subject.subjectType}, #{subject.subjectSubType})"
    )
    void addSubject(@Param("subject") Subject subject);

    @Insert(
            "INSERT INTO subject_songs VALUES (#{subjectId}, #{songId})"
    )
    void addSubjectSongs(@Param("subjectId") String subjectId, @Param("songId") String songId);

    /**
     * 获得主题
     * */
    @Select(
            "SELECT * FROM subject WHERE id=#{subjectId}"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "gmtCreated", column = "gmtCreated"),
            @Result(property = "gmtModified", column = "gmtModified"),
            @Result(property = "publishedDate", column = "publishedDate"),
            @Result(property = "name", column = "subjectName"),
            @Result(property = "description", column = "description"),
            @Result(property = "cover", column = "cover"),
            @Result(property = "master", column = "master"),
            @Result(property = "subjectType", column = "subjectType"),
            @Result(property = "subjectSubType", column = "subjectSubType")
    })
    Subject getSubject(@Param("subjectId") String subjectId);

    @Select(
            "SELECT song_id FROM subject_songs WHERE subject_id=#{subjectId}"
    )
    List<String> getSubjectSongs(@Param("subjectId") String subjectId);

    /**
     * 一级主题搜索
     * */
    @Select(
            "SELECT * FROM subject WHERE subjectType=#{type}"
    )
    List<Subject> getSubjects(@Param("type") String type);

    /**
     * 二级主题搜索
     * */
    @Select(
            "SELECT * FROM subject WHERE subjectSubType=#{subType} AND subjectType=#{type}"
    )
    List<Subject> getSubjectsSub(@Param("type") String type, @Param("subType") String subType);

    /**
     * 删除主题
     * */
    @Delete(
            "DELETE FROM subject WHERE id=#{subjectId}"
    )
    void deleteSubject(@Param("subjectId") String subjectId);

    @Delete(
            "DELETE FROM subject_songs WHERE subject_id=#{subjectId}"
    )
    void deleteSubjectSongIds(@Param("subjectId")String subjectId);

    @Update(
            "UPDATE subject SET gmtModified=#{subject.gmtModified} WHERE id=#{subject.id}"
    )
    void modify(@Param("subject") Subject subject);

    @Update(
            "UPDATE subject_songs SET song_id=#{songId} WHERE subject_id=#{subjectId}"
    )
    void modifySubjectSong(@Param("subjectId") String subjectId, @Param("songId") String songId);

}
