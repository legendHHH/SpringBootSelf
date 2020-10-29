package com.qcl.mongodb.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.qcl.mongodb.file.FileResult;
import com.qcl.mongodb.mapper.FileMapper;
import com.qcl.mongodb.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/30
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private MongoResource mongoResource;

    private static final String[] FILE_TYPES =
            new String[]{
                    "jpg", "bmp", "jpeg", "png", "gif", "tif", "pcx", "tga", "exif", "exif", "svg",
                    "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "WMF", "webp"
            };

    @Override
    public List<FileResult> upload(MultipartHttpServletRequest request) {
        List<FileResult> result = new ArrayList<>();

        //拿到文件名
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            FileResult fileResult = new FileResult();
            String fileName = fileNames.next();
            //根据文件名拿到文件
            MultipartFile file = request.getFile(fileName);

            //获取上传文件的原名
            String name = file.getOriginalFilename();
            String fileNameStr = Base64.encodeBase64String(name.getBytes());
            fileResult.setFileName(name);
            String[] split = name.split("\\.");

            //拿到文件后缀名
            String suff = split[split.length - 1];
            fileResult.setFileType(suff);

            // 文件类型
            String contentType = file.getContentType();
            String save = "";

            try {
                save = fileMapper.save(file.getInputStream(), fileNameStr, contentType);
                fileResult.setId(save);
                result.add(fileResult);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }

        }
        return result;
    }


    @Override
    public void download(String fileId, HttpServletResponse response) {
        if (!StringUtils.hasLength(fileId)) {
            throw new RuntimeException("ID不能为空");
        }
        GridFSFile file = fileMapper.find(fileId);
        if (null == file) {
            throw new RuntimeException("ID对应文件不存在");
        }
        String fileName = file.getFilename();
        String[] split = fileName.split("\\.");
        try {
            Long.parseLong(split[0]);
        } catch (Exception e) {
            fileName =
                    new String(
                            Base64.decodeBase64(fileName.getBytes(StandardCharsets.UTF_8)),
                            StandardCharsets.UTF_8);
        }
        //转成GridFsResource类取文件类型
        response.setContentType(mongoResource.convertGridFSFile2Resource(file).getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //文件长度
        response.setHeader("Content-Length", String.valueOf(file.getLength()));
        try {
            OutputStream out = response.getOutputStream();
            //IO复制
            InputStream in = mongoResource.convertGridFSFile2Resource(file).getInputStream();
            IOUtils.copy(
                    mongoResource.convertGridFSFile2Resource(file).getInputStream(), out);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("文件传输完成,退出方法");
    }
}
