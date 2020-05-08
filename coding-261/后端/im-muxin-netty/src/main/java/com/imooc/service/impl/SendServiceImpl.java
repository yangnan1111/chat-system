package com.imooc.service.impl;

import client.makecall;
import com.imooc.mapper.ScheduledMapper;
import com.imooc.pojo.Scheduled;
import com.imooc.service.ScheduleJobService;
import com.imooc.service.SendService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class SendServiceImpl implements SendService {
    @Autowired
    private ScheduledMapper scheduledMapper;
    @Autowired
    private ScheduleJobService scheduleJobService;
    @Override
    public void send(Scheduled scheduled) throws ParseException, SchedulerException {
        scheduledMapper.insert(scheduled);
        scheduleJobService.addJob(scheduled);
//        Scheduled cron = scheduledMapper.getCron("freeswitch-12");
//        System.out.println(cron.getClassName());
    }

}
