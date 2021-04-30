package com.briefing_management.gather.service;

import com.briefing_management.gather.model.Gather;

import java.util.List;

public interface GatherService {
    List<Gather> getGatherByPage(int pageOffset, int pageSize, int order);
    int getGatherNum();
}
