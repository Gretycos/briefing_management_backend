package com.briefing_management.summary.controller;

import com.briefing_management.configuration.util.Result;
import com.briefing_management.configuration.util.ResultCode;
import com.briefing_management.summary.service.SummaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/summary")
public class SummaryController {
    @Resource
    SummaryService summaryService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Object getSummary(@RequestParam Map<String,String> page){
        Result result = new Result();
        int pageOffset = Integer.parseInt(page.get("pageOffset"));
        int pageSize = Integer.parseInt(page.get("pageSize"));
        int order = Integer.parseInt(page.get("order"));
        if (pageOffset < 0){
            pageOffset = 0;
        }
        if (pageSize < 0){
            pageSize = 20;
        }
        if (order <= 0){
            order = 0;
        }else{
            order = 1;
        }
        try{
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("summaryList",summaryService.getSummaryByPage(pageOffset,pageSize,order));
            resultMap.put("total",summaryService.getSummaryNum());
            result.setData(resultMap);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }
}
