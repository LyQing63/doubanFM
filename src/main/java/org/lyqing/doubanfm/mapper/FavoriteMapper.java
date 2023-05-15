package org.lyqing.doubanfm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lyqing.doubanfm.pojo.Favorite;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    Favorite add(Favorite fav);

    List<Favorite> list(Favorite favParam);

    boolean delete(Favorite favParam);

}
