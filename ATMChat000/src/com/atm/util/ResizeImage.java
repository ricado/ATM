package com.atm.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
public class ResizeImage {
 
    /**
     * @param im            ԭʼͼ��
     * @param resizeTimes   ��Ҫ��С�ı�������С2��Ϊԭ����1/2 �������ֵԽ�󣬷��ص�ͼƬԽС
     * @return              ���ش�����ͼ��
     */
    public BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
        /*ԭʼͼ��Ŀ�Ⱥ͸߶�*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*�������ͼƬ�Ŀ�Ⱥ͸߶�*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);
 
        /*�����ɽ��ͼƬ*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
 
    /**
     * @param im            ԭʼͼ��
     * @param resizeTimes   ����,����0.5������Сһ��,0.98�ȵ�double����
     * @return              ���ش�����ͼ��
     */
    public BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
        /*ԭʼͼ��Ŀ�Ⱥ͸߶�*/
        int width = im.getWidth();
        int height = im.getHeight();
 
        /*�������ͼƬ�Ŀ�Ⱥ͸߶�*/
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
 
        /*�����ɽ��ͼƬ*/
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
 
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }
 
    /**
     * @param path  Ҫת����ͼ����ļ���,���Ǵ��ͼ����ļ���·��
     * @param type  ͼƬ�ĺ�׺����ɵ�����
     * @return
    */
    public List<BufferedImage> getImageList(String path, String[] type) throws IOException{
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        for(String s : type) {
            map.put(s,true);
        }
        List<BufferedImage> result = new ArrayList<BufferedImage>();
        File[] fileList = new File(path).listFiles();
        for (File f : fileList) {
            if(f.length() == 0)
                continue;
            if(map.get(getExtension(f.getName())) == null)
                continue;
            result.add(javax.imageio.ImageIO.read(f));
        }
        return result;
    }
 
    /**
     * ��ͼƬд��������
      * @param im
     * @param path     eg: C://home// ͼƬд����ļ��е�ַ
      * @param fileName DCM1987.jpg  д��ͼƬ������
      * @return
     */
    public boolean writeToDisk(BufferedImage im, String path, String fileName) {
        File f = new File(path + fileName);
        String fileType = getExtension(fileName);
        if (fileType == null)
            return false;
        try {
            ImageIO.write(im, fileType, f);
            im.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
 
 
    public boolean writeHighQuality(BufferedImage im, String fileFullPath) {
        try {
            /*������ļ���*/
            FileOutputStream newimage = new FileOutputStream(fileFullPath+System.currentTimeMillis()+".jpg");
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
            /* ѹ������ */
            jep.setQuality(1f, true);
            encoder.encode(im, jep);
           /*��JPEG����*/
            newimage.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    /**
     * �����ļ����ļ���׺��
      * @param fileName
      * @return
    */
    public String getExtension(String fileName) {
        try {
            return fileName.split("\\.")[fileName.split("\\.").length - 1];
        } catch (Exception e) {
            return null;
        }
    }
 
    public static void main(String[] args) throws Exception{
 
 
        String inputFoler = "c:\\cameraImage" ;
         /*�����д����Ҫ��СͼƬ���ļ���ȫ��ַ*/
        String outputFolder = "c:\\output\\"; 
        /*�����д��ת�����ͼƬ��ŵ��ļ���*/
        float times = 0.5f;
        /*���������Ҫת���ɵı���,�����1����ת����1��*/
 
 
        ResizeImage r = new ResizeImage();
   List<BufferedImage> imageList = r.getImageList(inputFoler,new String[] {"jpg"});
        for(BufferedImage i : imageList) {
            r.writeHighQuality(r.zoomImage(i,times),outputFolder);
        }
    }
}
