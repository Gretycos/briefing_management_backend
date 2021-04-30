package com.briefing_management.gather.service;

import com.briefing_management.gather.dao.GatherMapper;
import com.briefing_management.gather.model.Gather;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatherServiceImpl implements GatherService{
    @Resource
    GatherMapper gatherMapper;

    @Override
    public List<Gather> getGatherByPage(int pageOffset, int pageSize, int order) {
        return gatherMapper.getGatherByPage(pageOffset,pageSize,order);
    }

    @Override
    public int getGatherNum() {
        return gatherMapper.getGatherNum();
    }
}
