package com.example.watermark.service.impl;


import com.example.watermark.service.IImageUploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadServiceImpl implements IImageUploadService {


    @Override
    public String uploadImage(MultipartFile file, String uploadPath, String physicalUploadPath) {
        String filePath = physicalUploadPath + file.getOriginalFilename();
        try {
            File targetFile=new File(filePath); // create a file by filepath
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes()); //
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadPath + "/" + file.getOriginalFilename(); //返回图片的相对地址
    }
}
