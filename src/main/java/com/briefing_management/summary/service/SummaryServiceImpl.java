package com.briefing_management.summary.service;

import com.briefing_management.summary.dao.SummaryMapper;
import com.briefing_management.summary.model.Summary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService{
    @Resource
    SummaryMapper summaryMapper;

    @Override
    public List<Summary> getSummaryByPage(int pageOffset, int pageSize, int order) {
        return summaryMapper.getSummaryByPage(pageOffset,pageSize,order);
    }

    @Override
    public int getSummaryNum() {
        return summaryMapper.getSummaryNum();
    }
}
