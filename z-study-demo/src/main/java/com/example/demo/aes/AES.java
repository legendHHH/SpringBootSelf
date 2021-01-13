package com.example.demo.aes;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


    /**
     * 加密
     *
     * @param src 加密字符串
     * @param key 密钥
     * @return 加密后的字符串
     */
    public static String Encrypt(String src, String key) throws Exception {
        // 判断密钥是否为空
        if (key == null) {
            System.out.print("密钥不能为空");
            return null;
        }
        // 密钥补位
        int plus = 16 - key.length();
        byte[] data = key.getBytes("utf-8");
        byte[] raw = new byte[16];
        byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        for (int i = 0; i < 16; i++) {
            if (data.length > i)
                raw[i] = data[i];
            else
                raw[i] = plusbyte[plus];
        }
        System.out.println(plusbyte[plus]);
        String res = new String(raw);
        System.out.println(res);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");    // 算法/模式/补码方式
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
        System.out.println(binary(encrypted, 16).length());
        return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);//base64
//        return binary(encrypted, 16); //十六进制
    }

    /**
     * 解密
     *
     * @param src 解密字符串
     * @param key 密钥
     * @return 解密后的字符串
     */
    public static String Decrypt(String src, String key) throws Exception {
        try {
            // 判断Key是否正确
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }

            // 密钥补位
            int plus = 16 - key.length();
            byte[] data = key.getBytes("utf-8");
            byte[] raw = new byte[16];
            byte[] plusbyte = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
            for (int i = 0; i < 16; i++) {
                if (data.length > i)
                    raw[i] = data[i];
                else
                    raw[i] = plusbyte[plus];
            }

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] encrypted1 = new org.apache.commons.codec.binary.Base64().decode(src);//base64
//            byte[] encrypted1 = toByteArray(src);//十六进制

            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);    // 这里的1代表正数
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static void main(String[] args) {
        try {
//        String[] keys = {
//                "", "123456","root"
//        };
//        System.out.println("key | AESEncode | AESDecode");
//        for (String key : keys) {
//            System.out.print(key + " | ");
//            String encryptString = aesEncode(key,"GY_TEST");
//            System.out.print(encryptString + " | ");
//            String decryptString =aesDecode("jFskfZ+viBTwTeVFcwyEBFZ1ZwynejUVHNDWXUZ23AaoMtTdj+E2dpSQ2X6OQuYYFggd+7mEPGyYJeD0pEBEv79G8NS6BbLc8Tx9RETXPxh8dfTY0D8dQepe0sBjbQiNoC4pIjjMr1iMDdAI0N/Z8TJrpwLY04D4WxksvH3J9pA=","GY_TEST");
////            String decryptString = new String(encrypt(encryptString,"GY_TEST"), "utf-8");
//            System.out.println(decryptString);
//        }

//            Map a = new HashMap();
//            a.put("userName","8a5a2d95-db59-11e9-908c-ec0d9acef91e");
//            a.put("mobie","18855629811");
//            a.put("personName","张三");
//            a.put("cardCode","7654321000000012");
//            String s = JSONObject.toJSONString(a);
//            System.out.println(s);
            //http://test.guodadrugstores.cn/ucenter/pukang/index?data=isshMSYguetB2avJ0pvia3F2pCXNhVjBlo3ZD4kgDT2I21Bw7UHdQ98w6cIop7cYFggd-7mEPGyYJeD0pEBEv79G8NS6BbLc8Tx9RETXPxi6bofJR-uuyF0-ESbcL4bIHrj4KjemxMsCrXKaG8uRRjJrpwLY04D4WxksvH3J9pA=&longitude=15.54&latitude=157.12&wxId=63
            String encryptString = "kyIzSm/cjlWlevAF5s/ZU7XCrVpOQ7nyrBUPhpgoRSkXnu5ntOeZ8YyXK90xl2EIaSIvn+mtAyuJXk2bgfPkJndMXwrNgQbxT4FZt8TyMohpbyqb6XnzJ3phb4Ydu6FsdWhopb61hjZNQgsSw2mK/r1Ta91gGh2IQpFV9of36MgYwywqbkrEYtcZikBb9K9x";
            System.out.print(encryptString);
            String decryptString =Decrypt(encryptString,"GY_TEST");
            System.out.println(decryptString);
            System.out.println(decryptString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}