import requests
from pyquery import PyQuery


# Python王者荣耀皮肤批量抓取
# 页面地址https://pvp.qq.com/web201605/herolist.shtml

# 确定url地址，进行请求发送
html = requests.get('https://pvp.qq.com/web201605/herolist.shtml').content
print(html)

# 加载html代码
doc = PyQuery(html)

# css样式表
# id----#号
# class----.号
# >表示下一个标签  .items表示同类标签
items = doc('.herolist > li').items()
print(items)

# 生成器对象，迭代
for item in items:
    print(item)
    # 在列表签中继续向下查找，查找img标签。attr追加属性src
    url = item.find('img').attr('src')

    # 需要自动拼接http不然会报错
    urls = 'http:' + url
    name = item.find('a').text()
    print(url, name)

    url_content = requests.get(urls).content

    # 上下文管理器进行下载
    with open('../wangzhe/' + name + '.jpg', 'wb')as file:
        try:
            # 保存到文件目录中去
            file.write(url_content)

            # 格式化输出
            print('正在下载：%s----------------%s' % (name, urls))
            file.close()
        except Exception as e:
            print('保存失败')

print("下载完毕。。。。")
