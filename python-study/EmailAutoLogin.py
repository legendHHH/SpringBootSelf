import time

from selenium import webdriver

# Python实现邮件自动登陆


#  Message: 'chromedriver' executable needs to be in PATH. Please see https://sites.google.com/a/chromium.org/chromedriver/home 报错提示；；；报错原因：没有配置chrome浏览器的chromedriver
# 解决：chrome://version/ 查看版本号
# 访问http://chromedriver.storage.googleapis.com/index.html，找到自己浏览器对应版本的chromedriver.exe下载
# 复制该目录配置到Windows系统环境变量中
# 打开开始菜单->我的电脑→属性→高级系统设置→环境变量→系统变量→Path→编辑→新建，将复制的目录粘贴确定即可，注意要一路确定返回

# 简单：第二种方式可以直接修改执行路径


# 指定使用谷歌浏览器
a = webdriver.Chrome(executable_path='D:\software\Google\Chrome\Application\chromedriver.exe')

# 打开163邮箱网页
a.get('https://mail.163.com/')

# 需要找到我们想要的标签 (嵌套的标签 iframe)
iframe = a.find_element_by_tag_name('iframe')

# 切换框架  切换到iframe
a.switch_to_frame(iframe)

# 从iframe框架中查找name属性是email的值对应的就是设置值----邮箱账号
# a.find_element_by_name('email')

# 设置值
a.find_element_by_name('email').send_keys('xxxxx@163.com')
# 停顿五秒
time.sleep(5)

# 从iframe框架中查找name属性是password的值对应的就是设置值----邮箱密码
a.find_element_by_name('password').send_keys('xxxx')
time.sleep(2)

# 从iframe框架中查找id属性是dologin 进行click 点击进行登陆
a.find_element_by_id('dologin').click()
time.sleep(10)

# 关闭浏览器
a.close()
