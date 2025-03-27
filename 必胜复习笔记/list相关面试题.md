##### ArrayList

![image-20250403194905559](list相关面试题.assets/image-20250403194905559.png)

![image-20250407113345165](list相关面试题.assets/image-20250407113345165.png)

![image-20250407113717471](list相关面试题.assets/image-20250407113717471.png)



![image-20250407165740149](list相关面试题.assets/image-20250407165740149.png)

#####  源码分析

![image-20250407183935859](list相关面试题.assets/image-20250407183935859.png)

![image-20250407183858600](list相关面试题.assets/image-20250407183858600.png)

##### 实现list和数组转换

![image-20250408153306436](list相关面试题.assets/image-20250408153306436.png)

##### ArrayList 和 LinkList区别

![image-20250408162948594](list相关面试题.assets/image-20250408162948594.png)

![image-20250408163204094](list相关面试题.assets/image-20250408163204094.png)

![image-20250408163319286](list相关面试题.assets/image-20250408163319286.png)

![image-20250408163339023](list相关面试题.assets/image-20250408163339023.png)

##### 二叉树

![image-20250408164029888](list相关面试题.assets/image-20250408164029888.png)

![image-20250408164213852](list相关面试题.assets/image-20250408164213852.png)

![image-20250408164431119](list相关面试题.assets/image-20250408164431119.png)

##### 红黑树

![image-20250408164629793](list相关面试题.assets/image-20250408164629793.png)

![image-20250408164704574](list相关面试题.assets/image-20250408164704574.png)

![image-20250408164721978](list相关面试题.assets/image-20250408164721978.png)

##### 散列表（hash表）

![image-20250408164800352](list相关面试题.assets/image-20250408164800352.png)

![image-20250408165034828](list相关面试题.assets/image-20250408165034828.png)

###### 哈希冲突

拉链法，每个桶会有一个链表，链表也可以变成一个红黑树

![image-20250408171447175](list相关面试题.assets/image-20250408171447175.png)

##### hashmap原理

![image-20250408171822250](list相关面试题.assets/image-20250408171822250.png)

##### hashmap put 流程

![image-20250408172048578](list相关面试题.assets/image-20250408172048578.png)

![image-20250408173400340](list相关面试题.assets/image-20250408173400340.png)

![image-20250408173816852](list相关面试题.assets/image-20250408173816852.png)

##### hashmap 扩容机制

![image-20250408225019702](list相关面试题.assets/image-20250408225019702.png)

![image-20250408225921486](list相关面试题.assets/image-20250408225921486.png)

##### Hsahmap 寻址算法

![image-20250408230836476](list相关面试题.assets/image-20250408230836476.png)

![image-20250408231125357](list相关面试题.assets/image-20250408231125357.png)

##### hashmap 1.7 多线程死循环问题

答：主要原因是采用了头插法，在 扩容的过程中，链表数据会逆转，在多线程中 ，当T1线程执行完后，T2线程对此全然不知，此时T1 B节点下一个是A，而T2的A节点下一个是B形成了环。发生死循环后，系统不会报错，会一直没有反应，或者卡死，cup会100%。

解决方案升级到jdk1.8以上，或者加锁

1.多线程。2.触发扩容  3.jdk1.8之前 （头插法）

![image-20250408231323876](list相关面试题.assets/image-20250408231323876.png)

![image-20250408231719289](list相关面试题.assets/image-20250408231719289.png)