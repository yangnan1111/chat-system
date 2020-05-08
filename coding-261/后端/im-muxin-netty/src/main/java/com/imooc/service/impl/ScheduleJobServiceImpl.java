package com.imooc.service.impl;

import com.imooc.pojo.BaseJob;
import com.imooc.pojo.Scheduled;
import com.imooc.service.ScheduleJobService;
import com.imooc.service.TaskService;
import com.imooc.utils.ScheduleUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;
    //自己选择所用的dao
    @Autowired
    TaskService taskService;

    @Override
    public  String addJob(Scheduled task) throws SchedulerException {
        // 启动调度器
        scheduler.start();

        Class<? extends Job> getcclass = ScheduleUtils.getcclass(task);
        //构建job信息
        JobDetail jobDetail = JobBuilder
                .newJob(getcclass).withIdentity(task.getClassName(), task.getGroup()).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getClassName(), task.getGroup())
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            //taskService.insert(task);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public JobDetail jobPause(String jobName, String jobGroupName) throws Exception
    {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        JobDetail detail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
        return detail;
    }

    public void jobreschedule(String jobName, String jobGroupName, String cronExpression) throws Exception
    {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败"+e);
        }
    }

    public static BaseJob getClass(String classname) throws Exception
    {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }
}
