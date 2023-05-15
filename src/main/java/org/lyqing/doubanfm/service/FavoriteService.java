package org.lyqing.doubanfm.service;

import org.lyqing.doubanfm.pojo.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite add(Favorite fav);

    List<Favorite> list(Favorite favParam);

    boolean delete(Favorite favParam);

}
