package cn.jiuling.vehicleinfosys2.util;

import cn.jiuling.vehicleinfosys2.vo.PolygonPoint;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片缩放工具类.<br>
 * <p/>
 * 摘自网上,一个外国人写的高质量的图片缩放工具类
 *
 * @see <a
 * href="http://luoyahu.iteye.com/blog/1312043">http://luoyahu.iteye.com/blog/1312043</a>
 */
public class ImageUtil {

    public static void resize(File originalFile, File resizedFile,
                              int newWidth, float quality) throws IOException {
        String name = originalFile.getName();
        name = StringUtils.isEmpty(name) ? "" : name.toLowerCase();
        if (name.endsWith(".bmp") || name.endsWith(".dib")) {
            new CompressPic().compressPic(originalFile, resizedFile, newWidth);
            //TODO 暂时不做处理
        } /*else if (name.endsWith(".tif") || name.endsWith(".tiff")){
            resizeTIFF(originalFile, resizedFile, newWidth, quality);
        } */ else {
            resizePic(originalFile, resizedFile, newWidth, quality);
        }

    }

/*
    private static void resizeTIFF(File originalFile, File resizedFile, int newWidth, float quality) {

    }
*/

    public static void resizePic(File originalFile, File resizedFile,
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
        float[] softenArray = {0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
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
        out.flush();
        out.close();
    }

    /**
     *生成感兴趣区、非感兴趣区PNG图片
     * @param areaType 画图模式
     * @param width 感兴趣区/非感兴趣区宽度
     * @param height 感兴趣区/非感兴趣区高度
     * @param polygons 感兴趣区/非感兴趣区坐标
     * @param pngName png的绝对路径
     */
    public static void generatePNG(Short areaType,int width,int height, PolygonPoint[][] polygons, String pngName) {

    	// 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.RED);
        
        Rectangle r = new Rectangle(0, 0, width, height);
        Area a = new Area(r);
        
        for (int i = 0; i < polygons.length; i++) {
        	Polygon p = new Polygon();
            PolygonPoint[] polygon = polygons[i];

            for (int j = 0; j < polygon.length; j++) {
                PolygonPoint pp = polygon[j];
                if (null != pp) {
                	p.addPoint(pp.getX(), pp.getY());
                }
            }
            //感兴趣区
            if (areaType == 1) {
            	a.subtract(new Area(p));            	
            }
            //非感兴趣区
            if (areaType == 2) {
            	g2d.fillPolygon(p);
            }
            
        }
        
        if (areaType == 1) {        	
        	g2d.setClip(a);
        	g2d.fillRect(0, 0, width, height);
        }
        //释放对象
        g2d.dispose();
        // 保存文件
        try {
	        ImageIO.write(image, "png", new File(pngName));
        } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("生成png图片失败!");
        }
    }
    
    /**
     *生成8位的感兴趣区、非感兴趣区PNG图片（8位）
     * @param areaType 画图模式
     * @param width 感兴趣区/非感兴趣区宽度
     * @param height 感兴趣区/非感兴趣区高度
     * @param polygons 感兴趣区/非感兴趣区坐标
     * @param pngName png的绝对路径
     */
    public static void generate8BitPNG(Short areaType,int width,int height, PolygonPoint[][] polygons, String pngName) {

    	// 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.white);
        
        Rectangle r = new Rectangle(0, 0, width, height);
        Area a = new Area(r);
        Area roi = new Area();
        
        for (int i = 0; i < polygons.length; i++) {
        	Polygon p = new Polygon();
            PolygonPoint[] polygon = polygons[i];

            for (int j = 0; j < polygon.length; j++) {
                PolygonPoint pp = polygon[j];
                if (null != pp) {
                	p.addPoint(pp.getX(), pp.getY());
                }
            }
            roi.add(new Area(p));
        }
        
        Area fillArea = null;
        if (areaType == 1) { 
        	fillArea = roi;
        } else if(areaType == 2) {
        	fillArea = a;
        	fillArea.exclusiveOr(roi);
        }
    	g2d.setClip(fillArea);
    	g2d.fillRect(0, 0, width, height);
    	
        //释放对象
        g2d.dispose();
        // 保存文件
        try {
	        ImageIO.write(image, "png", new File(pngName));
        } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("生成png图片失败!");
        }
    }
    
    /**
     * 缩放png图片
     * @param originalFile 原始图片
     * @param resizedFile 新图片
     * @param newWidth 新宽度
     * @param newHeight 新高度
     * @param quality 缩放质量
     * @throws IOException
     */
    public static void resizePngPicture(File originalFile, File resizedFile,int newWidth, int newHeight,float quality) throws IOException {

        if (quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        resizedImage = i.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);


        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
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
        out.flush();
        out.close();
    }
    

    public static void main(String[] args) throws IOException {
    	int width = 800;
    	int height = 600;
    	PolygonPoint p = new PolygonPoint();
    	p.setX(0);
    	p.setY(0);
    	PolygonPoint p1 = new PolygonPoint();
    	p1.setX(0);
    	p1.setY(height/4);
    	PolygonPoint p2 = new PolygonPoint();
    	p2.setX(width/4);
    	p2.setY(height/4);
    	PolygonPoint p3 = new PolygonPoint();
    	p3.setX(width/4);
    	p3.setY(0);
    	
    	PolygonPoint p_ = new PolygonPoint();
    	p_.setX(width/2);
    	p_.setY(height/2);
    	PolygonPoint p_1 = new PolygonPoint();
    	p_1.setX(width/2);
    	p_1.setY(height);
    	PolygonPoint p_2 = new PolygonPoint();
    	p_2.setX(width);
    	p_2.setY(height);
    	PolygonPoint p_3 = new PolygonPoint();
    	p_3.setX(width);
    	p_3.setY(height/2);
    	
    	
    	
    	PolygonPoint[][] polygons = new PolygonPoint[2][4];
    	polygons[0][0] = p;
    	polygons[0][1] = p1;
    	polygons[0][2] = p2;
    	polygons[0][3] = p3;
    	
    	polygons[1][0] = p_;
    	polygons[1][1] = p_1;
    	polygons[1][2] = p_2;
    	
    	generatePNG((short)1, width, height, polygons, "c:/test.png");
    }
}
