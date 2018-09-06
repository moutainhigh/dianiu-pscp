/**
 *
 */
package com.edianniu.pscp.mis.util;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.CropParameter;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.util.ImageCropHelper;
import com.alibaba.simpleimage.util.ImageReadHelper;
import com.alibaba.simpleimage.util.ImageWriteHelper;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifImageDirectory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cyl
 */
public class ImageUtils {

    /**
     * Base64图片字符串解析，去掉data:image/jpep;base64,
     * @param images
     */
    public static String[] parseBase64Image(String[] images) {
        List<String> list = new ArrayList<>();
        if (images != null) {
            for (String image : images) {
                String rs = parseBase64Image(image);
                if (StringUtils.isNotBlank(rs)) {
                    list.add(rs);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public static String parseBase64Image(String image) {
        if (StringUtils.isNoneBlank(image)) {
            return image.substring(image.indexOf(",") + 1);
        }
        return null;
    }

    /**
     * 图片压缩
     * @param is     输入流
     * @param os     输出流
     * @param width  宽
     * @param height 高
     * @throws IOException
     */
    public static void changeSize(InputStream is, OutputStream os, int width, int height) throws IOException {
        BufferedImage bis = ImageIO.read(is); // 构造Image对象
        is.close();

        int srcWidth = bis.getWidth(null);   // 得到源图宽
        int srcHeight = bis.getHeight(null); // 得到源图高

        if (width <= 0 || width > srcWidth) {
            width = bis.getWidth();
        }
        if (height <= 0 || height > srcHeight) {
            height = bis.getHeight();
        }

        // 若宽高小于指定最大值，不需重新绘制
        if (srcWidth <= width && srcHeight <= height) {
            ImageIO.write(bis, "jpg", os);
            os.close();
        } else {
            double scale =
                    ((double) width / srcWidth) > ((double) height / srcHeight) ?
                            ((double) height / srcHeight)
                            : ((double) width / srcWidth);
            width = (int) (srcWidth * scale);
            height = (int) (srcHeight * scale);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(bis, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
            ImageIO.write(bufferedImage, "jpg", os);
            os.close();
        }
    }

    /**
     * 从中间裁切需要的大小
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public static void CutCenter(InputStream is, OutputStream os, Integer width, Integer height) {
        try {
            ImageWrapper imageWrapper = ImageReadHelper.read(is);

            int w = imageWrapper.getWidth();
            int h = imageWrapper.getHeight();

            int x = (w - width) / 2;
            int y = (h - height) / 2;

            CropParameter cropParam = new CropParameter(x, y, width, height);// 裁切参数
            if (x < 0 || y < 0) {
                cropParam = new CropParameter(0, 0, w, h);// 裁切参数
            }

            PlanarImage planrImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            imageWrapper = new ImageWrapper(planrImage);
            String prefix = "JPG";
            ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat(prefix), new WriteParameter());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }
    
    /**
     * 非等比例缩放，保持长宽均为400
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public static void zoomTo400And400(InputStream is, OutputStream os, Integer width, Integer height){
    	try {
			BufferedImage bufImg = ImageIO.read(is);
			is.close();
			int w0 = bufImg.getWidth();
			int h0 = bufImg.getHeight();
			
			double wRation = 1.0 * width / w0;
			double hRatio = 1.0 * height / h0;
			
			Image image = bufImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wRation, hRatio), null);
			image = ato.filter(bufImg, null);
			
			ImageIO.write((RenderedImage) image, "jpg", os);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 先等比例缩放，小边缩放至指定长度后， 大边直接裁剪指指定长度(方法一)
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public static void zoomAndCut1(InputStream is, OutputStream os, Integer width, Integer height) {
    	try {
    		BufferedImage bufferedImage = ImageIO.read(is);
    		is.close();
			int w = bufferedImage.getWidth();
	    	int h = bufferedImage.getHeight();
	    	/*1.缩放*/
	    	// 获取缩放比例
	    	double wRatio = 1.0 * width / w;
	    	double hRatio = 1.0 * height / h;
	    	double ratio = Math.max(wRatio, hRatio);
	    	// 缩放
	    	AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
	    	bufferedImage = ato.filter(bufferedImage, null);
	    	// 对象转换
	    	ImageWrapper imageWrapper = new ImageWrapper(bufferedImage);
	    	/*2.裁切*/
	    	// 获得裁剪偏移量
	    	int w2 = imageWrapper.getWidth();
	    	int h2 = imageWrapper.getHeight();
	    	float x = (w2 - width) / 2.0f;
	    	float y = (h2 - height) / 2.0f;
	    	// 裁剪参数   如果图片宽和高都小于目标图片则处理
	    	CropParameter cropParameter = new CropParameter(x, y, width, height);
	    	if (x < 0 && y < 0) {
	    		cropParameter = new CropParameter(0, 0, width, height);
			}
	    	PlanarImage crop = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParameter);
	    	imageWrapper = new ImageWrapper(crop);
	    	// 写文件
	    	ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat("jpg"), new WriteParameter());
	    	os.close();
		} catch (IOException | SimpleImageException e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 先等比例缩放，小边缩放至指定长度后， 大边直接裁剪指指定长度(方法二)
     * @param is
     * @param os
     * @param width
     * @param height
     */
    public final static void zoomAndCut2(InputStream is, OutputStream os, int width, int height) {
    	try {
    		// 读文件
        	BufferedImage read = ImageIO.read(is);
        	is.close();
            int w = read.getWidth();
            int h = read.getHeight();
            double wRatio = 1.0 * width / w;
            double hRatio = 1.0 * height / h;
            double ratio = Math.max(wRatio, hRatio);
            /*1.缩放*/
            // 向上取整，防止小数部分被丢弃
            BufferedImage bufferedImage = new BufferedImage((int) Math.ceil(ratio * w), (int) Math.ceil(ratio * h), BufferedImage.TYPE_INT_BGR);
            bufferedImage.getGraphics().drawImage(read, 0, 0, (int) Math.ceil(ratio * w), (int) Math.ceil(ratio * h), Color.WHITE, null);
            
            /*2.裁切*/
            // 获取裁剪偏移量
            ImageWrapper imageWrapper = new ImageWrapper(bufferedImage);
            int w2 = imageWrapper.getWidth();
            int h2 = imageWrapper.getHeight();
            int x = (w2 - width) / 2;
            int y = (h2 - height) / 2;
            // 裁切参数   如果图片宽和高都小于目标图片则处理
            CropParameter cropParam = new CropParameter(x, y, width, height);
            if (x < 0 || y < 0) {
                cropParam = new CropParameter(0, 0, w, h);
            }
            // 裁剪
            PlanarImage planarImage = ImageCropHelper.crop(imageWrapper.getAsPlanarImage(), cropParam);
            /*输出*/
            imageWrapper = new ImageWrapper(planarImage);
            ImageWriteHelper.write(imageWrapper, os, ImageFormat.getImageFormat("jpg"), new WriteParameter());
            os.close();
		} catch (SimpleImageException | IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 获取图片信息，并判断是否要做旋转操作
     * ios图片可能会发生旋转现象
     * @param fileBytes
     */
    public static byte[] getExifAndTurn(byte[] fileBytes){
    	ByteArrayInputStream is = null;
    	ByteArrayInputStream its = null;
		try {
			is = new ByteArrayInputStream(fileBytes);
			Metadata metadata = ImageMetadataReader.readMetadata(is);
			is.close();
			Iterable<Directory> directories = metadata.getDirectories();
			Iterator<Directory> iterator = directories.iterator();
			int orientation = 0;
			while (iterator.hasNext()) {
				Directory exif = iterator.next();
				// 获取图片旋转信息
				if (exif.containsTag(ExifImageDirectory.TAG_ORIENTATION)) {
					String description = exif.getDescription(ExifImageDirectory.TAG_ORIENTATION);
					orientation = getOrientation(description);
					break;
				}
			}
			// 确定旋转角度
			int angel = 360;
			if(orientation == 0 || orientation == 1) {  
	        	angel = 360;  
	        }  
	        else if(orientation == 3) {  
	        	angel = 180;  
	        }  
	        else if(orientation == 6) {  
	        	angel = 90;  
	        }  
	        else if(orientation == 8) {  
	        	angel = 270;  
	        }  
			
			// 若不需要旋转，则直接原图返回
			if (angel != 360) {
				// 将字节数组转换为BufferedImage
				its = new ByteArrayInputStream(fileBytes);
				BufferedImage bf = ImageIO.read(its);
				its.close();
				// 旋转
				bf = turn(bf, angel);
				// 将BufferedImage转换为字节数组
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bf, "jpg", baos);
				fileBytes = baos.toByteArray();
				baos.close();
			}
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return fileBytes;
	}
    
    /**
     * 获取图片旋转标志
     * @param description 图片旋转描述信息
     */
    public static int getOrientation(String description) {  
        int orientation = 0;  
        if ("Top, left side (Horizontal / normal)".equalsIgnoreCase(description)) {  
        	orientation = 1;  
        } else if ("Top, right side (Mirror horizontal)".equalsIgnoreCase(description)) {  
        	orientation = 2;  
        } else if ("Bottom, right side (Rotate 180)".equalsIgnoreCase(description)) {  
        	orientation = 3;  
        } else if ("Bottom, left side (Mirror vertical)".equalsIgnoreCase(description)) {  
        	orientation = 4;  
        } else if ("Left side, top (Mirror horizontal and rotate 270 CW)".equalsIgnoreCase(description)) {  
        	orientation = 5;  
        } else if ("Right side, top (Rotate 90 CW)".equalsIgnoreCase(description)) {  
        	orientation = 6;  
        } else if ("Right side, bottom (Mirror horizontal and rotate 90 CW)".equalsIgnoreCase(description)) {  
        	orientation = 7;  
        } else if ("Left side, bottom (Rotate 270 CW)".equalsIgnoreCase(description)) {  
        	orientation = 8;  
        }  
        return  orientation;  
    }  
    
    /**
     * 图片旋转
     * @param bi 需要转换的图像
     * @param degree 旋转角度
     */
    public static BufferedImage turn(BufferedImage bi, int degree) {
		int swidth = 0; // 旋转后的宽度
        int sheight = 0; // 旋转后的高度
        int x; // 原点横坐标
        int y; // 原点纵坐标

        // 处理角度，将角度转换到0-360度之间--确定旋转弧度
        degree = degree % 360;
        if (degree < 0) {
            degree = 360 + degree;
        }
        // 将角度转为弧度
        double theta = Math.toRadians(degree);

        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = bi.getWidth();
            sheight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = bi.getWidth();
            swidth = bi.getHeight();
        } else {
            swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
            sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth() + bi.getHeight() * bi.getHeight()));
        }
        // 确定原点坐标
        x = (swidth / 2) - (bi.getWidth() / 2);
        y = (sheight / 2) - (bi.getHeight() / 2);

        BufferedImage result = new BufferedImage(swidth, sheight, bi.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) result.getGraphics();
        gs.setColor(Color.white);
        gs.fillRect(0, 0, swidth, sheight);// 以给定颜色绘制旋转后图片的背景

        AffineTransform at = new AffineTransform();
        at.rotate(theta, swidth / 2, sheight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        result = op.filter(bi, result);
        
		return result;
    }

}
