import mysql.connector
from faker import Faker
from datetime import datetime, timedelta
import random

# 创建 Faker 实例
fake = Faker()

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '123456',
    'database': 'dbpj',
}
def generate_sql_timestamp():
    # 生成一个随机的 datetime 对象
    random_datetime = fake.date_time_this_decade()

    # 将 datetime 对象格式化为 SQL TIMESTAMP 字符串
    sql_timestamp = random_datetime.strftime("%Y-%m-%d %H:%M:%S")

    return sql_timestamp

# <--------user-------->
def insert_users(n):
    # 建立数据库连接
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()

    # 插入100条数据
    while(n>0):
        #限制user_name的长度
        name = fake.user_name()
        if len(name) > 10:
            name = name[:10]
        password = fake.password()
        age = fake.random_int(min=18, max=80)
        gender = fake.random_element(elements=('Male', 'Female'))
        tel = fake.random_int(min=100000000, max=999999999)

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `user` (`name`, `password`, `age`, `gender`, `tel`) VALUES (%s, %s, %s, %s, %s)"
        values = (name, password, age, gender, tel)
        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("user insert error!")

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()


#<--------platform-------->
def insert_platforms(n):
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()

    # 插入10条数据
    while(n>0):
        platform_name = fake.company()

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `platform` (`name`) VALUES (%s)"
        values = (platform_name,)
        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("Platform name already exists!")

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()


#<--------merchant-------->
def insert_merchants(n):
    conn = mysql.connector.connect(**db_config)
    # 创建游标
    cursor = conn.cursor()

    # 插入1000条数据
    while(n>0):
        merchant_name = fake.company()
        if len(merchant_name) > 32:
            merchant_name = merchant_name[:32]
        password = fake.password()
        address = fake.address()

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `merchant` (`name`, `password`, `addr`) VALUES (%s, %s, %s)"
        values = (merchant_name, password, address)
        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("Merchant name already exists!")
        

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()

#<--------product-------->
def insert_products(n):
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()
    merchant_ids = []
    platform_ids = []
    prod_names = []
    #从数据库中读取merchant_id和platform_id
    cursor.execute("SELECT merchant_id FROM merchant")
    for row in cursor.fetchall():
        merchant_ids.append(row[0])
    cursor.execute("SELECT platform_id FROM platform")
    for row in cursor.fetchall():
        platform_ids.append(row[0])
    while(1):
        prod_name = fake.word()
        if prod_name not in prod_names:
            prod_names.append(prod_name)
        if len(prod_names) == 50:
            break
    getCat = {}
    cats = ['电子产品', '服装', '食品', '日用品', '家具', '化妆品', '玩具', '书籍', '运动器材', '其他']
    for name in prod_names:
        getCat[name] = random.choice(cats)

    # 插入1000条数据
    while(n>0):
        # print(f'n={n}')
        product_name = fake.random_element(elements=prod_names)
        category = getCat[product_name]
        prod_region = fake.city()
        prod_date = fake.date_between(start_date='-1y', end_date='today')
        price = round(fake.random.uniform(10, 500), 2)
        merchant_id = fake.random_element(elements=merchant_ids)
        platform_id = fake.random_element(elements=platform_ids)
        prod_desc = fake.sentence(nb_words=10)
        last_modify_date = fake.date_between(start_date='-1y', end_date='today')

        # 构建插入数据的 SQL 语句
        sql = """
        INSERT INTO `product` 
        (`name`, `category`, `prod_region`, `prod_date`, `price`, `merchant_id`, `platform_id`, `prod_desc`, `last_modify_date`) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        values = (product_name, category, prod_region, prod_date, price, merchant_id, platform_id, prod_desc, last_modify_date)
        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError as err:
            n+=1
            # print(err.msg)

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()

#<---------price_history-------->
def insert_price_history(n):
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()
    product_ids = []
    #从数据库中读取product_id
    cursor.execute("SELECT product_id FROM product")
    for row in cursor.fetchall():
        product_ids.append(row[0])

    # 插入100,000条数据
    while(n>0):
        # print(f'n={n}')
        product_id = fake.random_element(elements=product_ids)
        date = fake.date_between(start_date='-1y', end_date='today')
        price = round(fake.random.uniform(10, 500), 2)

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `price_history` (`product_id`, `date`, `price`) VALUES (%s, %s, %s)"
        values = (product_id, date, price)
        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("price_history insert error!")

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()

#<--------msg_list---------->
def insert_msg_list(n):
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()
    user_ids = []
    #从数据库中读取product_id
    cursor.execute("SELECT ID FROM user")
    for row in cursor.fetchall():
        user_ids.append(row[0])

    # 插入100,000条数据
    while(n>0):
        # print(f'n={n}')
        product_id = fake.random_element(elements=user_ids)
        time = generate_sql_timestamp()
        content = "您收藏的商品降价了！"
        # print(f"product_id={product_id}, time={time}, content={content}")

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `msg_list` (`user_id`, `time`, `content`) VALUES (%s, %s, %s)"
        values = (product_id, time, content)

        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("price_history insert error!")

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()
#<-------favorite------->
def insert_favorite(n):
    conn = mysql.connector.connect(**db_config)

    # 创建游标
    cursor = conn.cursor()
    user_ids = []
    product_ids = []
    #从数据库中读取product_id
    cursor.execute("SELECT ID FROM user")
    for row in cursor.fetchall():
        user_ids.append(row[0])
    cursor.execute("SELECT product_id FROM product")
    for row in cursor.fetchall():
        product_ids.append(row[0])

    # 插入100,000条数据
    while(n>0):
        # print(f'n={n}')
        user_id = fake.random_element(elements=user_ids)
        product_id = fake.random_element(elements=product_ids)
        # print(f"user_id={user_id}, product_id={product_id}, time={time}")

        # 构建插入数据的 SQL 语句
        sql = "INSERT INTO `favorite` (`user_id`, `product_id`) VALUES (%s, %s)"
        values = (user_id, product_id)

        n-=1
        # 执行插入操作
        try:
            cursor.execute(sql, values)
        except mysql.connector.errors.IntegrityError:
            n+=1
            # print("price_history insert error!")

    # 提交事务
    conn.commit()

    # 关闭游标和连接
    cursor.close()
    conn.close()

if __name__ == '__main__':
    # insert_users(1000)
    # insert_platforms(10)
    # insert_merchants(100)
    # insert_products(1000)
    # insert_price_history(100000)
    # insert_msg_list(10000)
    insert_favorite(100000)