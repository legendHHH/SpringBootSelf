package com.qcl.mongodb.controller;

import com.qcl.mongodb.constants.Constant;
import com.qcl.mongodb.emun.ResponseCode;
import com.qcl.mongodb.file.FileResult;
import com.qcl.mongodb.response.Response;
import com.qcl.mongodb.service.IFileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/30
 */
@RestController
@RequestMapping("file/")
@Slf4j
public class FileController {

    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping(value = "upload")
    public Response<Object> upload(MultipartHttpServletRequest request) {
        log.info("进入方法>>>--upload--上传文件");
        if (null != request) {
            List<FileResult> upload = null;
            try {
                upload = fileService.upload(request);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new Response<>(Constant.EXCEPTION, e.getMessage());
            }
            log.info("退出方法>>>--upload--上传文件");
            return new Response<>(ResponseCode.SUCCESS, upload);
        }
        log.info("退出方法>>>--upload--上传文件");
        return new Response<>(Constant.FAIL, "上传文件不能为空");
    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @GetMapping(value = "download")
    public Response<Object> download(String fileId, HttpServletResponse response) {
        log.info("进入方法>>>--download--下载文件");
        if (!StringUtils.hasLength(fileId)) {
            return new Response<>(Constant.ERROR_PARAM_CODE, "文件ID不能为空");
        }
        String[] split = fileId.split("\\.");
        try {
            //下载文件
            fileService.download(split[0], response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Response<>(Constant.EXCEPTION, e.getMessage());
        }
        log.info("退出方法>>>--download--下载文件");
        return new Response<>(ResponseCode.SUCCESS);
    }


    @ApiOperation(value = "查询文件", notes = "查询文件")
    @GetMapping(value = "query")
    public Response<Object> query(String fileId) {
        log.info("进入方法>>>--query--查询文件");
        if (!StringUtils.hasLength(fileId)) {
            return new Response<>(Constant.ERROR_PARAM_CODE, "文件ID不能为空");
        }
        String[] split = fileId.split("\\.");
        try {
            //下载文件
            FileResult fileResult = fileService.query(split[0]);
            log.info("FileResult>>>--{}", fileResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Response<>(Constant.EXCEPTION, e.getMessage());
        }
        log.info("退出方法>>>--query--查询文件");
        return new Response<>(ResponseCode.SUCCESS);
    }
}
