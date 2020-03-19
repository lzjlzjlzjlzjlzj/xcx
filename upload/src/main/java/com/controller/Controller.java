package com.controller;

import com.response.CommonResponse;
import com.response.ImgUrlResponse;
import com.service.UploadServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class Controller {
    @Autowired
    UploadServer uploadServer;
    @PostMapping("uploadImg")
    CommonResponse<List<ImgUrlResponse>> upLoadImg(MultipartFile[] multipartFile){
        return uploadServer.upLoadImg(multipartFile);
    }
}

