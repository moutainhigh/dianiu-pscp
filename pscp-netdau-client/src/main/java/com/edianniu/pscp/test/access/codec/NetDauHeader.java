package com.edianniu.pscp.test.access.codec;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import stc.skymobi.util.DefaultNumberCodecs;
import stc.skymobi.util.NumberCodec;

//0               1               2               3                
//0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7 0 1 2 3 4 5 6 7  
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                          start(4)                             |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                          dataLength(4)                        |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                          dataTag(4)                           |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                          dataContent(M)                       |
//|                                                               |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|            crc(2)            |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//|                           end(4)                              |                                                            |
//+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

//字段名称                字段大小     取值范围    功能说明
//头部
//(start)        4(byte)            协议开始数据，固定
//有效数据总长度
//(dataLength)   4(byte)            有效数据总长度，包括(dataContent+dataTag),高位字节序
//数据标识
//(dataTag)      4(byte)            保留，可以填充任意数值
//数据内容
//(dataContent)  M                  根据数据标识的不同，内容不同，数据内容为经过AES加密后的XML文本
//CRC校验
//（crc）                 2(byte)   N/A      crc校验使用
//头部
//(尾部)          4(byte)            协议结束数据，固定

//(data field)    n   n/a 唯一对应某个消息编码

public class NetDauHeader {
	private static final NumberCodec NUMBERCODEC = DefaultNumberCodecs.getLittleEndianNumberCodec();
	public static final int HEADER_LENGTH = 8;
	public static final int CONTENT_TAG_LENGTH=4;
	public static final int CRC_LENGTH=2;
	public static final int END_LENGTH=4;
	private String start;
	private String end;
	private int length;//包总长度
	private int contentLength;//有效数据总长度
	private int messageLength;//包体长度
	private int dataCrc;
	public NetDauHeader() {
	}
	
	public  NetDauHeader(byte[] headerBytes) {
		ByteBuf bytes = Unpooled.buffer(HEADER_LENGTH);
		bytes.writeBytes(headerBytes);
		byte[] startBytes = new byte[4];
		bytes.readBytes(startBytes);
		this.start =HexUtil.bytesToHex(startBytes);
		byte[] lenBytes = new byte[4];
		bytes.readBytes(lenBytes);
		this.setContentLength(NUMBERCODEC.bytes2Int(lenBytes, 4));
	}
	public ByteBuf toBytes(){
    	ByteBuf bytes = Unpooled.buffer(HEADER_LENGTH);
    	//协议头:开始
    	Integer a1=0x55;
    	Integer a2=0xAA;
    	Integer a3=0x55;
    	Integer a4=0xAA;
    	byte[] startBytes={a1.byteValue(),a2.byteValue(),a3.byteValue(),a4.byteValue()};
    	bytes.writeBytes(startBytes);
    	//协议头：有效数据长度
    	bytes.writeBytes(NUMBERCODEC.int2Bytes(contentLength, 4));
    	return bytes;
    }
	public ByteBuf toEndBytes(){
		ByteBuf bytes = Unpooled.buffer(END_LENGTH);
		//协议尾部
		Integer a1=0x68;
    	Integer a2=0x68;
    	Integer a3=0x16;
    	Integer a4=0x16;
    	byte[] endBytes={a1.byteValue(),a2.byteValue(),a3.byteValue(),a4.byteValue()};
    	bytes.writeBytes(endBytes);
    	return bytes;
		
	}
	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setStart(String start) {
		this.start = start;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
		this.messageLength=this.contentLength+CRC_LENGTH+END_LENGTH;
		this.length=HEADER_LENGTH+this.messageLength;
	}

	public int getDataCrc() {
		return dataCrc;
	}
	public void setDataCrc(int dataCrc) {
		this.dataCrc = dataCrc;
	}
	public int getLength(){
		return this.length;
	}
	public int getMessageLength() {
		return messageLength;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
