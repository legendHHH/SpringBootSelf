package com.legend.springboot.controller;

import com.legend.springboot.service.MailService;
import com.legend.springboot.vo.MailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 邮箱控制器
 *
 * @author legend
 */
@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 发送邮件的主界面
     */
    @GetMapping("/")
    public ModelAndView index() {
        //打开发送邮件的页面
        ModelAndView mv = new ModelAndView("sendMail");
        //邮件发信人
        mv.addObject("from", mailService.getMailSendFrom());
        return mv;
    }

    /**
     * 发送邮件
     */
    @PostMapping("/mail/send")
    public MailVo sendMail(MailVo mailVo, MultipartFile[] files) {
        mailVo.setMultipartFiles(files);
        //发送邮件和附件
        return mailService.sendMail(mailVo);
    }
}