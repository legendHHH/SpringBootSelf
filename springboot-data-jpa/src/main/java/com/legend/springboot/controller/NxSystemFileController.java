package com.legend.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.legend.springboot.common.Result;
import com.legend.springboot.entity.NxSystemFileInfo;
import com.legend.springboot.repository.NxSystemFileInfoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@RequestMapping("/files")
public class NxSystemFileController {

    private static final String BASE_PATH = "D:/files";

    @Resource
    private NxSystemFileInfoRepository nxSystemFileInfoService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String originName = file.getOriginalFilename();

        String key = request.getParameter("key");
        System.out.println("key:" + key);

        // 文件名加个时间戳
        String fileName = FileUtil.mainName(originName) + System.currentTimeMillis() + "." + FileUtil.extName(originName);

        // 2. 文件上传
        FileUtil.writeBytes(file.getBytes(), BASE_PATH + fileName);

        // 3. 信息入库，获取文件id
        NxSystemFileInfo info = new NxSystemFileInfo();
        info.setOriginName(originName);
        info.setFileName(fileName);
        info.setKey(key);
        NxSystemFileInfo addInfo = nxSystemFileInfoService.save(info);
        System.out.println("添加文件操作：" + addInfo.toString());
        if (addInfo != null) {
            return Result.success(addInfo);
        } else {
            return Result.error("4001", "上传失败");
        }
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        if ("null".equals(id)) {
            throw new RuntimeException("您未上传文件");
        }
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.getOne(Integer.parseInt(id));
        if (nxSystemFileInfo == null) {
            throw new RuntimeException("未查询到该文件");
        }
        byte[] bytes = FileUtil.readBytes(BASE_PATH + nxSystemFileInfo.getFileName());
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(nxSystemFileInfo.getOriginName(), "UTF-8"));
        response.addHeader("Content-Length", "" + bytes.length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(bytes);
        toClient.flush();
        toClient.close();
    }

    @DeleteMapping("/{id}")
    public Result deleteFile(@PathVariable String id) {
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.getOne(Integer.parseInt(id));
        if (nxSystemFileInfo == null) {
            throw new RuntimeException("未查询到该文件");
        }
        String name = nxSystemFileInfo.getFileName();
        // 先删除文件
        FileUtil.del(new File(BASE_PATH + name));
        // 再删除表记录
        nxSystemFileInfoService.deleteById(Integer.parseInt(id));

        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<NxSystemFileInfo> getById(@PathVariable String id) {
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.getOne(Integer.parseInt(id));
        if (nxSystemFileInfo == null) {
            NxSystemFileInfo nxSystemFileInfoByKey = nxSystemFileInfoService.findNxSystemFileInfoByKey(id);
            if (nxSystemFileInfoByKey == null) {
                throw new RuntimeException("数据库未查到此文件");
            }
        }
        try {
            FileUtil.readBytes(BASE_PATH + nxSystemFileInfo.getFileName());
        } catch (Exception e) {
            throw new RuntimeException("此文件已被您删除");
        }
        return Result.success(nxSystemFileInfo);
    }
}
