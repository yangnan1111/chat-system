package com.imooc.service;

import com.imooc.pojo.Record;
import com.imooc.pojo.vo.SendRecord;

import java.util.List;

public interface RecordService {
    public void wav(Record record);
    public String selecturl(String id);
    public List<SendRecord> selectsendrecord(String phone);
}
