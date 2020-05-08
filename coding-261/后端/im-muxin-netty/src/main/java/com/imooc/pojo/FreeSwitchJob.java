package com.imooc.pojo;

import client.makecall;
import com.imooc.mapper.ScheduledMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.time.LocalDateTime;

public class FreeSwitchJob implements BaseJob {
    @Autowired
    private ScheduledMapper scheduledMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobKey key = jobDetail.getKey();
        System.out.println(key.getName()+"-"+key.getGroup()+"执行时间: " + LocalDateTime.now().toString());
        String name = key.getName();
        Scheduled scheduled = scheduledMapper.getCron(name);
        doTask(scheduled);
    }

    public void doTask(Scheduled scheduled){
        makecall makecall=new makecall();
        makecall.call(scheduled.getWav(),scheduled.getSend_phone());
    }
}
