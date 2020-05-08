package com.imooc.service;


import com.imooc.pojo.Scheduled;
import org.quartz.SchedulerException;

import java.text.ParseException;

public interface SendService {
    public void send(Scheduled scheduled) throws ParseException, SchedulerException;
}
