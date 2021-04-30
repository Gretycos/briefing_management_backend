package com.briefing_management.news.service;

import com.briefing_management.news.dao.NewsMapper;
import com.briefing_management.news.model.News;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{
    @Resource
    NewsMapper newsMapper;

    @Override
    public List<News> getNewsByPage(int pageOffset, int pageSize, int order) {
        return newsMapper.getNewsByPage(pageOffset,pageSize,order);
    }

    @Override
    public int getNewsNum() {
        return newsMapper.getNewsNum();
    }
}
