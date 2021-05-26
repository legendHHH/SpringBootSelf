package com.qcl.datastructure.sparsearray;

import java.io.File;
import java.io.FileOutputStream;

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
            //第一行数据为了初始化数组使用
            if (i == 0) {
                chessArr2 = new int[sParseArray[0][0]][sParseArray[0][1]];
            } else {
                chessArr2[sParseArray[i][0]][sParseArray[i][1]] = sParseArray[i][2];
            }
        }

        for (int[] arr : chessArr2) {
            for (int data : arr) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }


    public static String writeDataToFile(Object data) {
        try {
            File file = new File("D:/WorkCode/logs/sparseArray.data");
            FileOutputStream fos = new FileOutputStream(file);
            StringBuffer sb = new StringBuffer();
            sb.append(data);
            sb.append("\t");
            sb.append("\n");
            fos.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return "写入失败";
        }
        return "写入成功";
    }
}
