package com.imooc.service;

import com.imooc.pojo.Scheduled;
import org.quartz.SchedulerException;

public interface ScheduleJobService {
    String addJob(Scheduled scheduled) throws SchedulerException;
}
