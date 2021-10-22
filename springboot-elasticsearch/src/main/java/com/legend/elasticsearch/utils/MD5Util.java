package com.legend.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public final static String md5(String content) {
        //用于加密的字符
        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = content.getBytes();

            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成签名  md5 直播视频
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMd5_32(String str) throws Exception{

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update((str).getBytes("UTF-8"));
        }catch (Exception e){
            throw e;
        }
        byte[] b = md5.digest();

        int i;
        StringBuilder sb = new StringBuilder();

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        //{"code":"100094","pageNo":1,"pageSize":10}
        map.put("code", "100094");
        map.put("pageNo", 1);
        map.put("pageSize", 10);
        //map.put("secret", "5cc0d933a9b122a52f3d00a121a49ec2");
        String jsonString = JSONObject.toJSONString(map);

        String secret = "5cc0d933a9b122a52f3d00a121a49ec2";
        String res = MD5Util.md5(jsonString + secret);
        logger.info("加密参数：{}", jsonString);
        logger.info("加密后的字符串：{}", res);


        System.out.println(checkToken(jsonString, res));
    }



    public static boolean checkToken(String str,String token){

        //str是{"code":"100094121","pageNo":1,"pageSize":10}
        //secret是5cc0d933a91b122a52f3d00a121a49ec2
        String secret = "5cc0d933a91b122a52f3d00a121a49ec2";
        String mytoken = MD5Util.md5(str+secret);
        logger.info("visit token: " + token);
        logger.info("local token: " + mytoken);
        if(mytoken.equals(token)){
            return true;
        }
        return false;
    }

}
