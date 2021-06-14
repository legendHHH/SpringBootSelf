from lxml import etree

import requests

# Python破解vip视频实战

# 请求包图网url拿到html数据
# 获取当前网站首页的响应数据
# 反爬虫
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36',

}

a = requests.get("https://ibaotu.com/", headers=headers)

print(a)

# 抽取想要的数据  视频链接  视频标题
# etree.HTML():构造一个Xpath解析对象并对HTML文本进行自动修正
html = etree.HTML(a.text)

# 视频链接  返回给我们的是一个列表所有定义一个变量为src-list
# src_list = html.xpath("video-qn.ibaotu.com/19/75/21/22e888piCdbZ.mp4_10s.mp4")
src_list = html.xpath('//div[@class="video-play"]/video/@src')

# 视频标题
title_list = html.xpath('//span[@class="video-title"]/text()')
print(src_list, title_list)

# 循环下载  zip:可以同时选拿两个，没有zip就不能同时拿两个
for src, title in zip(src_list, title_list):
    # 下载视频  返回一个完整的网址拼接上https
    b = requests.get("https:" + src, headers=headers)

    # 保存视频
    # 创建文件名
    fileName = title + '.mp4'
    print("正在下载视频：" + fileName)

    # 下载视频 保存到文件目录中  上下文管理器
    with open('../video/' + fileName, 'wb') as file:
        file.write(b.content)
        file.close()
