package com.example.demo.apple;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * apple维修查询
 * 简单模拟请求、非做商业用途
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/2
 */
@Slf4j
@RestController
public class AppleController {

    public static Map<String, Object> method(String repairId, String serialNumber, String cookie) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("flag", false);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String redirectUrl = "https://test.apple/api/v1/supportaccount/getRepairStatus?repairId=" + repairId + "&serialNumber=" + serialNumber + "";
        HttpGet httpGet = new HttpGet(redirectUrl);
        httpGet.setHeader("Accept", "*/*");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "dssid2=97057611-5886-4c6c-9568-c3f31bdcc5dc; dssf=1; pxro=1; SA-Locale=zh_CN; POD=cn~zh; dslang=CN-ZH; acn01=UUjT/LZ34JOd8AeKzatuA5X6jXvn3TRuAA7zdXOhTJs=; sh_mysupport=00a011d03d12969b971ff9e0e0980436; mskubecookie=ae6cea006402688d1c2f51fa7d49f92f; JSESSIONID=node01eprzqmkznee6lkaueimb09fd1199916.node0; SA_SESSIONID=a8e1d6ad-ed57-4ba1-9242-c6e96dd0703b; MS_AFFINITY=awsuse1-1; geo=CN");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        //设置请求头
        httpGet.setHeader("Referer", "https://test.apple/repairs/details/" + repairId + "?serialNumber=" + serialNumber + "&cid=acs::repair.email");
        httpGet.setHeader("Sec-Fetch-Dest", "empty");
        httpGet.setHeader("Sec-Fetch-Mode", "cors");
        httpGet.setHeader("Sec-Fetch-Site", "same-origin");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.63 Safari/537.36");
        httpGet.setHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"");
        httpGet.setHeader("sec-ch-ua-mobile", "?0");
        httpGet.setHeader("sec-ch-ua-platform", "\"Windows\"");
        httpGet.setHeader("x-apple-timezone", "Asia/Shanghai");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        HttpEntity entity = httpResponse.getEntity();

        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");

            //处理json数据
            JSONObject jsonObject = JSONObject.parseObject(result);
            String data = jsonObject.getString("data");

            JSONObject jsonData = JSONObject.parseObject(data);
            String repairMetaData = jsonData.getString("repairMetaData");
            resultMap.put("repairMetaData", repairMetaData);

            //repairDetails
            JSONObject jsonRepairMetaData = JSONObject.parseObject(repairMetaData);
            String repairDetails = jsonRepairMetaData.getString("repairDetails");

            JSONArray jsonArray = JSONArray.parseArray(repairDetails);
            jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger("step")).reversed());

            resultMap.put("repairDetails", jsonArray.toJSONString());
            if (jsonArray.size() >= 3) {
                log.info("维修完成...，正在通知用户");
                resultMap.put("flag", true);

            }
            log.info("查询结果：{}", jsonArray.toJSONString());
        }
        EntityUtils.consume(entity);
        httpResponse.close();

        return resultMap;
    }
}
