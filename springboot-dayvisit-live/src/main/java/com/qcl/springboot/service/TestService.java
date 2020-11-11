package com.qcl.springboot.service;

import com.qcl.springboot.entity.MyAccessLog;
import com.qcl.springboot.mapper.MyAccessLogMapper;
import com.qcl.springboot.utils.IPUtil;
import com.qcl.springboot.utils.IdWorker;
import com.qcl.springboot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/11
 */
@Service
@Transactional(rollbackFor = Exception.class)
@EnableScheduling
public class TestService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MyAccessLogMapper myAccessLogMapper;

    public void saveUserActivity(HttpServletRequest request) {
        //访问量
        String userAccount = (String) redisUtil.get("userAccount");
        if (userAccount == null) {
            redisUtil.set("userAccount", "1");
        } else {
            redisUtil.increment("userAccount", 1);
        }
        //日活量
        String ip = IPUtil.getIpAddress(request);
        redisUtil.sSet("userLiveCount", ip);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "*/5 * * * * ?")
    public void saveUserAccessLog() {
        //访问量
        String userAccount = (String) redisUtil.get("userAccount");
        int num = Integer.parseInt(userAccount);
        MyAccessLog mal = new MyAccessLog();
        IdWorker iw = new IdWorker();
        String id = String.valueOf(iw.nextId());
        mal.setId(id);
        mal.setCount(num + "");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String createDatetime = dateFormat.format(calendar.getTime());
        mal.setCreateDatetime(createDatetime);
        mal.setType("1");
        mal.setDelFlag("0");
        //保存当天访问量
        myAccessLogMapper.insert(mal);
        //将redis中的访问量初始化
        redisUtil.set("userAccount", "0");
        //日活量
        long len = redisUtil.sGetSetSize("userLiveCount");
        MyAccessLog mal1 = new MyAccessLog();
        IdWorker iw1 = new IdWorker();
        String id1 = String.valueOf(iw1.nextId());
        mal1.setId(id1);
        mal1.setCount(len + "");
        mal1.setCreateDatetime(createDatetime);
        mal1.setType("2");
        mal1.setDelFlag("0");
        //保存当天日活量
        myAccessLogMapper.insert(mal1);
        //设置缓存时间为1秒，变向清除set
        redisUtil.expire("userLiveCount", 1);
    }

}
