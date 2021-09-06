import pyshorteners as psn

# 生成短网址
url='https://www.cnblogs.com/qichunlin/'

# https://clck.ru/X9G9c
print(psn.Shortener().clckru.short(url))