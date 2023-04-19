### IE浏览器下载文件中文文件名乱码问题解决

```
if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
    fileName = URLEncoder.encode(fileName, "utf-8");
    fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
} else {
    fileName = new String(fileName.getBytes(), "ISO8859-1");
}

```

### window.showmodaldialog is not a function 提示框无法打开
谷歌发布Chrome 37浏览器
Chrome 37禁用对showModalDialog的默认支持。
http://www.cfan.com.cn/2014/0827/110332.shtml

```
if(navigator.userAgent.indexOf("Chrome") >0 ){
    //url,window,
    return  window.open("login.do?"+"user=123",window,"dialogWidth=500px;dialogHeight=400px");
}
else{
    return window.showModalDialog("login.do?"+"user=123",window,"dialogWidth=500px;dialogHeight=400px");
}
```

![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230419133844481-2109863876.png)


window.open方法传值
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230419133955943-73997278.png)
