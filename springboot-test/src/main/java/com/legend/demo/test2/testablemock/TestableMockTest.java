package com.legend.demo.test2.testablemock;

import com.alibaba.testable.core.annotation.MockMethod;
import org.junit.Test;

import static com.alibaba.testable.core.matcher.InvokeVerifier.verify;
import static org.junit.Assert.assertEquals;

/**
 * TestableMockTest测试TestableMock
 * 参考文档：
 * https://github.com/alibaba/testable-mock
 * https://alibaba.github.io/testable-mock/
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/2
 */
public class TestableMockTest {

    private TestableMock testableMock = new TestableMock();

    /**
     * Mock 任意方法
     *
     * @return
     */
    @MockMethod(targetClass = String.class)
    private String trim() {
        return "http://www";
    }

    @MockMethod(targetClass = String.class, targetMethod = "substring")
    private String substr(int i) {
        return "legend.cn_";
    }

    @MockMethod(targetClass = String.class)
    private boolean startsWith(String website) {
        return false;
    }

    /**
     * Mock 成员方法
     *
     * @param text
     * @return
     */
    @MockMethod(targetClass = TestableMock.class)
    private String innerMethod(String text) {
        return "mock_" + text;
    }


    /**
     * Mock 静态方法
     *
     * @return
     */
    @MockMethod(targetClass = TestableMock.class)
    private String staticMethod() {
        return "_MOCK_LEGEND";
    }


    @Test
    public void commonMethodTest() {
        assertEquals("http://www.legend.cn_false", testableMock.commonMethod());
        System.out.println("commonMethodTest："+testableMock.commonMethod());
        verify("trim").withTimes(2);
        verify("substr").withTimes(2);
        verify("startsWith").withTimes(2);
    }


    @Test
    public void memberMethodTest() {
        assertEquals("{ \"result\": \"mock_hello_MOCK_LEGEND\"}", testableMock.memberMethod("hello"));
        verify("innerMethod").withTimes(1);
        verify("staticMethod").withTimes(1);
        verify("innerMethod").with("hello");
        verify("staticMethod").with();
    }
}
