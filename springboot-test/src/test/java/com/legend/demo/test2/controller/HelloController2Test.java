package com.legend.demo.test2.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/4/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloController2Test {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        //mockMvc = MockMvcBuilders.standaloneSetup(new HelloController2()).build();
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        System.out.println(mockMvc);
    }

    /**
     * 1、mockMvc.perform执行一个请求；
     * 2、MockMvcRequestBuilders.get("/show2")构造一个请求
     * 3、ResultActions.andExpect添加执行完成后的断言
     * 4、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
     * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
     *
     * @throws Exception
     */
    @Test
    public void testView() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello2/show2"))
//                .andExpect(MockMvcResultMatchers.view().name("user/view"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
    }

    @Test
    void helloW() throws Exception{
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("test1","1");
        httpHeaders.add("test2","2");
        httpHeaders.add("test3","3");
        httpHeaders.add("test4","4");
        MvcResult result = mockMvc
                . perform(post("/company/api/pc/v1/test")  //请求的方式 请求路径
                        .contentType("application/json")  //请求的形式
                        .content("{type:1,platform:1}")	 //参数（使用的是content） 同样也可以使用.param(key,value)方法去设置
                        .headers(httpHeaders))   //设置请求头
                .andExpect(status().isOk()) //确保成功的判断
                .andReturn();//返回
        String content=result.getResponse().getContentAsString();//返回体
    }
}