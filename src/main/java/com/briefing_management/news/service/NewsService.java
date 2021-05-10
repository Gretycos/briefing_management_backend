package com.briefing_management.news.service;

import com.briefing_management.news.model.News;

import java.util.List;
import java.util.Map;

public interface NewsService {
    List<News> getNewsByPage(int pageOffset, int pageSize, int order);

    int getNewsNum();

    Map<String,String> getSpiderTime();

    boolean updateSpiderTime(String minute, String hour);

    boolean getNewsState();

    boolean getNewsStateMore();

    int generateNews();
}
