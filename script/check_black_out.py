# 导入必要模块
import pandas as pd
from sqlalchemy import create_engine
import math

engine = create_engine('mysql+pymysql://root:xxx@localhost:3306/xxx')

sql = '''
      select * from t_center_black_out where type=2;
      '''
df = pd.read_sql_query(sql, engine)
print(len(df))

error_list = []
for index, row in df.iterrows():
    out_id = row['outId']
    if 'gy' in out_id or 'NY' in out_id:
        continue
    money = float(row['money'])
    if out_id:
        try:
            sql = "select * from t_center_black_out_detail where outId='"+str(out_id)+"'"
            # print(sql)
            df1 = pd.read_sql_query(sql, con=engine)
            if len(df1)>0:
                # print(df1)
                value = 0
                value2 = 0
                for index2, row2 in df1.iterrows():
                    value += int(row2['amount'])*float(row2['price'])
                    value2 += int(row2['amount'])*float(row2['costprice'])
                # print(money, value)
                equal = math.isclose(money, value)
                equal2 = math.isclose(money, value2)
                equal3 = math.isclose(money, value/2)
                equal4 = math.isclose(money, value2/2)
                if equal or equal2 or equal3 or equal4:
                    continue
                else:
                    print('{} money {} not equal to {}:{}'.format(out_id, money, value, value2))
                    error_list.append(out_id)
        except:
            continue

print(error_list)
result = pd.DataFrame(error_list, columns=['out_id'])
result.to_csv("error_black.xls", index=False)