package com.briefing_management.gather.dao;

import com.briefing_management.gather.model.Gather;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GatherMapper {
    @Select("<script>" +
            "select * from news_info " +
            "<if test='order==0'>" +
            "order by publish_date desc " +
            "</if>" +
            "<if test='order==1'>" +
            "order by publish_date asc " +
            "</if>" +
            "limit #{pageOffset},#{pageSize}" +
            "</script>")
    List<Gather> getGatherByPage(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize, @Param("order") int order);

    @Select("select count(*) from news_info")
    int getGatherNum();
}
