package com.imooc.controller;

import com.aliyuncs.utils.StringUtils;
import com.imooc.pojo.Record;
import com.imooc.pojo.Scheduled;
import com.imooc.pojo.bo.SendBo;
import com.imooc.pojo.bo.Wav;
import com.imooc.pojo.vo.SendRecord;
import com.imooc.service.SendService;
import com.imooc.service.RecordService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.FastDFSClient;
import com.imooc.utils.FileUtils;
import com.imooc.utils.IMoocJSONResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("phone")
public class PhoneController {
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private RecordService recordService;
    @Autowired
    private SendService sendService;
//    @PostMapping("/saveWav")
//    public IMoocJSONResult saveWav(Wav wav) throws Exception{
//        String base64Data = wav.getBasewav();
        //String userFacePath = "D:\\" + wav.getUserId() + "wavface64.wav";
//        String path = System.getProperties().getProperty("user.home");
//        String userFacePath=path+wav.getId()+"wavface64.wav";
////        FileUtils.base64ToFile(userFacePath, base64Data);
//        // 上传文件到fastdfs
//        wav.getBasewav().transferTo(new File(userFacePath));
//       MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
//        String url = fastDFSClient.uploadFile(faceFile);
//        String url=wav.getBasewav();
//        Record record=new Record();
//        record.setId(wav.getId());
//        record.setPhone(wav.getPhone());
//        record.setBasewav(url);
//        record.setRecordtime(wav.getRecordtime());
//        record.setTime(new Date());
//        recordService.wav(record);
//        return IMoocJSONResult.ok("保存成功");
//   }
   @PostMapping("/download")
   public File download(Record record) throws IOException {
       byte[] bytes = fastDFSClient.downloadFile(record.getBasewav());
       File file = new File("");
       FileInputStream fileInputStream=new FileInputStream(file);
       BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
       bufferedInputStream.read(bytes);
       return file;
    }

    /**
     * 发送记录
     * @param phone
     * @return
     */
    @PostMapping("/sendrecord")
    public IMoocJSONResult sendrecord(String phone){
        List<SendRecord> sendRecord=recordService.selectsendrecord(phone);
        return IMoocJSONResult.ok(sendRecord);
    }
    @PostMapping("/sendDacall")
   public IMoocJSONResult senddacall(SendBo sendBo) throws Exception{
       Scheduled scheduled=new Scheduled();
       Sid sid=new Sid();
       scheduled.setCron_id(sid.nextShort());
        if(StringUtils.isNotEmpty(sendBo.getSend_phone())) {
           scheduled.setSend_phone(sendBo.getSend_phone());
        }
        if(StringUtils.isNotEmpty(sendBo.getCron())){
            String cron = sendBo.getCron();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(cron);
            String cron1 = DateUtil.getCron(date);
            scheduled.setCron(cron1);
        }
        if(StringUtils.isNotEmpty(sendBo.getPhone())){
            scheduled.setPhone(sendBo.getPhone());
        }
        scheduled.setclazzname("freeswitch-"+ scheduled.getCron_id());
        String url=recordService.selecturl(sendBo.getId());
        String p=url.substring(url.indexOf("M00"));
        byte[] bytes = fastDFSClient.downloadFile(p);
        System.out.println("---------------->"+bytes.length);
        String fileurl = p.substring(p.lastIndexOf("/")+1,p.indexOf("."));
        String loadurl="/usr/local/freeswitch/sounds/en/us/callie/"+fileurl+".wav";
    //    String loadurl="D:\\"+fileurl+".wav";
        File file = new File(loadurl);
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream outputStream=new FileOutputStream(file);
//        BufferedOutputStream bufferedInputStream = new BufferedOutputStream(outputStream);
        outputStream.write(bytes,0,bytes.length);
        outputStream.flush();
        outputStream.close();
        scheduled.setWav(fileurl);
        sendService.send(scheduled);

        return IMoocJSONResult.ok("保存成功");
   }

}
