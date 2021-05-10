package com.briefing_management.summary.service;

import com.briefing_management.news.service.NewsService;
import com.briefing_management.summary.dao.SummaryMapper;
import com.briefing_management.summary.model.Summary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService{
    @Resource
    SummaryMapper summaryMapper;

    @Resource
    NewsService newsService;

    @Override
    public List<Summary> getSummaryByPage(int pageOffset, int pageSize, int order) {
        return summaryMapper.getSummaryByPage(pageOffset,pageSize,order);
    }

    @Override
    public int getSummaryNum() {
        return summaryMapper.getSummaryNum();
    }

    @Override
    public boolean getTopicState() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        File file = new File("/Users/huangyaocong/PycharmProjects/TopicDiscovery/predict/top/"+today+".json");
        File file = new File("/home/huangyaocong/PythonProjects/TopicDiscovery/predict/top/"+today+".json");
        return file.exists();
    }

    @Override
    public int generateTopic() {
        if (newsService.getNewsStateMore()){
            String command = "sh run.sh -o -d 0 1>topic.log 2>&1";
            String[] commands = {"/bin/sh","-c",command};
            Process process = null;

            try {
                process = Runtime.getRuntime().exec(commands, null,
                        // 文件所在的路径
//                        new File("/Users/huangyaocong/PycharmProjects/TopicDiscovery/")
                        new File("/home/huangyaocong/PythonProjects/TopicDiscovery/")
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
        return 2;
    }

    @Override
    public boolean updateTopicDiscoveryTime(String minute, String hour) {
        String command = "sed -i 's/^.*TopicDiscovery.*$/" + minute + " " + hour +
                " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/TopicDiscovery \\&\\& nohup sh run.sh \\-o \\-d 0 1\\>topic.log 2\\>\\&1 \\&/' root";
        if (Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=8){
            command = "sed -i 's/^.*TopicDiscovery.*$/" + minute + " " + hour +
                    " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/TopicDiscovery \\&\\& nohup sh run.sh \\-o \\-d 1 1\\>topic.log 2\\>\\&1 \\&/' root";
        }
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
                System.out.println("updateTopicDiscoveryTime, error");
                return false;
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getSummaryState() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return summaryMapper.getSummaryState(today) > 0;
    }

    @Override
    public int generateSummary() {
        if (getTopicState()){
            String command = "sh run.sh -g -d 0 1>generate.log 2>&1";
            String[] commands = {"/bin/sh","-c",command};
            Process process = null;

            try {
                process = Runtime.getRuntime().exec(commands, null,
                        // 文件所在的路径
//                        new File("/Users/huangyaocong/PycharmProjects/SingleDocSum/")
                        new File("/home/huangyaocong/PythonProjects/SingleDocSum/")
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
        return 2;
    }

    @Override
    public boolean updateSummaryGenerationTime(String minute, String hour) {
        String command = "sed -i 's/^.*SingleDocSum.*$/" + minute + " " + hour +
                " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/SingleDocSum \\&\\& nohup sh run.sh \\-g \\-d 0 1\\>generate.log 2\\>\\&1 \\&/' root";
        if (Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=8){
            command = "sed -i 's/^.*SingleDocSum.*$/" + minute + " " + hour +
                    " * * * cd \\/home\\/huangyaocong\\/PythonProjects\\/SingleDocSum \\&\\& nohup sh run.sh \\-g \\-d 1 1\\>generate.log 2\\>\\&1 \\&/' root";
        }
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
                System.out.println("updateTopicDiscoveryTime, error");
                return false;
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int updateSummary(String articleId, String newContent) {
        return summaryMapper.updateSummary(articleId,newContent);
    }
}
