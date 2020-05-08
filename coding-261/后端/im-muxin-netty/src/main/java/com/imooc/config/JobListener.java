package com.imooc.config;

import com.imooc.mapper.ScheduledMapper;
import com.imooc.pojo.Scheduled;
import com.imooc.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * 添加监听器
 */
@Configuration
public class JobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ScheduleJobService scheduleJobService;
    @Autowired
    ScheduledMapper scheduledMapper;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        List<Scheduled> tasks = scheduledMapper.selectAll();
        System.out.println("task size = "+tasks.size());
        try {
            for (Scheduled task : tasks) {
                scheduleJobService.addJob(task);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
