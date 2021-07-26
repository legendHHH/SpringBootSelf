package com.qcl.fastdfs.helper;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static javax.imageio.ImageWriteParam.MODE_DISABLED;
import static javax.imageio.ImageWriteParam.MODE_EXPLICIT;

/**
 * 图片压缩测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/26
 */
public class New2 {

    public static void main(String[] args) {
//        uploadFileAndCreateThumbnail();

        String newFileName = "D:\\Other\\BackgroundImage\\es-overview-1-180716-2880x1480.jpg";
        String stpa = newFileName;
        String st1 = "D:\\Other\\BackgroundImage\\6666.jpg";
        boolean b = false;
        try {
            b = compressPic(stpa, st1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(b);
    }


    /**
     * @param srcFilePath  原图路径
     * @param descFilePath 保存路径
     * @return
     * @throws IOException
     */
    public static boolean compressPic(String srcFilePath, String descFilePath) throws Exception {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        // 指定写图片的方式为 jpg
        ImageWriter imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam imgWriteParams = imgWrier.getDefaultWriteParam();
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality((float) 1);
        imgWriteParams.setProgressiveMode(MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();

        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(16, 16)));
        try {
            if (StringUtils.isEmpty(srcFilePath)) {
                return false;
            } else {
                file = new File(srcFilePath);
                System.out.println(file.length());
                src = ImageIO.read(file);
                out = new FileOutputStream(descFilePath);
                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
