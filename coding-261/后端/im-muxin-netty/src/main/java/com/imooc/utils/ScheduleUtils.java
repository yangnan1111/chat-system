package com.imooc.utils;

import com.imooc.pojo.FreeSwitchJob;
import com.imooc.pojo.Scheduled;
import org.quartz.Job;

public class ScheduleUtils {
    public static Class<? extends Job> getcclass(Scheduled scheduled){
        return FreeSwitchJob.class;
    }
}
