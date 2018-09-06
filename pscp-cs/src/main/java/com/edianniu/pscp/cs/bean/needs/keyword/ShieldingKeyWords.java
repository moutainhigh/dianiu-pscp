package com.edianniu.pscp.cs.bean.needs.keyword;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * ClassName: ShieldingKeyWords
 * Author: tandingbo
 * CreateTime: 2017-09-22 15:06
 */
public class ShieldingKeyWords {
    /**
     * 根节点
     */
    private TreeNode rootNode = new TreeNode();

    /**
     * 关键词编码
     */
    private String charset = "utf-8";

    /**
     * 创建DFA
     *
     * @param keywordList
     * @throws UnsupportedEncodingException
     */
    public void createKeywordTree(List<String> keywordList) throws UnsupportedEncodingException {
        for (String keyword : keywordList) {
            if (keyword == null) continue;
            keyword = keyword.trim();
            byte[] bytes = keyword.getBytes(charset);
            TreeNode tempNode = rootNode;
            for (int i = 0; i < bytes.length; i++) {
                int index = bytes[i] & 0xff;
                TreeNode node = tempNode.getSubNode(index);
                if (node == null) {
                    node = new TreeNode();
                    tempNode.setSubNode(index, node);
                }
                tempNode = node;
                if (i == bytes.length - 1) {
                    tempNode.setKeywordEnd(true);
                }
            }
        }
    }


    public String searchKeyword(String text) throws UnsupportedEncodingException {
        return searchKeyword(text.getBytes(charset));
    }

    public String searchKeyword(byte[] bytes) throws UnsupportedEncodingException {
        StringBuilder words = new StringBuilder();
        if (bytes == null || bytes.length == 0) {
            return words.toString();
        }
        TreeNode tempNode = rootNode;
        int rollback = 0;
        int position = 0;

        while (position < bytes.length) {
            int index = bytes[position] & 0xFF;

            tempNode = tempNode.getSubNode(index);
            if (tempNode == null) {
                position = position - rollback;
                rollback = 0;
                tempNode = rootNode;

            } else if (tempNode.isKeywordEnd()) {


                byte[] temp = new byte[rollback + 1];
                int j = 0;
                for (int i = position - rollback; i <= position; i++, j++) {
                    temp[j] = bytes[i];
                }
                String tempString = new String(temp, charset);

                int index1 = 0;
                int length = tempString.length();
                for (int i = 0; i <= rollback; i++) {
                    if (index1 < length)
                        bytes[position - i] = 42;
                    else
                        bytes[position - i] = 0;
                    index1++;
                }

                rollback = 1;
            } else {
                rollback++;
            }
            position++;
        }
        int leng = 0;
        for (byte aByte : bytes) {
            if (aByte != 0)
                leng++;
        }
        byte[] bytes2 = new byte[leng];
        int indexB2 = leng - 1;

        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] != 0) {
                bytes2[indexB2] = bytes[i];
                indexB2--;
            }
        }
        String result = null;
        try {
            result = new String(bytes2, "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
