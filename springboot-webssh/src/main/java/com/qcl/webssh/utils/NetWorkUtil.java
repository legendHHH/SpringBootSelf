package com.qcl.webssh.utils;

import cn.hutool.core.date.DateUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
public class NetWorkUtil implements Runnable {

    public static void main(String[] args) {
        NetWorkUtil nw = new NetWorkUtil();
        new Thread(nw).start();

//        getCurrentTime2();
    }


    @Override
    public void run() {
        while (true) {
            this.isConnect();

            try {
                // 每隔3秒钟测试一次网络是否连通
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断连接
     */
    private void isConnect() {
        Runtime runtime = Runtime.getRuntime();
        //获取系统的编码
        System.out.print(System.getProperty("file.encoding"));
        try {
            //执行对应的命令
            Process process = runtime.exec("ping " + "www.baidu.com");
            InputStream is = process.getInputStream();
            //windows的控制台编码格式是GBK
            InputStreamReader isr = new InputStreamReader(is, "GBK");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println("返回值为:" + line);

            }
            is.close();
            isr.close();
            br.close();

            if (null != br && !sb.toString().equals("")) {
                String logString = "";
                if (sb.toString().indexOf("TTL") > 0) {
                    // 网络畅通
                    logString = "网络正常，时间 " + this.getCurrentTime();
                    System.out.println(logString);
                } else {
                    // 网络不畅通
                    logString = "网络断开，时间 " + this.getCurrentTime();
                    System.out.println(logString);
                }
                //将信息写入日志文件
                this.writeIntoLog(logString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeIntoLog(String logString) {
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            file = new File("D:/WorkCode/logs/NetWork.log");
            if (!file.exists()) {
                // 如果不存在该文件，则创建
                file.createNewFile();
                //String sets = "attrib +H \"" + file.getAbsolutePath() + "\"";
                //将日志文件隐藏
                //Runtime.getRuntime().exec(sets);
            }
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            // 换行
            fw.append(logString + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得当前时间(旧版)
     *
     * @return
     */
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }

    /**
     * 获得当前时间(优化后)
     *
     * @return
     */
    public static String getCurrentTime2() {
        return DateUtil.date(new Date()).toStringDefaultTimeZone();
    }
}
