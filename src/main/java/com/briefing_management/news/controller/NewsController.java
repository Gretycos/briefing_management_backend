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

    @RequestMapping(value = "/get/time",method = RequestMethod.POST)
    public Object getSpiderTime(){
        Result result = new Result();
        try{
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            result.setData(newsService.getSpiderTime());
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/update/time",method = RequestMethod.POST)
    public Object updateSpiderTime(@RequestParam Map<String,String> time){
        Result result = new Result();
        try{
            boolean state = newsService.updateSpiderTime(time.get("minute"),time.get("hour"));
            if (state){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("修改成功");
                result.setData(1);
            } else {
                result.setCode(ResultCode.ERROR);
                result.setMsg("服务器异常");
                result.setData(0);
            }
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/get/news/state",method = RequestMethod.POST)
    public Object getNewsState(){
        Result result = new Result();
        try{
            result.setData(newsService.getNewsState());
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }

    @RequestMapping(value = "/generate/news",method = RequestMethod.POST)
    public Object generateNews(){
        Result result = new Result();
        try{
            int state = newsService.generateNews();
            if (state == 1) {
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("爬取成功");
            } else {
                result.setCode(ResultCode.ERROR);
                result.setMsg("爬取异常");
            }
            result.setData(state);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR);
            result.setMsg("服务器错误");
        }
        return result;
    }
}
