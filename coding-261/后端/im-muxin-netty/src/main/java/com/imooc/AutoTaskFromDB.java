package com.imooc;

import com.imooc.mapper.ScheduledMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

public class AutoTaskFromDB implements SchedulingConfigurer {

    @Autowired
    public ScheduledMapper scheduledMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

//        scheduledTaskRegistrar.addTriggerTask(() -> process(),
//                triggerContext -> {
////                    String cron = scheduledMapper.getCron(1);
////                    if (cron.isEmpty()) {
//////                        log.info("cron 为空");
////                    }
////                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
////                }
//        );
    }

    private  void process(){
//        log.info("formDB ");
        System.out.println("打电话");
    }
}
