package com.example.watermark.service.impl;


import com.example.watermark.comm.Const;
import com.example.watermark.service.IWatermarkService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class WatermarkServiceImpl implements IWatermarkService {

    @Override
    public String watermarkAdd( File imageFile, String imageFileName, String uploadPath, String realUploadPath ) {

        String logoFileName = "watermark_" + imageFileName;
        OutputStream os = null;

        try {
            Image image = ImageIO.read(imageFile);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  // 创建图片缓存对象
            Graphics2D g = bufferedImage.createGraphics();  // 创建绘绘图工具对象
            g.drawImage(image, 0, 0, width,height,null);  // 使用绘图工具将原图绘制到缓存图片对象

            String logoPath = realUploadPath + "/" + Const.LOGO_FILE_NAME;  // 水印图片地址
            File logo = new File(logoPath);        // 读取水印图片
            Image imageLogo = ImageIO.read(logo);

            int markWidth = imageLogo.getWidth(null);    // 水印图片的宽度和高度
            int markHeight = imageLogo.getHeight(null);

            g.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Const.ALPHA) );  // 设置水印透明度
            g.rotate(Math.toRadians(-10), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);  // 旋转图片

            int x = Const.X;
            int y = Const.Y;

            int xInterval = Const.X_INTERVAL;
            int yInterval = Const.Y_INTERVAL;

            double count = 1.5;
            while ( x < width*count ) {  // 循环添加水印
                y = -height / 2;
                while( y < height*count ) {
                    g.drawImage(imageLogo, x, y, null);  // 添加水印
                    y += markHeight + yInterval;
                }
                x += markWidth + xInterval;
            }

            g.dispose();

            os = new FileOutputStream(realUploadPath + "/" + logoFileName);
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferedImage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uploadPath + "/" + logoFileName;
    }

    @Override
    public String pressText2(String text, File srcImageFile, String imageFileName, String uploadPath,String realUploadPath, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        String logoFileName = "watermark_"+imageFileName;
        try {
            Image src = ImageIO.read(srcImageFile);    //获得图片流
            int width = src.getWidth(null);   //获取源图片的宽度
            int height = src.getHeight(null); // 获取源图片的高度
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null); // 绘制图片
            g.setColor(color);//设置颜色
            g.setFont(new Font(fontName, fontStyle, fontSize));  //设置水印字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  alpha)); //设置图片
            g.drawString(text, (width - (getLength(text) * fontSize)) / 2 + x, (height - fontSize) / 2 + y); // 在指定坐标绘制水印文字
            g.dispose();
            ImageIO.write(image, "JPEG", new File(realUploadPath+"/"+logoFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadPath + "/" + logoFileName;
    }


    /**
     * 功能：计算text的长度（一个中文算两个字符）
     * @param text 要计算的字符串
     * @return text的长度
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

}
