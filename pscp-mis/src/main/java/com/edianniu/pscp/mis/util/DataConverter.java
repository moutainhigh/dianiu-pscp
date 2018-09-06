package com.edianniu.pscp.mis.util;

public class DataConverter {
    private static String hexString = "0123456789ABCDEF";

    /**
     * @param bytes
     *            字节数组
     * @return 16进制类型
     */
    public static String byteToString(byte[] bytes) {
        if (bytes != null) {
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
                sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
                sb.append(" ");
            }
            return sb.toString();
        }
        return null;

    }

    public static byte[] StringToByte(String str) {
        if (str != null) {
            str =str.replace(" ", "");
            int len = str.length() / 2;
            byte[] bytes = new byte[len];
            char[] achar = str.toCharArray();
            for (int i = 0; i < len; i++) {
                int pos = i * 2;
                bytes[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
            }
            return bytes;
        }
        return null;
    }

    private static byte toByte(char c) {
        byte b = (byte) hexString.indexOf(c);
        return b;

    }
    
    public static void main(String[] args) {
        String context = "000003E90000000400000005";
       System.err.println( DataConverter.byteToString((DataConverter.StringToByte(context))));;
    }

}
