package com.itext.demo.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    //@Value("${filePath}")
    private String filePath = "D:/";

    //@ResponseBody
    @GetMapping(value = "/downloadForWord")
    public void downloadWord(HttpServletResponse response, HttpServletRequest request, String editorContent) throws Exception {
        //替换掉参数editorContent即可
        String content = "<h1>标题头</h1><h2>第二个标题</h2><a href=\"www.baidu.com\">百度搜索</a>";
        StringBuffer sbf = new StringBuffer();
        sbf.append("<html><body>");
        sbf.append(content);
        sbf.append("</body></html");
        exportWord(request, response, String.valueOf(sbf), "word1");
        log.info("导出word完成.....");
    }


    /**
     * 导出word
     *
     * @param request
     * @param response
     * @param content  富文本内容
     * @param fileName 生成word名字
     * @throws Exception
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String content, String fileName) throws Exception {
        //这里是必须要设置编码的，不然导出中文就会乱码。
        byte b[] = content.getBytes("GBK");
        //将字节数组包装到流中
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        //该步骤不可省略，否则会出现乱码。
        DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
        //输出文件
        request.setCharacterEncoding("utf-8");
        //导出word格式
        response.setContentType("application/msword");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "iso8859-1") + ".doc");
        ServletOutputStream ostream = response.getOutputStream();
        poifs.writeFilesystem(ostream);
        bais.close();
        ostream.close();

        //保存到本地一份文件
        FileOutputStream ostream2 = new FileOutputStream("D:/" + fileName + ".doc");
        poifs.writeFilesystem(ostream2);
        ostream2.close();

        poifs.close();
    }


    @GetMapping(value = "/downloadForPdf")
    public void downloadPdf(HttpServletResponse response, HttpServletRequest request, String editorContent) throws Exception {
        String content = "<h1>标题头</h1><h2>第二个标题</h2><a href=\"www.baidu.com\">百度搜索</a>";
        exportPDF(request, response, "pdf导出", content);
        log.info("导出pdf完成.....");
    }

    public void exportPDF(HttpServletRequest request, HttpServletResponse response, String title, String text) {
        Document document = new Document();

        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String((title + ".pdf").getBytes(), "iso-8859-1"));
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            //在下面，body中设置了style，设置了默认字体为宋体，这样导出时的html语言就默认带有了字体，汉字才会导出成功
            String content = "<html><body style=\"font-family: 宋体, SimHei;\">" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                    + title + "</span></p>" + text + "</body></html>";
            //这里是必须要设置编码的，不然导出中文就会乱码。
            byte b[] = content.getBytes("utf-8");
            //将字节数组包装到流中
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"));

            bais.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //@ResponseBody
    @GetMapping(value = "/preview")
    public void findPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String fileName = "pdfFile.pdf";
        String title = "test";
        String text = "<h1>标题头</h1><h2>第二个标题</h2><a href=\"www.baidu.com\">百度搜索</a>";

        Document document = new Document();
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File pdfFile = new File(filePath, fileName);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            //在下面，body中设置了style，设置了默认字体为宋体，这样导出时的html语言就默认带有了字体，汉字才会导出成功
            String content = "<html><body style=\"font-family: 宋体, SimHei;\">" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                    + title + "</span></p>" + text + "</body></html>";
            //这里是必须要设置编码的，不然导出中文就会乱码。
            byte b[] = content.getBytes("utf-8");
            //将字节数组包装到流中
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"));


            bais.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream in = new FileInputStream(new File(filePath + fileName));
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[512];
        while ((in.read(b)) != -1) {
            out.write(b);
        }
        out.flush();
        in.close();
        out.close();
    }
}