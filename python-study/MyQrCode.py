import requests
from MyQR import myqr


#MyQR模块可以生成个性化二维码图片

#需要下载的url图片
url = 'http://img.jj20.com/up/allimg/1114/0H120155P2/200H1155P2-8-1200.jpg'
a = requests.get(url).content

#上下文管理器  固定语法  w  write b bytes
with open('1.jpg','wb') as f:
    f.write(a)
    #扫二维码的指向链接
    myqr.run(words='https://www.baidu.com/',
             #结合图片 将二维码图像于一张同目录的图片结合产生，产生一张黑白图片
             picture='1.jpg',
             #使所产生的图片由黑白变成白色，如果值为空或者false，就是指定制作黑白图片
             colorized=True,
             #输出的文件名
             save_name='Hello.png'
)

