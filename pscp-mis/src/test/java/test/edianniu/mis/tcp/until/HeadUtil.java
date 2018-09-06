package test.edianniu.mis.tcp.until;

import java.util.UUID;

public class HeadUtil {
    private static byte[] baseversion = ByteUtil.int2bytes(2, 1);//版本
    private static byte[] jsonbaseversion = ByteUtil.int2bytes(3, 1);//版本
    private static byte[] type = ByteUtil.int2bytes(1, 1);//类型
    private static byte[] resver = ByteUtil.int2bytes(0, 3);// 预留字段
    public static byte[] tcphead(byte[] body, byte[] msgCode) {
        byte[] transaction = UUidUtil.getIdentification(UUID.randomUUID());
        byte[] tcphead = ByteUtil.pack(baseversion, ByteUtil.int2bytes(body.length + 32, 3), transaction, type,resver,msgCode,ByteUtil.int2bytes(body.length,4),body );
        return tcphead;
    } 
    public static byte[] tcpheadJson(byte[] body,byte[] msgCode){
    	byte[] transaction = UUidUtil.getIdentification(UUID.randomUUID());
        byte[] tcphead = ByteUtil.pack(jsonbaseversion, ByteUtil.int2bytes(body.length + 32, 3), transaction, type,resver,msgCode,ByteUtil.int2bytes(body.length,4),body );
        return tcphead;
    }

}
