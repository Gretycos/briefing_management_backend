package com.briefing_management.news.service;

import com.briefing_management.news.model.News;

import java.util.List;

public interface NewsService {
    List<News> getNewsByPage(int pageOffset, int pageSize, int order);

    int getNewsNum();
}
