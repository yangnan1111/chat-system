package com.imooc.utils;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FastWavUtil {
    @Autowired
    private FastDFSClient fastDFSClient;

    public  String WavUtil(MultipartFile file) throws IOException {
        String url = null;
        Sid sid = new Sid();
        String s = sid.nextShort();
        String path = System.getProperties().getProperty("user.home");
        String userFacePath = path+ s + "wavface64.wav";
        File file1 = new File(userFacePath);
        file.transferTo(file1);
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url1 = fastDFSClient.uploadFile(faceFile);
        url="http://123.56.146.238:88/yang"+url1;
        return url;
    }
}