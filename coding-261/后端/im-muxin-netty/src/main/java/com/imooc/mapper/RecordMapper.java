package com.imooc.mapper;

import com.imooc.pojo.Record;

import com.imooc.pojo.vo.SendRecord;
import com.imooc.utils.MyMapper;

import java.util.List;

public interface RecordMapper extends MyMapper<Record> {
    public String selecturl(String id);
    public List<SendRecord> selectsendrecord(String phone);
}
