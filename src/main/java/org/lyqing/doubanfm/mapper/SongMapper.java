package org.lyqing.doubanfm.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;

import java.util.List;

@Mapper
public interface SongMapper {

    @Insert(
            "INSERT INTO song VALUES (#{song.id}, #{song.gmtCreated}, #{song.name}, #{song.lyrics}, #{song.cover}, #{song.url}, #{song.singerId}, #{song.gmtModified})"
    )
    void addSong(@Param("song") Song song);

    @Insert(
            "INSERT INTO song_singers VALUES (#{songId}, #{singerId})"
    )
    void addSongSingers (@Param("songId") String songId, @Param("singerId") String singerId);

    @Select(
            "SELECT * FROM song WHERE id = #{songId}"
    )
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "gmtCreated", column = "gmtCreated"),
            @Result(property = "gmtModified", column = "gmtModified"),
            @Result(property = "name", column = "songName"),
            @Result(property = "lyrics", column = "lyrics"),
            @Result(property = "cover", column = "cover"),
            @Result(property = "url", column = "url")
    })
    Song getSong(@Param("songId") String songId);

    @Select(
            "SELECT singer_id FROM song_singers WHERE song_id = #{songId}"
    )
    List<String> getSongSingers(@Param("songId") String songId);

    @Update(
            "UPDATE song SET gmtModified=#{song.gmtCreated}, songName=#{song.name}, gmtCreated=#{gmtModified}, lyrics=#{lyrics}, cover=#{cover}, url=#{url} WHERE id=#{song.id}"
    )
    void modify(@Param("song") Song song);

    @Update(
            "UPDATE song_singers SET singer_id=#{singerId} WHERE song_id=#{songId}"
    )
    void SingerModify(@Param("songId") String songId, @Param("singerId") String singerId);

    @Delete(
            "DELETE FROM song WHERE id = #{songId}"
    )
    void delete(@Param("songId") String songId);

    @Delete(
            "DELETE FROM song_singers WHERE song_id=#{songId}"
    )
    void SingerDelete(@Param("songId") String songId);

    @Select(
            "SELECT * FROM song"
    )
    List<Song> songList();
}
