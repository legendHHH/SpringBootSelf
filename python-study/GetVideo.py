import re
import requests

# Python爬取最新电影票房

# 确定url地址，进行请求发送
from bs4 import BeautifulSoup

html = requests.get('http://58921.com/alltime').content.decode('utf-8')
# print(html)


# 写正则表达式去拿到所有的电影名字(.*? 是原生字符串  .*?匹配任意字符)
# <tr class="odd"><td>1</td><td>1</td><td><a href="/film/5331" title="战狼2">战狼2</a></td><td><img src="http://img.58921.com/sites/all/movie/files/protec/96b75ed6cbf1493f6cdf8bf77cbeba34.png" alt="" /></td><td>--</td><td>--</td><td>2017</td><td><a href="/boxoffice/history/5331" title="数据纠错">数据纠错</a></td> </tr>
# req = r'<td><a href="/film/5331" title="战狼2">战狼2</a></td>'
req = r'<td><a href="/film/.*?" title="(.*?)">.*?</a></td>'

# 根据表达式去查找所有符合规律的数据
title = re.findall(req, html)
print(title)

# 源码解析
soup = BeautifulSoup(html, 'html.parser')

# 获取总票房图片  img_url = soup.find_all('img')
# 切片删除列表中的元素  把第一个元素切掉
img_url = soup.find_all('img')[1:]

print(img_url)

# 设置初始值
i = 0
# 循环遍历出img标签中的src for in
for url in img_url:
    img_scr = url.get('src')
    print(img_url)

    # 获取内容  下载文件  二进制文件  content(图片、视频音频)
    img_content = requests.get(img_scr).content

    # 前面加个f表示格式化输出
    print(f'正在爬取：{title[i]} ,地址：{img_scr}', end='')

    # 上下文管理
    with open('../result/{}.png'.format(title[i]), 'wb') as file:
        # 保存到文件目录中
        file.write(img_content)
    i += 1
