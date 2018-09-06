package com.edianniu.pscp.mis.util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 水印工具类
 * ClassName: WatermarkUtils
 * Author: tandingbo
 * CreateTime: 2017-10-27 10:23
 */
public class WatermarkUtils {

    /**
     * 水印之间的间隔
     */
    private static final int INTERVAL = 150;
    private static final float ALPHA = 0.3F;
    private static final float MULTIPLE = 1.5F;

    /**
     * 图片水印
     */
    public static byte[] moreImageMark(byte[] content, String suffix) {
        if (content != null && content.length > 0) {
            BufferedImage bufferedImage = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(content);
                Image image2 = ImageIO.read(in);

                int width = image2.getWidth(null);
                int height = image2.getHeight(null);

                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                Graphics2D g = bufferedImage.createGraphics();
                g.drawImage(image2, 0, 0, width, height, null);

                String watermarkImage = ImageUtils.parseBase64Image(WATERMARK_BASE64);
                byte[] watermarkArray = Base64.decodeBase64(watermarkImage);
                ByteArrayInputStream watermarkIn = new ByteArrayInputStream(watermarkArray);
                Image logoImage = ImageIO.read(watermarkIn);

                int width1 = logoImage.getWidth(null);
                int height1 = logoImage.getHeight(null);

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
                g.rotate(Math.toRadians(30), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

                int x = -width / 2;
                int y;

                while (x < width * MULTIPLE) {
                    y = -height / 2;

                    while (y < height * MULTIPLE) {
                        g.drawImage(logoImage, x, y, null);
                        y += height1 + INTERVAL;
                    }

                    x += width1 + INTERVAL;
                }

                g.dispose();

                byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, suffix, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedImage != null) {
                    bufferedImage.flush();
                    // null是告诉jvm此资源可以回收
                    bufferedImage = null;
                }
                IOUtils.closeQuietly(byteArrayOutputStream);
                // 是让系统回收资源，但不一定是回收你刚才设成null的资源，可能是回收其他没用的资源
                System.gc();
            }
        }
        return content;
    }

    private static final String WATERMARK_BASE64 = "data:image/png;base64," +
            "iVBORw0KGgoAAAANSUhEUgAAAUEAAABRCAYAAABMrxQeAAAQVklEQVR4nO2d23McV53HPy" +
            "OPZceyseXbOImVRHIcTLE4FGR3YaFIFQUUVfDMH8kLT8ALUFyqthYSWDCXGOzIlnyJZRxZvs" +
            "jEkuzh4Tunejzpc7r79GV6Zn6fKpVcnp7uM5qZb//up8P0cAA4A7wMHAWOAHODx54Cj4D7wB1g" +
            "A3g+hjUahlEh/X4/97GdTif9/6tazBjoAMdJhG+R/K9nB1gF/o4E0jCMCWQWRXAeiZ4TvgMlz7" +
            "cL/Bn4EMj/1zQMoxXMggh2gGNI9F5Bll8da/4Q+H0N5zUMo0aqEMFuVYupkHngNBK9M8DBBq5" +
            "5DtgGLjdwLcMwWkRbRPAYcm/PACcpbu09BT5CSY+HwBNgD9gHHELxwqXBNXxcHDxvveC1Dc" +
            "OYYMblDneBHom191LEObaQ8N0GNskX0zsJ/A9+6/I58CvgnxHrMQyjYSYtJniUJKFxkqR8" +
            "JS/PUWmLE74nkes4BnwrcP1d4OfIojQMo8W0XQS7KLbn3NyFiHN8ggTvNnAXubhp7EOW5VngFHA" +
            "L+GPgvK8D/x14/Anws8H1DcNoKW0UwSNI9F5GYlTU2gMVNDvh28Lv5u4fXOcsEtnR+Oaf" +
            "gQ8C1/kySoj42AJ+gV94DcMYM20QwX0k1t7LxFl7z1BC46PBz78Cxx4AXkXCd5pskf0tsOZ5" +
            "bA74Jiq78XEH+A1WQ2gYrWRcIriAEhrO2tsXcY4nJLG9u0gIQ9dzwneCYmveBX6Uce5vo7IcH9" +
            "eA9wpc0zCMhmiqTnAOWV0uqXEk91UT+nzazQ1xFAnfq6i8JYYnwJWMY7aB3wFfDxyzPDjub5Hr" +
            "MAyjxYSsquPAm0iI9kecew9lc28jqy+UZOggsTs7uF6M0ILE6gZKjOQtmwH4IvBWxjG/A65Hrssw" +
            "jBqoyxI8hEThbMSatklE7y7hSS0d5E47i+9QxPVA02Gc8N2PPMcdskXwHfT6pqWG8CLpVvZ94FLD" +
            "a6mKHwQe20A1oE0yT7YnszH07wXgcH3LycVj9DkvygVUoTHKNvB+4HnvACuex95Hg05G+R6D/MOIs" +
            "P243+8XXvuoCJ4BvkI4RjZMH/iYRPgeZBzvSlmc8OW9zigPSIQv65pZdJHFm8Uc8FXgJ0xHxn" +
            "iR9A+tUR2LwLsZx/xw6N9L6OY0Ti5RvH10Af+6QwI4j18AId2oWcKfgF0iovV1WARfAb5Gvs" +
            "TDPZQwuE32KCpXyvLq4Hdsq959JHw30d2qDHNI8JfQ687r7h9E1utHJa8fwyLxN400fK95" +
            "P9WLYxnrwocbh2aMn895/n+b8HuU5W2mCWvIUr7Q6XRSP7v9ft/rBThBOoasnCwB3AT+H1l/IQ4" +
            "gcTmLvlAx9YL9wfVuIvGL7RBxxArfMP9ifJ0kF2nGcjtOtvVSlBjrokfYKppUl33aWMBvzYXqd" +
            "MEvno6in/f5iOfQRcL3X2SXulxBXRi+SOQhksRGzBAEBuf+JxK+W4RrBvNQhfA9QxbvdWwidZOE3" +
            "CTQjdEYPzFWYA+9vzF1xZXTRaJ1LOO4K8gC9HECFR7HCt8GEr2blJ/07Ep6lpAoxwhfHyV21tGXbR" +
            "pigJPEAnr/fGwQ514b1VLUCrzA+GOen6KL+mhDbAF/yjimTzEBfI4ysrcGPzsFnpvGsPCVSbhsoQ6" +
            "TdcpboUY8WW6SWYHtIDYW2Cq6KNAf4i9ku4CbKPgdClo+QwmFW8i93M25Rh9VCZ+bIbhG+UyzUZ55s" +
            "gPmK4QtxTTyZGqLUEe5zWUmZ7BvmVhgq+gSFo8d8mdC14DPex7bBX5K+aksVQnfLrIm1lCme9J7g2" +
            "O+kF8gvW96Ew2fKMoi1bg6K2S/r6F+bx9RQfOS3Kf52sSmqNMK/GHgsR7+m9mlfr8fVSITcmWLdF2s4xf" +
            "BDuXjageA7xK/udJzJOhryBKdpgTHRvYhn8JXfrIbeb6qCJXFTBo7jPdvWRdTYwWCRPAh6tVNIzTYYJRH" +
            "SDTT7tJdZL1dK7S6F9kpuB7HPSR8NygfezTqJY8VaIyfWCvwBkkBtM8TaZwuioP5RLDoJkfr+F/YCuVEsA" +
            "9cJZ/L9ZAkwWFZxMlgnuyEiDF+iliBvgL/HcrnBCrDiaCPRVQ/mNcCWwfeJt29PoHEtkzyYRW53Gk1jZ+QJ" +
            "Dhie4iNZhnuX666I8aoh7xW4CIaU5dGqwrds0RwDmWP7+Q83yeovs4XgF4mPPY+ix0kcu5OtIeyzWso9jLp" +
            "CY5Zo0j/8jbZ7ZKhc+1gN8eyFLECfR6ba3dsTd96lgiCsrF5RRBkjfle4OvoLlAmKXEZrduV21gh82zwS7" +
            "JDG6EpMtOYqc0zpaYooZtFXiuwh18DLtOy2HwX1cnt4u+sOF3wnDeAL5Husrqe4psFzznMY+D/Sjy/CF0UC" +
            "jALc7xcZjZiu23oqPCNHCtiBb7jOa6VQy9cicxDFLNLYxEJZN5A5h4qQfEVs65QTgTrZg4V6y6ju5ntMz" +
            "JedpicAmKHb1ZjGpNinYaSVksk3/f9+HuC81qBIYs+xMVOp+O7iXiHeLgpMg/wi2AHWYO3CixmDb8I9t" +
            "CwhbJTYarmM0ig3+DFAP0ZNGQ21Ds9bmJq63zdPYcrPl8ZdpBItMp9ysE0zmoMDTvI81pbaQXCiyIYwu3" +
            "lm5c76EWnZfs6yMr6a4Hz1cU+4DW0npOB486jv1Er30SqdaFCAzKr5gbhYuIbzIYbPAu0LhbocCKYtfFR0" +
            "bvac+Ty+mIIb6CNi8blYh5Da3ud/FNmvoQKwqdlvH4baOtNxaiW1lqBkAw7zRoUepTi7Wq+/X5B1kbRhE" +
            "tZukj4vgV8B43ULzJmaw4JoWEYxWitFQiJJfgU1fiFOkROU2yE0T0U9/NtoLRCM32VJ0imjsSO9ge9iR9" +
            "WsiLDSCdtikyo6Bj0nYy1spr4/sVYgaF1HabiYazDovCAakWwj2oGfUF2NwWmjjvEPIr1ncPfEpiXe0j8" +
            "bhLXu2zMHpd4MR5epk82a2TYJZqJm4bit+conxEeJpQxD5URBafI5Nly8wHh2F+M+xoSwTkUG/xHxHnT6" +
            "KDkhrP6YvY1ceyicfqrTMaMwWkapTUNjBYbl5mMExLBVZpLHPmsuVAirdWxQMeoCIY4QvHSli3CAxqWK" +
            "S+CB5CYrhC/abtjE1l960yW1TdNo7SMhB5h168NY6tC9YOtjgU6hkUwK0MMKpUJJTzSWMN/pziKrJHNgud" +
            "0tYsryK0ua/WtoTtWnr+BYTRFW6xAH4v4K0AmwgqEF0XwIdl7hfQoLoI3CLtJK+QXwZdIrL6ywdH7JFaf9" +
            "R8bbSPUpgblrcB3SQ9/+drm0gh9ryfCCoQXRfAZ6ssNuZQxccFtlFzwFSMvoW4Mn/vZQZu2L6O+45gd7Rx" +
            "7SJSvYhNFjHYTcjPbUEQeGpKwzQS1Oo6WjDwkLIKHUIo6a6TRKGv4RXA/EsLrI/+/gITvDfxlNnnZQqb5" +
            "deqx+g6jusOjaHDseg3XMGaHLCuwDW5mKNnThlhlbkZFcAvF2EKcprgIuskyPivuHBKoOWTtraC7TBmr" +
            "7xlJDdW9Eufx0UFrfBNZqm6tPeS2/72GaxqzQcgK3GD8iaslwlZgWZEOCWyogqXnK4NBnl/q3y3NEsy" +
            "iR/EX6Xate8Xz+AnUxfES8RspOR6SWH11xCTcXs3n0dCFNN4e/DYhNIqSZQVW4WaW3XkvFAuswgqMLbU" +
            "KueiXyCmCeTPEHYr3/a7hF0FQP28sz0msvrp6exeQ1bdMvjHwb6PMcxtcF2Ny8M3iA93UFyk3SHWe4ns" +
            "2DxNKSk7UpuuOURF8jAQlVHJyEFlARYuIb6N4XJnWtVEekVh9Tys87zCngLeIS8p8GX1w656fGHNX9/VN7" +
            "4883xcinmO8SNYIrnnGX5AectUfE3ZlW1k2MypIbsBqllV2iuIi+AyJwRsFnzfKczTW6yr1bZzuRmydp5y" +
            "F2gG+goay1hnH8W1GHcPxis8H/rBEk1tsxs5J9OGNMZWgDRtNhV7TBbLnCoZEfIMJEEGQS5z1xe8hESrK" +
            "OvEiuI3q+q5Rn9V3CCVpzlHdB3IO+BrwY+pbd9vxlSOFAuxVU/WcRG+MacIJld5UeRNpDWkimMfCi40Lb" +
            "pA9rWaYPrL6Vql3N7mTyOo7S7mMtI8uCiHM4ixC2+VtctgmPCSlKUs1tCXnYfyJo1Dm3PsZTBPBPBnieW" +
            "QtFv1w99Ef+XzGcU+Q8K0i0ayDORKXt+odu0bpM/7i1nExMUWzM84O8L/jXsSA0GemR0AEy06RceSN9Z0m" +
            "7g6/RroI9lHyZBWN56/L6juI3N03KV+Ok4dt4A+0b0+VunEbJJkI5meH5l1st5/zKhPS5lY1aSKYtQWno0" +
            "dcHdwmyuq6zpQnKM53jXqF4jjK8p6l3MCFvNxFcdNbVCPoozPqylL1KK1hHjO7lm8Z2r43clmBbmVYxFeu" +
            "soXifiFOEhcXBPUKv4ayxR9FniMPbvvM8/h306uSZ8jSvUL1cwir/gDZKK128A7w/rgXkZM2C3Q0PhF8QL" +
            "YIdpGwxLSk3Rn81MU8icv7Uo3XcbjM9cy6FJFU/aUK7VdbZDpKk6wgr+v32GdnLIREMA+nqKcvN5ajyOV9" +
            "DdX61c1dZPXdZno2Zy+y+ZSRTZ4WtSUUmviAFtbRTTs+EcyTIQa9ueOeGOFGbZ2nmZqzOl3eNnAc1dRZTK" +
            "8888A3ch67gFzjiyhMtMF4EiUzR1lL8CSyuMYxit5toXmeinef8jBLLu/3SL58VXwJ6+iuaDPvIos6ZnOl" +
            "efS5Dg1RqBNf2KBH+U6ivCGJUFH24cBjoSky3pY9nwjuEN4u0zGH4oJ3M46rksNI+Japtg/Zxway+upM4L" +
            "SR3sjvMkxrd4WPprpgppU6psh4W/ZCIvKAfMNMz1K/CLrZfeeBM9TT1THMHnJ5rzKdLi/IOrMvqzHzhETw" +
            "IYq1ZbGMCmLrqPHbh3qNQ7P7qmQbCd81pt/l3WBKe0EniEvIiIjdk9iogJAI5t38aB/wn8Cvqc5dPITKW5" +
            "qaMjKLLq9zD8YVe5p1XDfNKroZ2Q1pTIRE8OMC5+khIXyPeBFxm6e/SX2DDIaZBZc3i/fRza5HkhU26mMT" +
            "eRs3SQYV7CCL8EOSqTqLtGOs1kyQJTTfodg8vXuo6LOIqBxD+5q8TjjzUxXbyOq7hrojDMNonqaF3jvNKE" +
            "sELxCXqdlAd7tNFCt8itzmg8jVPYqsvlM009Hh1jRrLq9hTDX9fv6vsq98JksEDwDfp5nuizrYQ6P3r5K/A" +
            "NwwjAmhChHMqrN7imIVb+W+Ujswl9cwjFzkKTb+AJWpTEKg9g6y+szlNQwjF3kzsMso+9tG9pDFdxXNKTQM" +
            "Y0Zowh12XEOp+9dyX7F+HpH08u6NeS2GYUwoRXpv30N1ZE0MJw1xB8X76hzBbxjGjFC0IHk/miTRdJuPub" +
            "yGYXyKJkpk0ugCXyVfX3FZzOU1DMPLuETQPe+zwH9Q/aZFfRKXt869hg3DmHDGKYKOI8AXqcYq3EWFzVfQ" +
            "bmWGYRhB2iCCjhNo8MESxS3DR0j4rmMur2EYBWiTCDq6qJTmFGqQXkD9wk4Yd5CV9wD1FX88+Le5vIZhFK" +
            "YKETQMwzAMwzAMwzAMwzAMY7b4N7ezyPoxXLVqAAAAAElFTkSuQmCC";
}
