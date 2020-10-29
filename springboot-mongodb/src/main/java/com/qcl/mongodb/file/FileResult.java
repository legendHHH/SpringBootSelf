package com.qcl.mongodb.file;

import lombok.Data;

/**
 * 文件上传的返回值
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/28
 */
@Data
public class FileResult {
    private String fileName;
    private String id;
    private String fileType;
}