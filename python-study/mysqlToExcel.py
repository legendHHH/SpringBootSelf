import pandas as pd
import pymysql

# 创建链接
conn = pymysql.connect(
    host='localhost',
    user='root',
    password='123456',
    port=3306,
    db='test',
    charset='utf8'
)

# 查询mysql导出到excel
df = pd.read_sql(""""
    select * from test
""", con=conn)
df.to_excel("测试.xlsx", index=False)


df = pd.read_sql(""""
    select * from test
""", con=conn).pivot(index="one", columns='two')
df.to_excel("测试统计.xlsx")
