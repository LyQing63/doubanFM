package org.lyqing.doubanfm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lyqing.doubanfm.mapper.SongMapper;
import org.lyqing.doubanfm.param.SongQueryParam;
import org.lyqing.doubanfm.pojo.Song;
import org.lyqing.doubanfm.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public void addSong(Song song) {
        if (getSong(song.getId()) == null) {
            songMapper.addSong(song);
            List<String> singerIds = song.getSingerId();
            for (String singerId : singerIds) {
                songMapper.addSongSingers(song.getId(), singerId);
            }
        }
    }

    @Override
    public Song getSong(String songId) {
        Song song = songMapper.getSong(songId);
        if (song == null) {
            return null;
        }
        List<String> singerIds = songMapper.getSongSingers(songId);
        song.setSingerId(singerIds);
        return song;
    }

    @Override
    public Page<Song> list(SongQueryParam songParam) {
        Page<Song> page = PageHelper.startPage(songParam.getPageNum(), songParam.getPageSize());
        return page;
    }

    @Override
    public void modify(Song song) {
        if (getSong(song.getId()) == null) {
            songMapper.modify(song);
            List<String> singerIds = song.getSingerId();
            for (String singerId : singerIds) {
                songMapper.SingerModify(song.getId(), singerId);
            }
        }
    }

    @Override
    public void delete(String songId) {
        if (getSong(songId) == null) {
            songMapper.delete(songId);
            songMapper.SingerDelete(songId);
        }
    }
}
