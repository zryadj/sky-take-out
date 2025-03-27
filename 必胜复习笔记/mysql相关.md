## 1.定位慢查询

​	1.运维工具 skywalking

​	2.MySQL慢日志查询 
​	slow_query_log=1;
​	long_query_time=2;

![image-20250321003649488](mysql相关.assets/image-20250321003649488.png)

![image-20250321140044375](mysql相关.assets/image-20250321140044375.png)

## 2.索引

索引：本质上说它就是一种数据结构，它的作用就是为了提高**查询**速度。

索引的分类：单值索引（给某一列创建索引）、复合索引（把某个表的两列或者多列组合起来创建一个索引）、唯一索引（这个列的值必须唯一）、主键索引（这个列的值必须唯一，而且不能为空）

![image-20250321154004668](mysql相关.assets/image-20250321154004668.png)

![image-20250321154421771](mysql相关.assets/image-20250321154421771.png)

![image-20250321154517701](mysql相关.assets/image-20250321154517701.png)

### 回表

![image-20250321154719532](mysql相关.assets/image-20250321154719532.png)

![image-20250321155040423](mysql相关.assets/image-20250321155040423.png)

### 覆盖索引（超大分页）

![image-20250321234327851](mysql相关.assets/image-20250321234327851.png)

![image-20250321235153334](mysql相关.assets/image-20250321235153334.png)

![image-20250321235234012](mysql相关.assets/image-20250321235234012.png)

### 创建索引的原则

![image-20250326124224240](mysql相关.assets/image-20250326124224240.png)

![image-20250326124315070](mysql相关.assets/image-20250326124315070.png)

### 索引失效

1）违反最左前缀法则![image-20250326160250425](mysql相关.assets/image-20250326160250425.png)![image-20250326160442763](mysql相关.assets/image-20250326160442763.png)![image-20250326160604723](mysql相关.assets/image-20250326160604723.png)![image-20250326160722904](mysql相关.assets/image-20250326160722904.png)![image-20250326160805111](mysql相关.assets/image-20250326160805111.png)

![image-20250326160921379](mysql相关.assets/image-20250326160921379.png)

# 3.SQL语句优化

![image-20250326161453685](mysql相关.assets/image-20250326161453685.png)

# 4.事务

![image-20250326162508916](mysql相关.assets/image-20250326162508916.png)

ACID 是数据库事务的四大特性，保证数据的可靠性和一致性：

- **A（Atomicity，原子性）**：事务要么全部成功，要么全部回滚，不可部分执行。
- **C（Consistency，一致性）**：事务执行前后，数据必须保持一致的状态。
- **I（Isolation，隔离性）**：多个事务并发执行时，互不干扰，避免脏读、幻读等问题。
- **D（Durability，持久性）**：事务一旦提交，数据会被永久保存，即使系统崩溃也不会丢失。

![image-20250326162857085](mysql相关.assets/image-20250326162857085.png)

![image-20250326163218053](mysql相关.assets/image-20250326163218053.png)

![image-20250326163359177](mysql相关.assets/image-20250326163359177.png)

![image-20250326163438160](mysql相关.assets/image-20250326163438160.png)

# 5.undolog和redolog的区别

![image-20250327185905894](mysql相关.assets/image-20250327185905894.png)