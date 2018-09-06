/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月8日 下午12:20:18 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.codec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stc.skymobi.util.ByteUtils;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月8日 下午12:20:18 
 * @version V1.0
 */
public class AESCoder {
	private static final Logger log=LoggerFactory.getLogger(AESCoder.class);
	//加密算法
	//AES加密位： 128位，加密模式：CBC， 填充模式：NoPadding
	//AES密钥：0102030405060708090a0b0c0d0e0f10
	//AES初始向量(IV)：0102030405060708090a0b0c0d0e0f10
	//AES密钥与初始向量说明：AES中密钥和初始向量为16个字节的数据，本文采用的密钥为32个0~F的字符，刚好两个字符拼起来当做16进制数为一个字节。

	//MD5密钥：IJKLMNOPQRSTUVWX
	
	/**
	 * 16进制字符串转换为10进制字符串
	 * @param input
	 * @return
	 *//*
	public static String hexStrToStr(String input) {  
        if (input == null || input.equals("")) {  
            return null;  
        }  
        input = input.replace(" ", "");  
        byte[] baKeyword = new byte[input.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(  
                		input.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
        	input = new String(baKeyword, "utf-8");  
            new String();  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return input;  
    }*/  
	/**
	 * 加密
	 * @param content
	 * @param secretKeyStr
	 * @param ivStr
	 * @return
	 */
	public static byte[] encrypt(String content, String secretKeyStr,String ivStr){  
        try {  
            /*KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(secretKeyStr.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded(); */ 
            SecretKeySpec key = new SecretKeySpec(secretKeyStr.getBytes(), "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8"); 
            System.out.println("加密内容的长度="+byteContent.length);
            cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(ivStr.getBytes("utf-8")));// 初始化  
            int blockSize = cipher.getBlockSize();
            int plaintextLength = byteContent.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext= Arrays.copyOf(byteContent, plaintextLength);
            //byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(byteContent, 0, plaintext, 0, byteContent.length);
            byte[] result = cipher.doFinal(plaintext);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }catch (InvalidKeyException e) {  
            e.printStackTrace();  
        }catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        }catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return null;  
    }  
      
    /**
     * 解密
     * @param content
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] content, String secretKeyStr,String ivStr){  
        try {  
           /* KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(secretKeyStr.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded(); */ 
            SecretKeySpec key = new SecretKeySpec(secretKeyStr.getBytes(), "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(ivStr.getBytes("utf-8")));// 初始化  
            byte[] result = cipher.doFinal(content);  
            int i=0;
            for(byte b:result){
            	if(b==0){
            		i++;
            	}
            }
            if(i>0){
            	return Arrays.copyOfRange(result, 0, result.length-i);
            }
            
            return result; // 解密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        }catch (InvalidKeyException e) {  
            e.printStackTrace();  
        }catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        }catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        return null;  
    }  
    public static void  main(String args[]) throws Exception{
    	/*String origalStr="中国人12345678中";
    	System.out.println("加密前的数据="+origalStr);
    	byte[] origalStrBytes=origalStr.getBytes("utf-8");
    	System.out.println("加密前的数据16进制:"+ByteUtils.bytesAsHexString(origalStrBytes, origalStrBytes.length));
		byte[] encryptBytes=AESCoder.encrypt(origalStr,HexUtil.hexToStr("0102030405060708090a0b0c0d0e0f10"),HexUtil.hexToStr("0102030405060708090a0b0c0d0e0f10"));
		System.out.println("加密后的数据16进制:"+ByteUtils.bytesAsHexString(encryptBytes, encryptBytes.length));
		byte[] origalBytes=AESCoder.decrypt(encryptBytes,HexUtil.hexToStr("0102030405060708090a0b0c0d0e0f10"),HexUtil.hexToStr("0102030405060708090a0b0c0d0e0f10"));
		System.out.println("解密后的数据="+new String(origalBytes));
		System.out.println("解密后的数据16进制:"+ByteUtils.bytesAsHexString(origalBytes, origalBytes.length));
		Integer a1=0x55;
    	Integer a2=0xAA;
    	Integer a3=0x55;
    	Integer a4=0xAA;
    	byte[] startBytes={a1.byteValue(),a2.byteValue(),a3.byteValue(),a4.byteValue()};
    	System.out.println(HexUtil.bytesToHex(startBytes));*/
    	String s="0sdfsadsdf";
    	byte[] ss=s.getBytes();
    	for(byte b:ss){
    		System.out.println(b);
    	}
    	
		
	}
}
