import smtplib
from email.mime.text import MIMEText

#短信轰炸开启第一行代码(需要缩进代码不然会报错)
for i in range(5):

    # 构建 邮件信息
    msg = MIMEText('hello', 'plain', 'UTF-8')

    # 构建 邮件主题
    msg['Subject'] = 'Python第一天学习'

    # 收件人邮箱
    msg['to'] = '737795279@qq.com'

    # 发件人邮箱
    msg['from'] = '15607835338@163.com'

    # 实现发送邮件功能,需要得到邮箱服务器的授权
    # 获取邮箱的SMTP服务器授权码
    password = 'CQOQYPHKIQKSQYWS'

    # 对邮箱服务器地址发送请求
    smtp = smtplib.SMTP_SSL('smtp.163.com', 465)
    smtp.login(msg['from'], password)

    # 发送邮件
    smtp.sendmail(msg['from'], msg['to'], msg.as_string())

    print("-----------------发送成功-----------------")

    # 发送完毕 退出smtp
    smtp.close()
