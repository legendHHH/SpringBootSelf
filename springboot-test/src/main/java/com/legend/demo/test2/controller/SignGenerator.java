package com.legend.demo.test2.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/15
 */
public class SignGenerator {

    public static String genSig(String baseUrl) throws Exception {
        String str = null;


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            str = byte2hex(md.digest(baseUrl.getBytes("utf-8")));
            return str;
        } catch (NoSuchAlgorithmException var3) {
            throw new Exception("ErrorEnum.SYS_ERR");
        } catch (UnsupportedEncodingException var4) {
            throw new Exception("ErrorEnum.SYS_ERR");
        }
    }

    private static String byte2hex(byte[] b) {
        StringBuffer buf = new StringBuffer();

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(genSig("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
