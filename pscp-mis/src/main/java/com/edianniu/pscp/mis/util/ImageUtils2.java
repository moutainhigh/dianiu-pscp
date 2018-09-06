package com.edianniu.pscp.mis.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifImageDirectory;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhoujianjian
 * @date 2018年2月1日 上午9:39:15
 */
public class ImageUtils2 {
	
	private static final boolean IS_WINDOWS = SystemUtils.IS_OS_WINDOWS;
	
	private static final String GRAPHICS_MAGICK_PATH = "D:\\Program Files\\GraphicsMagick-1.3.28-Q16";
    
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
     * 先等比例缩放，后居中切割图片 
     * @param os
     * @param is
     * @param width
     * @param height
     */
    public static void zoomPic(InputStream is, OutputStream os, Integer width, Integer height) {
    	try {
	    	IMOperation op = new IMOperation();
	    	op.addImage("-");                                  // 从输入流中读取图片
	    	op.resize(width, height, '^').gravity("center").extent(width, height); 
	    	/*op.addRawArgs("+profile", "*");                  // 去除Exif信息       */	
            op.addImage("jpg" + ":-");                         // 图片格式
	        Pipe pipeIn = new Pipe(is, null);
	        is.close();
	        Pipe pipeOut = new Pipe(null, os);
	        os.close();
	        ConvertCmd cmd = new ConvertCmd(true);
	        if (IS_WINDOWS) { // Linux不设置此值
	            cmd.setSearchPath(GRAPHICS_MAGICK_PATH);
	        }
	        cmd.setInputProvider(pipeIn);
	        cmd.setOutputConsumer(pipeOut);
			cmd.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
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
			double angel = 360;
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
				its = new ByteArrayInputStream(fileBytes);
				fileBytes = rotate(its, angel);
				its.close();
			}
		} catch (ImageProcessingException | IOException e) {
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
     * @param is
     * @param degree
     */
	public static byte[] rotate(InputStream is, double degree) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			// 将角度转换到0-360度之间
			degree = degree % 360;
			if (degree <= 0) {
				degree = 360 + degree;
			}
			IMOperation op = new IMOperation();
			op.addImage("-");
			op.rotate(degree);
			op.addImage("jpg" + ":-");
			Pipe pipeIn = new Pipe(is, null);
	        is.close();
	        Pipe pipeOut = new Pipe(null, os);
	        os.close();
			ConvertCmd cmd = new ConvertCmd(true);
			if (IS_WINDOWS) {
				cmd.setSearchPath(GRAPHICS_MAGICK_PATH);
			}
			cmd.setInputProvider(pipeIn);
			cmd.setOutputConsumer(pipeOut);
			cmd.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		return os.toByteArray();
	}

}
