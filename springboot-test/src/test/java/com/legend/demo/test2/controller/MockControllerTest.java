package com.legend.demo.test2.controller;

import com.legend.demo.test2.Test2Application;
import com.legend.demo.test2.service.MockServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Mock测试接口
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MockController.class, MockServiceImpl.class})
@AutoConfigureMockMvc
//@ContextConfiguration
public class MockControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void editItem() throws Exception {
        //返回视图方法测试
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/editUser").param("id", "28"))
                .andExpect(MockMvcResultMatchers.view().name("userEdit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Assert.assertNotNull(result.getModelAndView().getModel().get("item"));
    }

    @Test
    public void getUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getUser")
                .param("id", "28")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(28))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("san"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println(mvcResult);
    }
}