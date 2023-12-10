import random
import datetime
import pymysql

# 连接数据库，请根据实际情况修改连接参数
db = pymysql.connect(host='localhost', user='root', password='123456', database='dbpj')
cursor = db.cursor()

# 生成并插入 100,000 条数据
for i in range(100000):
    user_id = random.randint(1, 3)
    product_id = random.randint(1, 3)
    price_LB = round(random.uniform(0, 1000), 2)
    date = datetime.date(2023, random.randint(1, 12), random.randint(1, 28))

    # 构建插入语句
    sql = f"INSERT INTO `favorite` VALUES ({user_id}, {product_id}, {price_LB});"

    try:
        # 执行插入语句
        cursor.execute(sql)
        # 提交到数据库执行
        db.commit()
    except Exception as e:
        # 如果发生错误，回滚事务
        print(f"Error inserting data: {e}")
        db.rollback()

# 关闭数据库连接
db.close()
