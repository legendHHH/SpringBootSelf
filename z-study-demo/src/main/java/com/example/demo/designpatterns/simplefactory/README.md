在Mybatis中比如SqlSessionFactory使用的是工厂模式，该工厂没有那么复杂的逻辑，是一个简单工厂模式。
简单工厂模式(Simple Factory PatteEn):又称为静态工厂方法(Static Factory Method)模式，它属于创建型模式。
在简单工厂模式中，可以根据参数的不同返回不同类的实例。简单工厂模式专门定义一个类来负责创建其他类的实例，
被创建的实例通常都具有共同的父类


例子:生产电脑
假设有一个电脑的代工生产商，它目前已经可以代工生产联想电脑了，随着业务的拓展，这个代工生产商还要生产惠普的电脑，我们就需要用一个单独的类来专门生产电脑，这就用到了简单工厂模式。下面我们来实现简单工厂模式: