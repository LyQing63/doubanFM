package org.lyqing.doubanfm.service;

import org.lyqing.doubanfm.pojo.Singer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SingerService {
    void addSinger(Singer singer);
    Singer getSinger(String singerId);
    List<Singer> getAll();
    void modify(Singer singer);
    void delete(String singerId);
}
