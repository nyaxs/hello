# Java

------

## 基础特性
### 基本类型，包装类型缓存，自动装箱，拆箱，Object,String

------

### 集合，stream

------

### 多态，继承，接口，抽象类，代理
### 异常
### 泛型
### 反射
### 注解
### IO模型


## 并发编程

### 进程，线程，并发，并行，同步，异步

### 并发编程三个重要特性
  - 原子性
  - 可见性
  - 有序性

------

### 线程生命周期
![threadState.png](imgs%2FthreadState.png)

### 线程池
- 线程池：
  - 作用 
  - 创建
  - 常见参数
  - 处理流程
  - ![threadPoolFlow.png](imgs%2FthreadPoolFlow.png)
  - 饱和策略
  - 阻塞队列
  - 配置线程池大小的策略
    - CPU密集型--N+1
    - IO密集型--2N
- Executor
- Executor 框架不仅包括了线程池的管理，还提供了线程工厂、队列以及拒绝策略等，Executor 框架让并发编程变得更加简单。
  - 任务: Callable,Runnable
    - Runnable 接口不会返回结果或抛出检查异常，execute()提交任务
    - Callable Runnable 强化版，可以返回结果或抛出检查异常，submit()提交任务
  - 执行: Executor,ExecutorService,ThreadPoolExecutor,ScheduledThreadPoolExecutor
  - ![threadPool.png](imgs%2FthreadPool.png)
  - 异步结果: Future，FutureTask,CompletableFuture
    - Future.get()
- 几种常见的内置线程池
  - FixedThreadPool 被称为可重用固定线程数的线程池
  - SingleThreadExecutor 是只有一个线程的线程池。
  - CachedThreadPool 是一个会根据需要创建新线程的线程池。
  - ScheduledThreadPool 用来在给定的延迟后运行任务或者定期执行任务。

### 常见并发容器
- ConcurrentHashMap : 线程安全的 HashMap
- CopyOnWriteArrayList : 线程安全的 List，在读多写少的场合性能非常好，远远好于 Vector。
- ConcurrentLinkedQueue : 高效的并发队列，使用链表实现。可以看做一个线程安全的 LinkedList，这是一个非阻塞队列。
- BlockingQueue : 这是一个接口，JDK 内部通过链表、数组等方式实现了这个接口。表示阻塞队列，非常适合用于作为数据共享的通道。
- ConcurrentSkipListMap : 跳表的实现。这是一个 Map，使用跳表的数据结构进行快速查找。
------


### 锁
- CAS,AQS,ReentrantLock,Semaphore,CountDownLatch
  - CAS: CompareAndSwap(CompareAndSet)
  - AQS: AbstractQueueSynchronizer-->CLH队列锁
  - Semaphore: 指定数量线程共享资源
  - CountDownLatch: 指定数量线程阻塞在某处，线程间等待
  - CyclicBarrier: CountDownLatch强化版
- synchronized,volatile
- ThreadLocal
- 乐观锁，悲观锁,共享锁，独占锁，读锁，写锁
- 线程死锁描述的是这样一种情况：多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。
- 上面的例子符合产生死锁的四个必要条件：
  - 互斥条件：该资源任意一个时刻只由一个线程占用。
  - 请求与保持条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放。
  - 不剥夺条件:线程已获得的资源在未使用完之前不能被其他线程强行剥夺，只有自己使用完毕后才释放资源。
  - 循环等待条件:若干线程之间形成一种头尾相接的循环等待资源关系
- 避免死锁
  - 破坏请求与保持条件 ：一次性申请所有的资源。
  - 破坏不剥夺条件 ：占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源。
  - 破坏循环等待条件 ：靠按序申请资源来预防。按某一顺序申请资源，释放资源则反序释放。破坏循环等待条件。

------

### Java 内存模型（JMM）
- 缓存模型
  - cpu l1,l2,l3三级缓存<-->内存
  - ![cpuMM.png](imgs%2FcpuMM.png)
  - 线程 --> JVM本地内存<-->主内存
  - ![JMM.png](imgs%2FJMM.png)
  - Java内存区域（JVM运行时存储数据分区）和JMM(抽象线程和主内存关系，规定了代码->cpu指令的并发开发规范）
- 指令重排序
  - Java 源代码会经历 编译器优化重排 —> 指令并行重排 —> 内存系统重排 的过程，最终才变成操作系统可执行的指令序列。
  - 指令重排序可以保证串行语义一致，但是没有义务保证多线程间的语义也一致
  - 对于编译器，通过禁止特定类型的编译器重排序的方式来禁止重排序。对于处理器，通过插入内存屏障（Memory Barrier，或有时叫做内存栅栏，Memory Fence）的方式来禁止特定类型的处理器重排序。
  - happens-before 原则
    -  happens-before 原则的诞生是为了程序员和编译器、处理器之间的平衡。程序员追求的是易于理解和编程的强内存模型，遵守既定规则编码即可。编译器和处理器追求的是较少约束的弱内存模型，让它们尽己所能地去优化性能，让性能最大化。
    -  happens-before 原则的设计思想其实非常简单：为了对编译器和处理器的约束尽可能少，只要不改变程序的执行结果（单线程程序和正确执行的多线程程序），编译器和处理器怎么进行重排序优化都行。
    -  对于会改变程序执行结果的重排序，JMM 要求编译器和处理器必须禁止这种重排序。
    - ![happensBefore.png](imgs%2FhappensBefore.png)

------


# JVM
## JVM运行时数据区域
![JVM运行时数据区域.png](imgs/jvmMemoryZone.png)
- 堆
  - 分代
- 虚拟机栈
- 方法区
- 直接内存

### 类文件结构
### 类加载过程，类加载器，双亲委派
### JVM中对象的创建
1. 类加载检查
2. 分配内存
3. 初始化零值
4. 设置对象头
5. 执行init方法

### 对象的内存结构
- 对象头
- 实例数据
- 对齐填充
### 对象的访问定位
- 句柄
- 直接指针
### 字符串常量池
### OOM

## GC垃圾回收
### 堆结构
### 分配和回收原则
### 死亡对象判断方法
### 垃圾收集算法
### 垃圾收集器

## 重要常用参数
### 堆内存
### 垃圾收集
### 处理OOM

## JDK 监控和故障处理工具

这些命令在 JDK 安装目录下的 bin 目录下：
- jps (JVM Process Status）: 类似 UNIX 的 ps 命令。用于查看所有 Java 进程的启动类、传入参数和 Java 虚拟机参数等信息；
- jstat（JVM Statistics Monitoring Tool）: 用于收集 HotSpot 虚拟机各方面的运行数据;
- jinfo (Configuration Info for Java) : Configuration Info for Java,显示虚拟机配置信息;
- jmap (Memory Map for Java) : 生成堆转储快照;
- jhat (JVM Heap Dump Browser) : 用于分析 heapdump 文件，它会建立一个 HTTP/HTML 服务器，让用户可以在浏览器上查看分析结果;
- jstack (Stack Trace for Java) : 生成虚拟机当前时刻的线程快照，线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合
------



# Spring
### Spring 核心功能
![springframework5.png](imgs%2Fspringframework5.png)
- IoC,SpringBean
  - @Component 作用于类 ，@Bean 作用于方法
  - Spring 内置的 @Autowired 以及 JDK 内置的 @Resource 和 @Inject 都可以用于注入 Bean。
  - Bean的作用域
    - singleton : IoC 容器中只有唯一的 bean 实例。Spring 中的 bean 默认都是单例的，是对单例设计模式的应用。
    - prototype : 每次获取都会创建一个新的 bean 实例。也就是说，连续 getBean() 两次，得到的是不同的 Bean 实例。
    - web应用可用的 bean: request,session,application,websocket
    - Bean 的生命周期
      - 扫描Bean
      - 反射创建Bean实例
      - set属性，检查aware接口，依赖注入
      - BeanPostProcessor 后处理，钩子方法处理
      - 销毁
      - ![beanLife.png](imgs%2FbeanLife.png)
    - 循环依赖
- AOP
  - AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可拓展性和可维护性。
  - Spring AOP 就是基于动态代理的，如果要代理的对象，实现了某个接口，那么 Spring AOP 会使用 JDK Proxy，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy 去进行代理了，这时候 Spring AOP 会使用 Cglib 生成一个被代理对象的子类来作为代理，如下图所示：
  - ![springAOP.png](imgs%2FspringAOP.png)
  - 统一异常处理
    - @ControllerAdvice + @ExceptionHandler
      - 这种异常处理方式下，会给所有或者指定的 Controller 织入异常处理的逻辑（AOP），当 Controller 中的方法抛出异常的时候，由被@ExceptionHandler 注解修饰的方法进行处理。
- Spring 用到的设计模式
  - 工厂设计模式 : Spring 使用工厂模式通过 BeanFactory、ApplicationContext 创建 bean 对象。
  - 代理设计模式 : Spring AOP 功能的实现。
  - 单例设计模式 : Spring 中的 Bean 默认都是单例的。
  - 模板方法模式 : Spring 中 jdbcTemplate、hibernateTemplate 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
  - 包装器设计模式 : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
  - 观察者模式: Spring 事件驱动模型就是观察者模式很经典的一个应用。
  - 适配器模式 : Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配Controller。

----------------------
### Spring 事务
  - 事务是逻辑上的一组操作，要么都执行，要么都不执行。
  - 管理事务的两种方式
    - 编程式事务 ： 在代码中硬编码(不推荐使用) : 通过 TransactionTemplate或者 TransactionManager 手动管理事务，实际应用中很少使用，但是对于你理解 Spring 事务管理原理有帮助。
    - 声明式事务 ： 在 XML 配置文件中配置或者直接基于注解（推荐使用） : 实际是通过 AOP 实现（基于@Transactional 的全注解方式使用最多）
      - @Transactional(rollbackFor = Exception.class): 在 @Transactional 注解中如果不配置rollbackFor属性,那么事务只会在遇到RuntimeException的时候才会回滚，加上 rollbackFor=Exception.class,可以让事务在遇到非运行时异常时也回滚。

  - 事务原理
  - 事务失效的原因
    -  Spring AOP 自调用问题若同一类中的其他没有 @Transactional 注解的方法内部调用有 @Transactional 注解的方法，有@Transactional 注解的方法的事务会失效。这是由于Spring AOP代理的原因造成的，因为只有当 @Transactional 注解的方法在类以外被调用的时候，Spring 事务管理才生效。
    - 

  - 事务传播行为
    -  **事务传播行为是为了解决业务层方法之间互相调用的事务问题。** 当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如：方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行
    1. TransactionDefinition.PROPAGATION_REQUIRED使用的最多的一个事务传播行为，我们平时经常使用的@Transactional注解默认使用就是这个事务传播行为。如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
    2. TransactionDefinition.PROPAGATION_REQUIRES_NEW创建一个新的事务，如果当前存在事务，则把当前事务挂起。也就是说不管外部方法是否开启事务，Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
    3. TransactionDefinition.PROPAGATION_NESTED如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。
    4. TransactionDefinition.PROPAGATION_MANDATORY如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性）这个使用的很少。
    5. 若是错误的配置以下 3 种事务传播行为，事务将不会发生回滚：
       1. TransactionDefinition.PROPAGATION_SUPPORTS: 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
       2. TransactionDefinition.PROPAGATION_NOT_SUPPORTED: 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
       3. TransactionDefinition.PROPAGATION_NEVER: 以非事务方式运行，如果当前存在事务，则抛出异常。
  - 事务隔离级别
    1. TransactionDefinition.ISOLATION_DEFAULT :使用后端数据库默认的隔离级别，MySQL 默认采用的 REPEATABLE_READ 隔离级别 Oracle 默认采用的 READ_COMMITTED 隔离级别.
    2. TransactionDefinition.ISOLATION_READ_UNCOMMITTED :最低的隔离级别，使用这个隔离级别很少，因为它允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读
    3. TransactionDefinition.ISOLATION_READ_COMMITTED : 允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生
    4. TransactionDefinition.ISOLATION_REPEATABLE_READ : 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生。
    5. TransactionDefinition.ISOLATION_SERIALIZABLE : 最高的隔离级别，完全服从 ACID 的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
  - 事务的特性（ACID）
    1. 原子性（Atomicity）： 一个事务（transaction）中的所有操作，或者全部完成，或者全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。即，事务不可分割、不可约简。
    2. 一致性（Consistency）： 在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设约束、触发器、级联回滚等。
    3. 隔离性（Isolation）： 数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括未提交读（Read uncommitted）、提交读（read committed）、可重复读（repeatable read）和串行化（Serializable）。
    4. 持久性（Durability）: 事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。
  - Spring事务的核心接口
    - PlatformTransactionManager： （平台）事务管理器，Spring 事务策略的核心。
    - TransactionDefinition： 事务定义信息(事务隔离级别、传播行为、超时、只读、回滚规则)。
    - TransactionStatus： 事务运行状态。
    - PlatformTransactionManager 会根据 TransactionDefinition 的定义比如事务超时时间、隔离级别、传播行为等来进行事务管理 ，而 TransactionStatus 接口则提供了一些方法来获取事务相应的状态比如是否新事务、是否可以回滚等等


### SpringMVC 核心原理
- Spring MVC 是 Spring 中的一个很重要的模块，主要赋予 Spring 快速构建 MVC 架构的 Web 程序的能力。MVC 是模型(Model)、视图(View)、控制器(Controller)的简写，其核心思想是通过将业务逻辑、数据、显示分离来组织代码。
- ![springmvc.png](imgs%2Fspringmvc.png)
- SpringMVC 核心组件
  - DispatcherServlet ：核心的中央处理器，负责接收请求、分发，并给予客户端响应。
  - HandlerMapping ：处理器映射器，根据 uri 去匹配查找能处理的 Handler ，并会将请求涉及到的拦截器和 Handler 一起封装。
  - HandlerAdapter ：处理器适配器，根据 HandlerMapping 找到的 Handler ，适配执行对应的 Handler；
  - Handler ：请求处理器，处理实际请求的处理器。
  - ViewResolver ：视图解析器，根据 Handler 返回的逻辑视图 / 视图，解析并渲染真正的视图，并传递给 DispatcherServlet 响应客户端
- SpringMVC 工作原理
  - ![springmvcFlow.png](imgs%2FspringmvcFlow.png)

------------

## SpringBoot
### SpringBoot 启动流程
### SpringBoot 自动配置原理

# SpringCloud

# MySQL
### 三大范式
### 日志
### 框架
### 引擎
### 事务
### 隔离级别
### 锁
### 索引
### 分库分表
### SQL优化

# Redis
### 特性
### 数据结构
### 持久化
### 用途
### 过期策略
### 内存淘汰机制
### 缓存穿透、击穿、雪崩
### 缓存一致性
### 主从复制
### 集群，哨兵

# ElasticSearch
## 倒排索引

# 系统设计
### 认证授权
### JWT
### SSO单点登录
### 权限管理
### 数据脱敏
### 敏感词过滤
### 计算在线峰值
### 定时任务
### 实时消息


# 设计模式

## 创建型模式
### 单例模式

### 工厂模式
### 建造者模式

## 结构型模式

### 代理模式

## 行为型模式

## MVX模式及变种


# 网络
### TCP 三次握手四次挥手
### HTTP，HTTPS
