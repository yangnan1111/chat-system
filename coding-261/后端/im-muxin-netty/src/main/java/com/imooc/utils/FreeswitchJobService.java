//package com.imooc.utils;
//
//import client.makecall;
//import com.imooc.mapper.ScheduledMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
///**
// * @author Yang
// */
//@Service
//public class FreeswitchJobService {
//    @Autowired
//    private ScheduledMapper scheduledMapper;
//
//    /**
//     * 通过id查找cron表达式
//     * @param id
//     * @return
//     */
//    @Bean
//    public String getCronByID(int id){
//        String cron = scheduledMapper.getCron(id);
//        return cron;
//    }
//    /**
//     * 执行定时任务
//     * @param acceptPhone
//     * @param fileName
//     */
//    @Scheduled(cron = "#{@getCronByID}")
//    public void execute(String acceptPhone,String fileName){
//        makecall makecall=new makecall();
//        makecall.call(fileName,acceptPhone);
//    }
//}
