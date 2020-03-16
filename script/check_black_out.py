# 导入必要模块
import pandas as pd
from sqlalchemy import create_engine

# 初始化数据库连接，使用pymysql模块
# MySQL的用户：root, 密码:147369, 端口：3306,数据库：mydb
engine = create_engine('mysql+pymysql://root:lazio_2000@localhost:3306/uportal2')

sql = '''
      select * from t_center_black_out where type=2;
      '''

# read_sql_query的两个参数: sql语句， 数据库连接
df = pd.read_sql_query(sql, engine)

print(len(df))

# duplicate_bool = df.duplicated(subset=['outId', 'productId', 'amount','invoiceMoney','invoiceNum','invoiceDate','mobile'], keep='first')
# duplicate = df.loc[duplicate_bool == True]
# duplicate = df[df.duplicated(subset=['outId', 'productId', 'amount','invoiceMoney','invoiceNum','invoiceDate','mobile'], keep=False)]
# print(duplicate)
# duplicate.to_csv("duplicate.xls")

# 过滤掉正常的数据
removed = 0
for index, row in df.iterrows():
    out_id = row['outId']
    if out_id:
        try:
            sql = "select * from t_center_black_out_detail where outId='"+str(out_id)+"'"
            # print(sql)
            df1 = pd.read_sql_query(sql, con=engine)
            if len(df1)>0:
                print(df1)
                for index2, row2 in df1.iterrows():
                    value = row2[]
            # sql2 = "select * from t_center_invoiceins where id='" + str(ins_id) + "'"
            # df2 = pd.read_sql_query(sql2, con=engine)
            # if len(df1)> 0 and len(df2) >0:
            #     print(row_id, index, ins_id)
            #     removed += 1
            #     duplicate = duplicate.drop(index=index)
        except:
            continue

# for index, row in duplicate.iterrows():
#     row_id = row['id']
#     try:
#         sql = "delete from t_center_invoiceins_import where id='"+str(row_id)+"'"
#         pd.read_sql_query(sql, con=engine)
#     except:
#         continue
# print(sql)
# pd.read_sql_query(sql,con=engine)