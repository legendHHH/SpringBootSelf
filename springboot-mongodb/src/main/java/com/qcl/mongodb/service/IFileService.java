package com.qcl.mongodb.service;

import com.qcl.mongodb.file.FileResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/30
 */
public interface IFileService {

    /**
     * 上传
     *
     * @param request
     * @return
     */
    List<FileResult> upload(MultipartHttpServletRequest request);

    /**
     * 下载
     *
     * @param fileId
     * @param response
     */
    void download(String fileId, HttpServletResponse response);
}
