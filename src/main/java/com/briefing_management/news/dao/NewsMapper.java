package com.briefing_management.news.dao;

import com.briefing_management.news.model.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsMapper {
    @Select("<script>" +
            "select * from news " +
            "<if test='order==0'>" +
            "order by publish_time desc " +
            "</if>" +
            "<if test='order==1'>" +
            "order by publish_time asc " +
            "</if>" +
            "limit #{pageOffset},#{pageSize}" +
            "</script>")
    List<News> getNewsByPage(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize, @Param("order") int order);

    @Select("select count(*) from news")
    int getNewsNum();
}
