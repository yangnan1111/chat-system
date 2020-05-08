package com.imooc.service.impl;

import com.imooc.mapper.RecordMapper;
import com.imooc.pojo.Record;
import com.imooc.pojo.vo.SendRecord;
import com.imooc.service.RecordService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RecordService recordService;
    @Override
    public void wav(Record record) {
      recordMapper.insert(record);

    }

    @Override
    public String selecturl(String id) {
        return recordMapper.selecturl(id);
    }

    @Override
    public List<SendRecord> selectsendrecord(String phone) {
        List<SendRecord> sendRecord1=recordMapper.selectsendrecord(phone);
        for(SendRecord sen:sendRecord1) {
            String dd = sen.getCron();
            Date date = DateUtil.getCronToDate(dd);
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ss = s.format(date);
            sen.setCron(ss);
        }
        return sendRecord1;
    }
}
