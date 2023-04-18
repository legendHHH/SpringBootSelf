package com.pipilin.web.core.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.pipilin.system.domain.vo.PayVo;
import lombok.Data;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public String app_id ="2016092500594852";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public String merchant_private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCE7yHSOfiQsiu55QlakkhlZIRS20Dr4uDYLnubL7omGCDCO/Yw4n9ot0UmtJM33uGfsDKvc06GLtbIusXcg8oxVy+8BrhnEuRi2qgU1xcnf9TqMpGJ8qsrOkQMSrq6Yv35utDwXv9ioUjW/RKU/m9QBnwvS97qYS7fU/EPSJ3JQBA5P1OhBwcOYNrKSHX9aNRSrER6v6aVZYs7Oz/4ttcgJFBuXeVxHD5qDJsQiAu9mrVP0JhNcbhmTGDtmUxmmC8kb238RG4y8H3G5ypOaPFso27Ff/radj8dE2GT3UMtchuILbTlbH+JenkOmcAHh8d6dGnZHpZdWxocssYkxUrJAgMBAAECggEAZpfzvz6UUn/fdUmJbYgmLXJlGVkzgpmHs/Az3zH3dTz700vZliLH9/YxfUyxrdXqxYaDp5rTCIYPp71VxPlFS80g/vkkyg/viUd9nw/dQ9PJa7H5VsCdT/BY4i+uavjJUDnY1RpL+T/Gan3IIIs5N0a6AvqIINU+CofLLGN7uvwEihgXgNjRibZ8M+ERw2/vQkgHrm/6byBLTvW1ac18k6rNoyXQ0PjPNRxclcAtjrwM9yXIpI6rz00n7abW0xmkjmVvPi6L0KDjnaM3wQ8ASYlf1PiiPNtTobTtN5x0+D36S7U2CkqeiFZ9p/qV6PxEHJnYNe+H3XFTyJCDWyIHAQKBgQC41N50+TTga/PwnMzJbL2HgbamXDteim5AxDq080XSjPh73T1LeAuK6+/ItyT5lTvuLwe8e37qJ0xXkJXigprC7az5JpdKKhbk9hqvy8NZVv+Zk6WSeAzlGGEdEdAu/KU08l6U2G43P8X3upsbh6fdrCDxW9fNKfvmybRWvJ7hIQKBgQC4HqeRu51HFylFDEoEwycoqq2/AXicSgRfU5zgmgjEOooPzj8ThfNuIwZi1gj3bc81P2mPD/qwftHCVnw95GmoPeVtZsRl2hwL8w4Y9Kp/L1Fvw0O2xdHFS1hwPu24etLPSL3nDpWUDAguGzrg9HUyz+Z+rjiw5BWQ9d95nhEsqQKBgQCCw6d3HnB4biexjV2dC9+zJOQyIoKmSk3iaHIYXxyA79dE2qto309bQ7zrRhdUtG4n/wH8KWsLAi6SrOo9zKTtT/XWd6UFhW2ZIzySLxjP9irzXm//hgupcWwTguzEsV24bdjYeZ7QW8AVmeHuwjTTMdpEp/OvZQqgpwd9bgkBwQKBgCnS1M+25YArk7VamEnCpteHBda4l9QwwBODY8RlWQ5yGcCDYjKmH+jpzd6PrhO+fu4ijHDx+Uk0VaOmj6xjgVi1TrNuP7i/NdXFDXO/yCcB8abInU4cW41MkzdUJB3e0JpDdNfSqUmq/gSzLMnC3gh/z2UBYGFWPkvifjQbIt2ZAoGAJMBnQu4apiiHIFPwLxUJHBmymNH6RaDqsIVu9+XJ+DACDcSIjHviHXuNF8Ci2ANvkyEIP39utccT0VWRhkPGtQth1rdJuDu5p/aTrBesq22Xw2mp6kxmSJZzUD9rbzEaAk+GtZZgD1Jl86U+o7jdb1aOuV3UsYidbUjpDzJxPDI=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public String alipay_public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuQ5o651uSNFric/JX/FejyLkjdf/77VCK1PX91WeBPinz+VaVjXhyx3eiJilUa52FiUbe/WyA3bEtbRwIxlzdX8wL/k4A1BRGMR65Y7BiGLpDTLuNgNyXpLLg5gzW9vxdD8DeOe15tOZguVnIGXM2DaAqXz3cZDrYR3UMNXxroBi77OqBtluO2HL50h00N8iL5Mhss1uYGi8mBXaaAX0VFROZd6XwbCqNx57iUu6MmStZeunVniLMq5eKV0ZnrBUu9wHQBAKL8uQ7vkNoSWi1dhuvg+fgTjwBEu/fsQPRR0mXWzahO8td5gD/FTspIlhIhA3NncTt5OCLdkiIcaOtwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    public String notify_url = "http://jnqgwk.natappfree.cc/mini/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    public String return_url = "http://localhost:7000/myorder";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    //订单超时时间
    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    public String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}