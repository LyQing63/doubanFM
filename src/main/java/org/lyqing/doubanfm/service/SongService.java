package org.lyqing.doubanfm.service;

import com.github.pagehelper.Page;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;

public interface SongService {

    void addSong(Song song);

    Song getSong(String songId);

    Page<Song> list(SongQueryParam songParam);

    void modify(Song song);

    void delete(String songId);
}
