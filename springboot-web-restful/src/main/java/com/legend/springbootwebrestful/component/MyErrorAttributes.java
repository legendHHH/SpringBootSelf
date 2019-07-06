package com.legend.springbootwebrestful.component;


import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 给容器中加入我们自定义的的ErrorAttributes
 *  将我们定制的数据携带出去
 *
 *  最终效果：响应是自适应的,可以通过定制ErrorAttributes改变需要返回的内容
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company","legend");


        //我们的异常处理器携带的数据
        Map<String, Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext",0);
        map.put("ext",ext);
        return map;
    }
}
