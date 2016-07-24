package com.atm.util.bbs;
/**
 * @TODO：图片的压缩工具，来源网上
 * @fileName : com.atm.util.bbs.ImageUtil.java
 * date | author | version |   
 * 2015年10月4日 | Jiong | 1.0 |
 */
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGEncodeParam;  
import javax.swing.*;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.awt.image.Kernel;  
import java.awt.image.ConvolveOp;  
  
public class ImageUtil {  
  
    public static void resize(File originalFile, File resizedFile,  
            int newWidth, float quality) throws IOException {  
  
        if (quality > 1) {  
            throw new IllegalArgumentException(  
                    "Quality has to be between 0 and 1");  
        }  
  
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
        Image i = ii.getImage();  
        Image resizedImage = null;  
  
        int iWidth = i.getWidth(null);  
        int iHeight = i.getHeight(null);  
  
        if (iWidth > iHeight) {  
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)  
                    / iWidth, Image.SCALE_SMOOTH);  
        } else {  
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,  
                    newWidth, Image.SCALE_SMOOTH);  
        }  
  
        // This code ensures that all the pixels in the image are loaded.  
        Image temp = new ImageIcon(resizedImage).getImage();  
  
        // Create the buffered image.  
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
  
        // Copy image to buffered image.  
        Graphics g = bufferedImage.createGraphics();  
  
        // Clear background and paint the image.  
        g.setColor(Color.white);  
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
        g.drawImage(temp, 0, 0, null);  
        g.dispose();  
  
        // Soften.  
        float softenFactor = 0.05f;  
        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
        Kernel kernel = new Kernel(3, 3, softenArray);  
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
        bufferedImage = cOp.filter(bufferedImage, null);  
  
        // Write the jpeg to a file.  
        FileOutputStream out = new FileOutputStream(resizedFile);  
  
        // Encodes image as a JPEG data stream  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
  
        JPEGEncodeParam param = encoder  
                .getDefaultJPEGEncodeParam(bufferedImage);  
  
        param.setQuality(quality, true);  
  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(bufferedImage);  
    } // Example usage  
  
    public static void main(String[] args) throws IOException {  
//       File originalImage = new File("C:\\11.jpg");  
//       resize(originalImage, new File("c:\\11-0.jpg"),150, 0.7f);  
//       resize(originalImage, new File("c:\\11-1.jpg"),150, 1f);  
         File originalImage = new File("E:\\MyEclipse\\SHEN\\WebRoot\\otherImage\\foodImage_w\\chashao.jpg");  
         //resize(originalImage, new File("E:\\back-0.jpg"),400, 1f);  
         resize(originalImage, new File("E:\\MyEclipse\\SHEN\\WebRoot\\otherImage\\foodImage_b\\chashao.jpg"),400, 0.9f);  
    }  
}  

