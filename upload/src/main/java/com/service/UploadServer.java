package com.service;

import com.response.CommonResponse;
import com.response.ImgUrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServer {
    public CommonResponse<List<ImgUrlResponse>> upLoadImg(MultipartFile[] multipartFiles){
        List<ImgUrlResponse> imgUrlResponseList=new ArrayList<>();
        if(multipartFiles!=null){
            for (int i=0;i<multipartFiles.length;i++){
                String fileType=multipartFiles[i].getContentType();
                System.out.println(fileType);
                String fileName=multipartFiles[i].getName();
                try {
                    multipartFiles[i].transferTo(new File("G:\\jyb\\img"+fileName));
                    imgUrlResponseList.add(new ImgUrlResponse("http:localhost:1001/"+fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return CommonResponse.success(200,"请求成功",imgUrlResponseList);
    }
}
