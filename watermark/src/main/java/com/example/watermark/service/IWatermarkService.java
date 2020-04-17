package com.example.watermark.service;

import java.awt.*;
import java.io.File;

public interface IWatermarkService {

    /**
     * 功能：给上传的图片添加图片水印 效果为水印铺满原图片
     *
     * @param imageFile      待添加水印的文件
     * @param imageFileName  文件名称
     * @param uploadPath     文件在服务器上的相对路径
     * @param realUploadPath 文件在服务器上的物理路径
     * @return 添加水印后的文件的地址
     */
    String watermarkAdd(File imageFile, String imageFileName,
                        String uploadPath, String realUploadPath);

    /**
     * 功能：给图片添加文字水印
     * @param text 水印文字
     * @param imageFile   待添加水印的文件
     * @param imageFileName  待加水印的文件名
     * @param uploadPath     服务器上上传文件的相对路径
     * @param realUploadPath 服务器上上传文件的物理路径
     * @param fontName  字体名称
     * @param fontStyle 字体样式
     * @param color     字体颜色
     * @param fontSize  字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @Return 添加水印后的文件在服务器上的相对地址
     */
    String pressText2(String text, File imageFile,String imageFileName,String uploadPath,String realUploadPath,
                                        String fontName, int fontStyle, Color color,
                                        int fontSize, int x,int y, float alpha);
}
