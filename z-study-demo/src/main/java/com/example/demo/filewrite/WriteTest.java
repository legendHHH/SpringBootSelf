package com.example.demo.filewrite;

import java.io.*;
import java.util.*;

/**
 * XXX
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/18
 */
public class WriteTest {

    public static void main(String[] args) {
        String property = System.getProperty("user.dir");
        readFileByLines(property + "\\src\\binpack(2).txt", 1);
        //readFileByLines(property + "\\src\\binpack(3).txt", 2);
        //readFileByLines(property + "\\src\\binpack(4).txt", 3);
    }

    static int Frec(int n) // ASSUME N >= 0
    {
        if (n == 0) return 1;
        return n * Frec(n - 1);
    }

    static int Floop(int n) // ASSUME N >= 0
    {
        int f = 1;
        while (n > 0)
            f *= n--;
        return f;
    }


    static void swap(int x, int y) {
        int temp = x;
        x = y;
        y = temp;
    }

    static void mystery(int[] a1, int[] a2) {
        a1 = a2;
    }

    public static void writeDictionary(String result, String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(result);
            fw.close();
        } catch (FileNotFoundException e) {
            // File not found
            System.out.println("FileNotFoundException e");
        } catch (IOException e) {
            // Error when writing to the file
            System.out.println("FileNotFoundException e");
        }

    }


    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param fileName
     * @param type
     */
    public static void readFileByLines(String fileName, int type) {
        List<String> fileStringList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //111
            StringBuffer sb = new StringBuffer();
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                //获取文件名字
                if (tempString.contains(" u250_0")) {
                    fileNameList.add(tempString);
                }
                Map<String, Integer> genRandomMap = genRandom(type);
                // 显示行号
                System.out.println("line " + line + ": " + tempString);

                //u250 或者 150 就跳过当前行并且作为分割点
                if (tempString.contains(" u250_") || tempString.contains(" 150 250")) {
                    String result = sb.toString();
                    if (result != null && !"".equals(result)) {
                        fileStringList.add(result);
                    }
                    sb = new StringBuffer();
                    continue;
                } else {
                    sb.append(tempString).append("\t\t").append(genRandomMap.get("start")).append("\t\t").append(genRandomMap.get("end")).append("\r\n");
                    System.out.println("lin:" + sb);
                    line++;
                }

            }
            //将最后一次的数据保存
            fileStringList.add(sb.toString());
            reader.close();

            //循环写入文件
            for (int i = 0; i < fileStringList.size(); i++) {
                writeDictionary(fileStringList.get(i), "D://u250_0" + (type - 1) + "_" + i + ".txt");
            }

            //System.out.println(fileNameList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static Map<String, Integer> genRandom(int type) {
        Map<String, Integer> map = new HashMap<>(2);
        int start = -1;
        int end = 0;
        Random rand = new Random();
        if (type == 1) {
            start = rand.nextInt(4) + 1;
            end = rand.nextInt(5) + 1;
            if (start >= end) {
                end = start + 1;
            }
        } else if (type == 2) {
            start = rand.nextInt(9) + 1;
            end = rand.nextInt(10) + 1;
            if (start >= end) {
                end = start + 1;
            }
        } else if (type == 3) {

        }
        //System.out.println(start + "	" + end);
        map.put("start", start);
        map.put("end", end);
        return map;

    }

}
