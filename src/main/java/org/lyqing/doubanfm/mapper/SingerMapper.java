package org.lyqing.doubanfm.mapper;

import org.apache.ibatis.annotations.*;
import org.lyqing.doubanfm.pojo.Singer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SingerMapper {

    @Insert(
            "INSERT INTO singer VALUES (#{singer.id}, #{singer.gmtCreated}, #{singer.name}, #{singer.avatar}, #{singer.homepage})"
    )
    void addSinger(@Param("singer") Singer singer);

    @Insert(
            "INSERT INTO singer_similar VALUES (#{singerId}, #{similarId})"
    )
    void addSimilarSinger(@Param("singerId") String singerId, @Param("similarId") String similarityId);

    @Select(
            "SELECT * FROM singer WHERE id = #{singerId}"
    )
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "singerName"),
            @Result(property = "gmtCreated", column = "gmtCreated"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "homepage", column = "homepage")
    })
    Singer getSinger(@Param("singerId") String singerId);

    @Select(
            "SELECT similar_id FROM singer_similar WHERE singer_id = #{singerId}"
    )

    List<String> getSimilarSinger(@Param("singerId") String singerId);

    @Select(
            "SELECT * FROM singer"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "singerName"),
            @Result(property = "gmtCreated", column = "gmtCreated"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "homepage", column = "homepage"),
    })
    List<Singer> getAll();

    @Update(
            "UPDATE singer SET singerName=#{singer.name}, avatar=#{singer.avatar}, homepage=#{singer.homepage} WHERE id = #{singer.id}"
    )
    void modify(@Param("singer") Singer singer);

    @Update(
            "UPDATE singer_similar SET similar_id=#{similarId} WHERE singer_id = #{singerId}"
    )
    void similarModify(@Param("singerId") String singerId, @Param("similarId") String similarId);
    @Delete(
            "DELETE FROM singer WHERE id = #{singerId}"
    )
    void delete(@Param("singerId") String singerId);

    @Delete(

            "DELETE FROM singer_similar WHERE singer_id = #{singerId}"
    )
    void similarDelete(@Param("singerId") String singerId);
}
