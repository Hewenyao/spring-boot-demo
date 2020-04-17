package com.example.watermark.controller;

import com.example.watermark.model.dto.ImageInfo;
import com.example.watermark.service.IImageUploadService;
import com.example.watermark.service.IWatermarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@RestController
public class ImageController {
    @Autowired
    private IImageUploadService imageUploadService;

    @Autowired
    private IWatermarkService watermarkService;

    @RequestMapping(value = "/watermark", method = RequestMethod.POST)
    public ImageInfo watermarkTest(@RequestParam("file") MultipartFile image ) {

        ImageInfo imgInfo = new ImageInfo();
        String uploadPath = "static/images/";                // 服务器上上传文件的相对路径
        String physicalUploadPath = getClass().getClassLoader().getResource(uploadPath).getPath();  // 服务器上上传文件的物理路径

        String imageURL = imageUploadService.uploadImage( image, uploadPath, physicalUploadPath ); //上传图片 获得图片地址
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename() );

        String watermarkAddImageURL = watermarkService.watermarkAdd(imageFile, image.getOriginalFilename(), uploadPath, physicalUploadPath);

        imgInfo.setImageUrl(imageURL);
        imgInfo.setLogoImageUrl(watermarkAddImageURL);
        return imgInfo;
    }
}
