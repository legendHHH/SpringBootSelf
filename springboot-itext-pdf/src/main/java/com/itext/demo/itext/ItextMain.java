package com.itext.demo.itext;

import com.itext.demo.utils.PdfKitUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/4/18
 */
public class ItextMain {

    public static void main(String[] args) {
        // 将html代码读取到html字符串中
        String html = PdfKitUtil.readFileByUrl("https://www.cnblogs.com/blueskyli/p/9921832.html");

        try {
            Document document = new Document();
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("D:\\WorkCode\\3.pdf")));
            document.open();
            ByteArrayInputStream bin = new ByteArrayInputStream(html.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, null, new ChinaFontProvide());
            System.out.println("生成完毕");
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final class ChinaFontProvide implements FontProvider {

        @Override
        public boolean isRegistered(String s) {
            return false;
        }

        @Override
        public Font getFont(String arg0, String arg1, boolean arg2, float arg3, int arg4, BaseColor arg5) {
            BaseFont bfChinese = null;
            try {
                bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            return FontChinese;
        }
    }
}
