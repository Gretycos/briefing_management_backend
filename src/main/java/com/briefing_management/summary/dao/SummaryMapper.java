package com.briefing_management.summary.dao;

import com.briefing_management.summary.model.Summary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SummaryMapper {
    @Select("<script>" +
            "select * from news_summary " +
            "<if test='order==0'>" +
            "order by publish_time desc " +
            "</if>" +
            "<if test='order==1'>" +
            "order by publish_time asc " +
            "</if>" +
            "limit #{pageOffset},#{pageSize}" +
            "</script>")
    List<Summary> getSummaryByPage(@Param("pageOffset") int pageOffset, @Param("pageSize") int pageSize, @Param("order") int order);

    @Select("select count(*) from news_summary")
    int getSummaryNum();

    @Select("select count(*) " +
            "from news_summary " +
            "where publish_time > #{today}")
    int getSummaryState(@Param("today") String today);

    @Update("update news_summary " +
            "set summary = #{newContent} " +
            "where article_id = #{articleId}")
    int updateSummary(@Param("articleId") String articleId,@Param("newContent") String newContent);
}
