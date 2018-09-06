package com.edianniu.pscp.mis.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class CheckCodeUtils {
	private String checkCode;
	private byte[] checkCodeImg;

	public CheckCodeUtils() {
		int width = ConstantUtils.AUTHCODE_WIDTH;
		int height = ConstantUtils.AUTHCODE_HEIGHT;
		BufferedImage image = new BufferedImage(width, height, 1);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("Arial", 0, 18));

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		this.checkCode = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			this.checkCode += rand;

			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			g.drawString(rand, 13 * i + 6, 16);
		}
		g.dispose();
		try {
			this.checkCodeImg = bufferedImageToByteArray(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static byte[] bufferedImageToByteArray(BufferedImage image)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "gif", baos);
		return baos.toByteArray();
	}

	public String getCheckCode() {
		return this.checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public byte[] getCheckCodeImg() {
		return this.checkCodeImg;
	}

	public void setCheckCodeImg(byte[] checkCodeImg) {
		this.checkCodeImg = checkCodeImg;
	}
}
