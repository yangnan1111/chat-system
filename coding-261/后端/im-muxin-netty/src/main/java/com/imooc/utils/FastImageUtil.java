package com.imooc.utils;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FastImageUtil {
    @Autowired
    private FastDFSClient fastDFSClient;
    public String ImageUtil(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String path = System.getProperties().getProperty("user.home");
        Sid sid=new Sid();
        String s=sid.nextShort();
        String url=null;
        String userFacePath = path + s + "userface64"+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        File newFile=new File(userFacePath);
//		FileUtils.base64ToFile(userFacePath, base64Data);
        file.transferTo(newFile);
        // 上传文件到fastdfs
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url1 = fastDFSClient.uploadBase64(faceFile);
        url="http://123.56.146.238:88/yang"+url1;
        return url;
    }
}
