package com.redis.lock.controller;

import com.redis.lock.service.KillItmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀程序测试
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@RestController
public class KillItemController {

    @Autowired
    private KillItmsService killItmsService;


    @RequestMapping("/init")
    public ResponseEntity<Boolean> kill() {
        killItmsService.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/kill")
    public ResponseEntity<Boolean> kill(@RequestParam("killId") Integer killId) {
        return new ResponseEntity<>(killItmsService.killItem(killId), HttpStatus.OK);
    }
}
