package com.qcl.fastdfs.helper;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片压缩--处理历史图片
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/26
 */
public class New {

    public static void main(String[] args) {
        //分页查询数据库获取到所有的图片url
        //对图片url做一个手动分页查询(查询处理完成后删掉原图)
        //调用图片压缩处理的方法
        //替换上传图片,记录数据是否一致
        uploadFileAndCreateThumbnail();
    }

    public static void uploadFileAndCreateThumbnail() {
        //拼接后台文件名称
        //10.0M===>224K
        //String thumbnailPathName = "D:\\Other\\BackgroundImage\\apple.jpg";

        //3.25M===>184K
        String thumbnailPathName = "D:\\Other\\BackgroundImage\\2.jpg";

        //1.28M===>584K
        //String thumbnailPathName = "D:\\Other\\BackgroundImage\\Ubuntu10.png";

        //768K===>270K
        // String thumbnailPathName = "D:\\Other\\BackgroundImage\\es-overview-1-180716-2880x1480.jpg";

        //359K===>117KB
        //String thumbnailPathName = "D:\\Other\\BackgroundImage\\10.jpg";

        File file = new File(thumbnailPathName);

        if (file == null || !file.exists()) {
            return;
        }

        long size = file.length();
        double scale = 1.0d;
        System.out.println("图片大小为：" + size / 1024 / 1000 +"Kb");
        if (size >= 200 * 1024) {
            scale = (200 * 1024f) / size;
            System.out.println(scale);
        }

        //拼接文件路径
        String thumbnailFilePathName = thumbnailPathName.substring(0, thumbnailPathName.lastIndexOf(".")) + "_min.jpg";
        System.out.println("名字：" + thumbnailFilePathName);
        try {
            if (size > 200 * 1024) {
                //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
                Thumbnails.of(thumbnailPathName).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(thumbnailFilePathName);
                //Thumbnails.of(thumbnailPathName).size(360,360).outputFormat("jpg").toFile(thumbnailFilePathName);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            //原图地址
            map.put("originalUrl", thumbnailPathName);
            //缩略图地址
            map.put("thumbnailUrl", thumbnailPathName);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
