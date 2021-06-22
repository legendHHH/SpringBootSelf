### 对象头


![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210622210326107-949918395.png)


2.Object object = new Object()谈谈你对这句话的理解?
一般而言JDK8按照默认情况下，new一个对象占多少内存空间
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210622210508281-1562117850.png)


3.对象在堆内存中布局

在HotSpot虚拟机里，对象在堆内存中的存储布局可以划分为三个部分:对象头(Header)、实例数据（ Instance Data）和对齐填充(Padding)。

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210622210626751-805989827.png)


