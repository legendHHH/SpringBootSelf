package com.qcl.datastructure.sparsearray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 稀疏数组
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/26
 */
public class SparseArray {

    public static void main(String[] args) {
        streamFileRead();
        //创建一个原始二维数组 11 * 11
        //0：表示没有棋子 1：表示黑子,2：表示蓝子
        int[][] chessArr1 = new int[11][11];

        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;

        //输出原始二维数组
        System.out.println("输出原始二维数组~~");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //记录有效数字个数
        int sum = 0;

        //行数
        int row = 0;
        //列数
        int col = 0;

        //将二维数组转化为稀疏数组的思想
        //1.先遍历二维数组得到非0数据的个数
        for (int[] chessArrTemp : chessArr1) {
            row++;
            for (int data : chessArrTemp) {
                col++;
                //记录有效个数
                if (data != 0) {
                    sum++;
                }
            }
        }

        col = col / row;
        System.out.println("行数：" + row + "列数：" + col);

        //2.创建对应的稀疏数组(根据sum创建)
        int[][] sParseArray = new int[sum + 1][3];

        //计数器用于记录是第几个非0数据
        int count = 0;
        //给稀疏数组赋值
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                //初始化第一行记录数据
                if (i == 0) {
                    sParseArray[0][0] = row;
                    sParseArray[0][1] = col;
                    sParseArray[0][2] = sum;
                } else {
                    //遍历二维数组将非0的数据存入到sParseArray
                    if (chessArr1[i][j] != 0) {
                        count++;
                        sParseArray[count][0] = i;
                        sParseArray[count][1] = j;
                        sParseArray[count][2] = chessArr1[i][j];
                    }
                }

            }
        }

        System.out.println("\n行数\t列数\t值");

        //输出数据第一种格式
        System.out.println("第一种格式");
        for (int i = 0; i < sParseArray.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sParseArray[i][0], sParseArray[i][1], sParseArray[i][2]);
        }

        //输出数据第二种格式
        System.out.println("\n第二种格式");
        for (int[] arr : sParseArray) {
            for (int data : arr) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        System.out.println("\n将稀疏数组转化为二维数组~~~~");
        //将稀疏数组转化为二维数组
        int[][] chessArr2 = null;
        for (int i = 0; i < sParseArray.length; i++) {
            for (int j = 0; j < sParseArray[i].length; j++) {
                //第一行数据为了初始化数组使用
                if (i == 0) {
                    chessArr2 = new int[sParseArray[0][0]][sParseArray[0][1]];
                } else {
                    //列的数据根据 sParseArray[i].length 行数里面的长度决定  然后通过 长度减1 来获取指定的数据
                    chessArr2[sParseArray[i][sParseArray[i].length - 3]][sParseArray[i][sParseArray[i].length - 2]] = sParseArray[i][sParseArray[i].length - 1];
                }
            }
        }

        for (int[] arr : chessArr2) {
            for (int data : arr) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //写入数据到文件
        try {
            String result = writeDataToFile("chessArr1.data", chessArr1);
            String result2 = writeDataToFile("sparseArray.data", sParseArray);
            System.out.println("写入数据完成......" + result + "  " + result2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        readFileToData("sparseArray.data");
    }

    /**
     * 写入数据到文件
     *
     * @param fileName
     * @param data
     * @return
     * @throws IOException
     */
    public static String writeDataToFile(String fileName, Object data) throws Exception {
        if (fileName == null) {
            throw new RuntimeException("文件后缀名不规范");
        }
        File file = new File("D:/WorkCode/logs/" + fileName);
        FileOutputStream fos = null;
        StringBuffer sb = new StringBuffer();
        try {

            if (!(data instanceof int[][])) {
                throw new RuntimeException("非二维数组");
            }

            fos = new FileOutputStream(file);
            int[][] dataArr = (int[][]) data;
            for (int i = 0; i < dataArr.length; i++) {
                for (int j = 0; j < dataArr[i].length; j++) {
                    sb.append(dataArr[i][j]).append("\t");
                }
                sb.append("\n");
            }
            fos.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "写入失败";
        } finally {
            fos.close();
        }
        return "写入成功";
    }

    /**
     * 使用stream流来读取文件
     *
     * @return
     */
    public static String streamFileRead() {
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get("D:/WorkCode/logs/sparseArray.data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.forEach(System.out::println);
        return null;
    }

    /**
     * 读取文件转化为数据
     *
     * @param fileName
     * @return
     */
    public static int[][] readFileToData(String fileName) {
        int[][] result = null;
        try {
            FileInputStream fis = new FileInputStream(new File("D:/WorkCode/logs/" + fileName));
            StringBuffer sb = new StringBuffer();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            System.out.println("读取的数据：" + sb.toString());
            String[] row = sb.toString().split("\n");

            for (int i = 0; i < row.length; i++) {
                String[] dataStr = row[i].split("\t");
                int[] data = new int[dataStr.length];
                for (int j = 0; j < dataStr.length; j++) {
                    data[j] = Integer.parseInt(dataStr[0]);
                }
                if (i == 0) {
                    result = new int[data[1]][data[1]];
                } else {
                    //result[data[i]][data[i]] = row[i][data[i]];
                }
            }

            //输出结果
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    System.out.printf("%d\t", result[i][j]);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
