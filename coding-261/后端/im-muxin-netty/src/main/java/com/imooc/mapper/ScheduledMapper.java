package com.imooc.mapper;

import com.imooc.pojo.ChatMsg;
import com.imooc.pojo.Scheduled;
import com.imooc.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yang
 */
public interface ScheduledMapper extends MyMapper<Scheduled> {


    public Scheduled getCron(String clazzname);
}
