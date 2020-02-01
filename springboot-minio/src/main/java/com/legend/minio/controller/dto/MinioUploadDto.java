package com.legend.minio.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传返回结果
 *
 * @author legend
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MinioUploadDto {

    private String name;
    private String url;
}
