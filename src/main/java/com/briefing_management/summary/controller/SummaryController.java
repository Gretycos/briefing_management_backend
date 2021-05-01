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

    @RequestMapping(value = "/get/topic/state",method = RequestMethod.POST)
    public Object getTopicState(){
        Result result = new Result();
        try{
            result.setData(summaryService.getTopicState());
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/generate/topic",method = RequestMethod.POST)
    public Object generateTopic(){
        Result result = new Result();
        try{
            int state = summaryService.generateTopic();
            if (state == 1){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("生成成功");
            } else if (state == 2){
                result.setCode(ResultCode.ERROR);
                result.setMsg("生成主题前应先爬取数据");
            } else {
                result.setCode(ResultCode.ERROR);
                result.setMsg("生成错误");
            }
            result.setData(state);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/get/summary/state",method = RequestMethod.POST)
    public Object getSummaryState(){
        Result result = new Result();
        try{
            result.setData(summaryService.getSummaryState());
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/generate/summary",method = RequestMethod.POST)
    public Object generateSummary(){
        Result result = new Result();
        try{
            int state = summaryService.generateSummary();
            if (state == 1){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("生成成功");
            } else if (state == 2){
                result.setCode(ResultCode.ERROR);
                result.setMsg("生成摘要前应先生成主题");
            } else {
                result.setCode(ResultCode.ERROR);
                result.setMsg("生成错误");
            }
            result.setData(state);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }
}
