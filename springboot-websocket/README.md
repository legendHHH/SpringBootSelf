WebSocket是一种在单个TCP连接上进行全双工通信的协议。WebSocket通信协议于2011年被IETF定为标准RFC 6455,并由RFC7936补充规范。
WebSocket API也被W3C定为标准。WebSocket使得客户端和服务器之间的数据交换变得更加简单,允许服务端主动向客户端推送数据。在
WebSocket API中,浏览器和服务器只需要完成一次握手,两者之间就直接可以创建持久性的连接,并进行双向数据传输。


在SpringBoot框架下,WebSocket基于注解使用的3种场景：
1、自己给自己发消息
2、自己给所有客户端发送消息(不包括自己)
3、自己给另一个客户端发送消息


其中@ServerEndpoint注解是服务端与客户端交互的关键,其值(/test/one)得与index页面中的请求路径对应。

启动服务,在浏览器请求http://localhost:8888/index.html


https://www.cnblogs.com/xuwenjin/p/12664650.html