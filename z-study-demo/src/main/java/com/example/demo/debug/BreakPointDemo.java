package com.example.demo.debug;

/**
 * 断点调试demo
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/3
 */
public class BreakPointDemo {

    /**
     * 行断点
     */
    public static void line(){
        System.out.println("请把断点打在这一行上");
    }

    /**
     * 详细断点(源断点)
     */
    public static void detailLine(){
        //取消suspend
        System.out.println("请把按住Shift键+鼠标点在这一行上");
    }

    /**
     * 方法断点(菱形)
     * 接口跳转实现类
     */
    public static void method(){
        //方法断点
        System.out.println("开始进入：在方法的第一行停住和最后一行停住,这样可以查看方法内的各个变量变化了");
        IService service = new ServiceImpl();
        service.execute();
    }

    /**
     * 异常断点 全局捕获
     * 需要idea工具配置NPE的异常配置
     * java exception breakpoints 选择NullPointException
     */
    public static void exception(){
        //异常断点
        Object o = null;
        o.toString();
        System.out.println("this line will be never print");
    }

    /**
     * 读写监控-字段断点(是一个眼睛)
     * 在实体类的age属性上面打一个断点
     */
    public static void field(){
       Person person = new Person(12,"66666");
       //在实体类的age属性上面打一个断点
       person.setAge(5);
        System.out.println(person);
    }

    public static void main(String[] args) {
        //line();

        //detailLine();

        //method();

        exception();

        //field();
    }

}
