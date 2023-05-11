package org.lyqing.doubanfm.service.impl;

import org.lyqing.doubanfm.mapper.SingerMapper;
import org.lyqing.doubanfm.pojo.Singer;
import org.lyqing.doubanfm.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper mapper;

    @Override
    public void addSinger(Singer singer) {
        if (getSinger(singer.getId()) == null) {
            mapper.addSinger(singer);
            List<String> similarSingerIds = singer.getSimilarSingerIds();
            if (similarSingerIds == null) {
                return;
            }
            for (String similarSingerId : similarSingerIds) {
                mapper.addSimilarSinger(singer.getId(), similarSingerId);
            }
        } else {
            mapper.modify(singer);
        }
    }

    @Override
    public Singer getSinger(String singerId) {
        Singer singer = mapper.getSinger(singerId);
        if (singer == null) {
            return null;
        }
        List<String> similarSinger = mapper.getSimilarSinger(singerId);
        singer.setSimilarSingerIds(similarSinger);
        return singer;
    }

    @Override
    public List<Singer> getAll() {
        return mapper.getAll();
    }

    @Override
    public void modify(Singer singer) {
        if (getSinger(singer.getId()) == null) {
            mapper.modify(singer);
            List<String> similarSingerIds = singer.getSimilarSingerIds();
            if (similarSingerIds != null) {
                for (String similarSingerId : similarSingerIds) {
                    mapper.similarModify(singer.getId(), similarSingerId);
                }
            }

        }
    }

    @Override
    public void delete(String singerId) {
        if (getSinger(singerId) != null) {
            mapper.delete(singerId);
            mapper.similarDelete(singerId);
        }

    }
}
