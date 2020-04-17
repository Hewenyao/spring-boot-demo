package com.example.watermark.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImageUploadService {

    /**
     * 功能：上传图片
     * @param file 文件
     * @param uploadPath 服务器上上传文件的路径
     * @param physicalUploadPath  服务器上上传文件的物理路径
     * @return 上传文件的 URL相对地址
     */
     String uploadImage(MultipartFile file, String uploadPath, String physicalUploadPath );
}
