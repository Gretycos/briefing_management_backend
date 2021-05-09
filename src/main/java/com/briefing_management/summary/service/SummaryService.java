package com.briefing_management.summary.service;

import com.briefing_management.summary.model.Summary;

import java.util.List;

public interface SummaryService {
    List<Summary> getSummaryByPage(int pageOffset,int pageSize,int order);

    int getSummaryNum();

    boolean getTopicState();

    int generateTopic();

    boolean updateTopicDiscoveryTime(String minute, String hour);

    boolean getSummaryState();

    int generateSummary();

    boolean updateSummaryGenerationTime(String minute, String hour);

    int updateSummary(String articleId, String newContent);
}
