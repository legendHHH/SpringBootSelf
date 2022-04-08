package com.leyou.httpdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.leyou.httpdemo.service.MailService;
import com.leyou.httpdemo.utils.HexUtil;
import com.leyou.httpdemo.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/12/28
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code", 200);
        map.put("message", "服务启动成功...");
        map.put("data", "{}");
        return JSONObject.toJSONString(map);
    }

    /**
     * 发送邮件测试
     *
     * @return
     */
    @GetMapping("/sendMail")
    public Object sendMail() {
        MailVo mailVo = new MailVo();
        mailVo.setSubject("test");
        mailVo.setText("hello");
        mailService.sendMail(mailVo);
        return JSONObject.toJSONString(mailVo);
    }

    /**
     * 加密测试
     *
     * @return
     */
    @GetMapping("/genHex")
    public Object genHex(String source) {
        return JSONObject.toJSONString(HexUtil.string2HexUTF8(source));
    }

    /**
     * 加密测试
     *
     * @return
     */
    @GetMapping("/encodeHex")
    public Object encodeHex(String src) {
        return JSONObject.toJSONString(HexUtil.hexUTF82String(src));
    }

}
