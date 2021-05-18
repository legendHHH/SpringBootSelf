## 设计模式--策略模式的使用

### 什么是策略模式
比如说某个对象的行为，在不同场景中有不同的实现方式，这样可以将这些实现方式定义成一组策略，每一个实现类对应一个策略，在不同的场景就使用不同的实现类，并且可以自由切换策略。

![](https://img2020.cnblogs.com/blog/1231979/202105/1231979-20210518211913583-1872007956.png)


策略模式需要一个策略接口，不同的策略实现不同的实现类，在具体业务环境中仅持有该策略接口，根据不同的场景使用不同的实现类即可

面向接口编程，而不是面向实现


### 策略模式的优点：
干掉繁琐的if/else、switch判断逻辑

代码优雅、可复用、可读性好；

符合开闭原则，扩展性好、便于维护；


### 策略模式的缺点：
策略如果很多的话，会造成策略类膨胀；

使用者必须清楚所有的策略类及其用途；


### 策略模式实战
#### 1、定义策略接口

```java
public interface IPayment {

    /**
     * 支付
     *
     * @param order
     * @return
     */
    PayResult pay(Order order);

}
```


订单信息类：
```java
public class Order {

    /**
     * 金额
     */
    private int amount;

    /**
     * 支付类型
     */
    private String paymentType;
}    
```


返回结果类
```java
public class PayResult {

    /**
     * 支付结果
     */
    private String result;
}
```


#### 2、定义各种策略

`定义各种支付策略，微信支付、支付宝、云闪付等支付实现类都实现这个接口。`


云闪付
```java
@Service("UnionPay")
public class UnionPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("云闪付支付成功");
    }

}
```


微信支付实现：
```java
@Service("WechatPay")
public class WechatPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("微信支付成功");
    }

}
```

>这里把所有的支付方式类都用@Service注解生成Bean放入SpringBean容器中，在使用策略模式的时候就不用new支付对象，可以直接使用Bean对象



#### 3、使用策略

```java
@RestController
public class PayController {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 支付接口
     * http://localhost:8083//pay?amount=12&paymentType=UnionPay
     *
     * @param amount
     * @param paymentType
     * @return
     */
    @GetMapping("/pay")
    public PayResult pay(@RequestParam("amount") int amount,
                         @RequestParam("paymentType") String paymentType) {
        Order order = new Order();
        order.setAmount(amount);
        order.setPaymentType(paymentType);

        // 根据支付类型获取对应的策略 bean
        IPayment payment = applicationContext.getBean(order.getPaymentType(), IPayment.class);

        System.out.println("支付方式：" + payment.toString() + "    支付类型：" + paymentType);
        // 开始支付
        PayResult payResult = payment.pay(order);

        return payResult;
    }

}
```