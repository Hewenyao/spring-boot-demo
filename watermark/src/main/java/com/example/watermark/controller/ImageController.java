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

import java.awt.*;
import java.io.File;


@RestController
public class ImageController {
    @Autowired
    private IImageUploadService imageUploadService;

    @Autowired
    private IWatermarkService watermarkService;

    /**
     * 功能: 给前台上传的图片的加上默认的水印
     * @param image
     * @return
     */
    @RequestMapping(value = "/watermark", method = RequestMethod.POST)
    public ImageInfo watermarkTest(@RequestParam("file") MultipartFile image ) {
        ImageInfo imgInfo = new ImageInfo();
        String uploadPath = "static/images/";                 // 服务器上上传文件的相对路径
        String physicalUploadPath = getClass().getClassLoader().getResource(uploadPath).getPath();  //服务器上上传文件的物理路径
        String imageURL = imageUploadService.uploadImage( image, uploadPath, physicalUploadPath ); //上传图片 获得图片地址
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename() );
        String watermarkAddImageURL = watermarkService.watermarkAdd(imageFile, image.getOriginalFilename(), uploadPath, physicalUploadPath);
        imgInfo.setImageUrl(imageURL);
        imgInfo.setLogoImageUrl(watermarkAddImageURL);
        return imgInfo;
    }

    /**
     * 功能: 接收前台上传的图片和水印文字内容，将文字添加到图片上
     * @param image 图片
     * @param text  水印文字内容
     * @return
     */
    @RequestMapping(value = "/textWatermark", method = RequestMethod.POST)
    public ImageInfo addTextWatermark(@RequestParam("file") MultipartFile image,@RequestParam("text") String text) {
        ImageInfo imgInfo = new ImageInfo();
        String uploadPath = "static/images/";// 服务器上上传文件的相对路径
        String physicalUploadPath = getClass().getClassLoader().getResource(uploadPath).getPath();  //服务器上上传文件的物理路径
        String imageURL = imageUploadService.uploadImage( image, uploadPath, physicalUploadPath ); //上传图片 获得图片地址
        File imageFile = new File(physicalUploadPath + image.getOriginalFilename() );
        String watermarkAddImageURL = watermarkService.pressText2(text,imageFile,image.getOriginalFilename(),uploadPath,physicalUploadPath,"黑体", Font.BOLD,Color.WHITE,80,0,0,0.5f);
        imgInfo.setImageUrl(imageURL);
        imgInfo.setLogoImageUrl(watermarkAddImageURL);
        return imgInfo;
    }
}
