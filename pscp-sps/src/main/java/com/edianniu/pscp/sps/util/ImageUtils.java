/**
 *
 */
package com.edianniu.pscp.sps.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cyl
 */
public class ImageUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(ImageUtils.class);

    public static byte[] getBase64ImageBytes(String image) {
        image = parseBase64Image(image);
        byte[] content = Base64.decodeBase64(image);
        // 图片压缩
        if (content != null) {
            logger.debug("原图 :imageSize :{}", content.length
                    / (1024 * 1024F));
            if (content.length > 5 * 1024 * 1024) {// 大于5M的图片 ，进行压缩处理。
                content = ImageUtils.compress(content, 0.75F);// 高质量的图片
                logger.debug("压缩后:imageSize :{}", content.length
                        / (1024 * 1024F));
            }
            /*if (content.length > 8 * 1024 * 1024) {// 压缩后的图片或者原图图片大于8M，则提示片太大了，请重新选择图片。

			}*/
        }
        return content;
    }

    /**
     * Base64图片字符串解析，去掉data:image/jpep;base64,
     *
     * @param images
     * @return
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
     * 压缩图片
     *
     * @param content
     * @param quality
     * @return
     */
    public static byte[] compress(byte[] content, float quality) {
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        BufferedImage image = null;
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressImage(image, quality);
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param quality
     * @return
     */
    public static byte[] compressImage(BufferedImage image, float quality) {
        long start = System.currentTimeMillis();
        // 如果图片空，返回空
        if (image == null) {
            return null;
        }
        // 开始开始，写入byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
        try {
            saveAsJPEG(null, image, quality, byteArrayOutputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("compressive quality:" + quality + ",质量打包完成:" + "耗时:" + (System.currentTimeMillis() - start) + " ms"
                + ",length:" + byteArrayOutputStream.toByteArray().length);
        return byteArrayOutputStream.toByteArray();

    }

    /**
     * 以JPEG编码保存图片
     *
     * @param dpi             分辨率
     * @param image_to_save   要处理的图像图片
     * @param JPEGcompression 压缩比
     * @param fos             文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression, ByteArrayOutputStream fos) throws IOException {

        ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        //and metadata  
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);


        if (dpi != null && !dpi.equals("")) {
            //new metadata  
            Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(dpi));
            jfif.setAttribute("Ydensity", Integer.toString(dpi));

        }
        if (JPEGcompression >= 0 && JPEGcompression <= 1f) {
            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);

        }
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();
    }

    public static void main(String args[]) throws IOException {
        String filePath = "D:/tmp/96136482469459284-测试图片";
        String fileFix = ".jpg";
        File file = new File(filePath + fileFix);
        byte[] content = FileUtils.readFileToByteArray(file);
        byte[] result = ImageUtils.compress(content, 0.75F);
        FileUtils.writeByteArrayToFile(new File(filePath + "_new_" + System.currentTimeMillis() + fileFix), result);
    }
}
