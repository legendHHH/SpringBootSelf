package com.example.demo.java2py;

import java.io.*;

/**
 * Java调用python脚本(直接通过Runtime实现)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/11
 */
public class Java2Python {
    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String exe = "python";
        String command = "D:\\WorkCode\\ideaWork\\SpringBootSelf\\z-study-demo\\src\\main\\java\\com\\example\\demo\\java2py\\calculator_simple.py";
        String num1 = "1";
        String num2 = "2";
        System.out.println("\nExecuting python script file now.");
        String[] cmdArr = new String[]{exe, command, num1, num2};
        Process process = Runtime.getRuntime().exec(cmdArr);
        process.waitFor();
        // 定义Python脚本的返回值
        String result = null;
        // 获取CMD的返回流
        BufferedInputStream in = new BufferedInputStream(process.getInputStream());
        // 字符流转换字节流
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        // 这里也可以输出文本日志

        String lineStr = null;

        while ((lineStr = br.readLine()) != null) {
            result = lineStr;
        }
        // 关闭输入流
        br.close();
        in.close();

        System.out.println(result);
    }

}
