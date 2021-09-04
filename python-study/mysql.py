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


df = pd.read_excel("C:\\Users\\legend\\Desktop\\test.xlsx")
for idx,row in df.iterrows():
    sql= f"""\
        INSERT INTO `test`(`one`, `two`, `createTime`) VALUES ('{row.one}', '{row.two}', '{row.createTime}');
"""

    cursor = conn.cursor()
    cursor.execute(sql)
    
    conn.commit()
    
conn.close()