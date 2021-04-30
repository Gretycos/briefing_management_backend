package com.briefing_management.news.controller;

import com.briefing_management.configuration.util.Result;
import com.briefing_management.configuration.util.ResultCode;
import com.briefing_management.news.service.NewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    NewsService newsService;

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    public Object getNews(@RequestParam Map<String,String> page){
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
            resultMap.put("newsList",newsService.getNewsByPage(pageOffset,pageSize,order));
            resultMap.put("total",newsService.getNewsNum());
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
