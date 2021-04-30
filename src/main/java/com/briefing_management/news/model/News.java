package com.briefing_management.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    String articleId;
    String media;
    String url;
    String title;
    String content;
    String publishTime;
    String  images; // 数据库中是json格式
}
