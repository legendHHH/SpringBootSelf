package com.legend.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.legend.springboot.utils.VideoUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @RequestMapping(value = "/video")
    public ResultModel test() {
        ResultModel resultModel = new ResultModel();
        resultModel.setMsg("成功");
        try {
            resultModel.setCode(JsonConfigModel.SUSSESS);
            JSONObject obj = new JSONObject();
            obj.put("url", VideoUtils.getVideoUrlForFengGou("", ""));
            resultModel.setObj(obj);
        } catch (Exception e) {
            resultModel.setCode(JsonConfigModel.ERROR_VIDEO);
        }
        return resultModel;
    }
}