package com.qcl.mongodb.mapper;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/30
 */
@Component
public class FileMapper {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 保存图片
     *
     * @param file
     * @param fileName
     * @param contentType
     * @return
     */
    public String save(InputStream file, String fileName, String contentType) {
        // 将文件存储到mongodb中,mongodb 将会返回这个文件的具体信息
        ObjectId store = gridFsTemplate.store(file, fileName, contentType);
        String id = store.toString();
        if (StringUtils.hasLength(id)) {
            return id;
        }
        return null;
    }
}
