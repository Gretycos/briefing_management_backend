package com.briefing_management.news.service;

import com.alibaba.fastjson.JSON;
import com.briefing_management.news.dao.NewsMapper;
import com.briefing_management.news.model.News;
import com.briefing_management.summary.service.SummaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService{
    @Resource
    NewsMapper newsMapper;
    @Resource
    SummaryService summaryService;

    @Override
    public List<News> getNewsByPage(int pageOffset, int pageSize, int order) {
        List<News> newsList = newsMapper.getNewsByPage(pageOffset,pageSize,order);
        for (News news: newsList){
            List<String> imageList = (List<String>) JSON.parse(news.getImages());
            if (!imageList.isEmpty()){
                imageList.replaceAll(url ->
                        "/"+url.split("/")[6]+"/"+url.split("/")[7]+"/"+url.split("/")[8]);
                news.setImages(JSON.toJSONString(imageList));
            }
        }
        return newsList;
    }

    @Override
    public int getNewsNum() {
        return newsMapper.getNewsNum();
    }

    @Override
    public Map<String, String> getSpiderTime() {
        String command = "sed -n '/newsdata/p' root";
        String[] commands = {"/bin/sh","-c",command};
        Process process = null;
        Map<String, String> time = new HashMap<>();
        try {
            process = Runtime.getRuntime().exec(commands, null,
                    // 文件所在的路径
//                    new File("/Users/huangyaocong/Desktop/")
                    new File("/var/spool/cron/crontabs/")
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            // getInputStream()获取命令的输出
                            process.getInputStream()
                    )
            );
            String data = "";
            String line;
            while((line = reader.readLine()) != null) {
                data += line;
                System.out.println(data);
            }
            String[] results = data.split(" ");
            time.put("minute",results[0]);
            time.put("hour",results[1]);

            // waitFor()命令执行成功返回0
            int exitValue = process.waitFor();

            if(exitValue != 0) {
                System.out.println("error");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    @Override
    public boolean updateSpiderTime(String minute, String hour) {
        // mac
//        String command = "sed -i '' 's/^.*newsdata.*$/" + minute + " " + hour +
//                " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/newsdata\\/newsdata \\&\\& nohup python3 main.py 1\\>spider.log 2\\>\\&1 \\&/' root";
        String command = "sed -i 's/^.*newsdata.*$/" + minute + " " + hour +
                " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/newsdata\\/newsdata \\&\\& nohup python3 main.py 1\\>spider.log 2\\>\\&1 \\&/' root";
        String[] commands = {"/bin/sh","-c",command};
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(commands, null,
                    // 文件所在的路径
//                    new File("/Users/huangyaocong/Desktop/")
                    new File("/var/spool/cron/crontabs/")
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            // getInputStream()获取命令的输出
                            process.getInputStream()
                    )
            );
            String data = "";
            while((data = reader.readLine()) != null) {
                System.out.println(data);
            }

            // waitFor()命令执行成功返回0
            int exitValue = process.waitFor();

            if(exitValue != 0) {
                System.out.println("updateSpiderTime, error");
                return false;
            }
            return summaryService.updateTopicDiscoveryTime(Integer.parseInt(minute)+5+"", hour)
                    && summaryService.updateSummaryGenerationTime(Integer.parseInt(minute)+15+"", hour);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getNewsState() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return newsMapper.getNewsState(today) > 0;
    }

    @Override
    public boolean getNewsStateMore() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return newsMapper.getNewsState(today) > 20;
    }

    @Override
    public int generateNews() {
        String command = "python3 main.py 1>spider.log 2>&1";
        String[] commands = {"/bin/sh","-c",command};
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(commands, null,
                    // 文件所在的路径
//                    new File("/Users/huangyaocong/PycharmProjects/newsdata/newsdata/")
                    new File("/home/huangyaocong/PythonProjects/newsdata/newsdata/")
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            // getInputStream()获取命令的输出
                            process.getInputStream()
                    )
            );
            String data = "";
            while((data = reader.readLine()) != null) {
                System.out.println(data);
            }

            // waitFor()命令执行成功返回0
            int exitValue = process.waitFor();

            if(exitValue != 0) {
                System.out.println("error");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
