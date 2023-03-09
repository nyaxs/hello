# Java

------

## 基础特性
### 基本类型，包装类型缓存，自动装箱，拆箱，Object,String

------

### 集合，stream

------

### 多态，继承，接口，抽象类，代理,SPI
- SPI(ServiceProviderInterface)
  - 如：springboot-starter,slf4j日志
    - 调用方定义接口，如slf4j定义接口规范，各家引入接口文件，提供实现
    - 实现方在resource目录下定义配置文件，描述接口实现类位置
    - 调用方去加载配置，反射-类加载-读取配置文件，读取实现，调用
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
## SpringFramework
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
  - 事务原理
    - **spring的事务是通过数据库连接来实现的。当前线程中保存了一个map，key是数据源，value是数据库连接。**
  - 管理事务的两种方式
    - 编程式事务 ： 在代码中硬编码(不推荐使用) : 通过 TransactionTemplate或者 TransactionManager 手动管理事务，实际应用中很少使用，但是对于你理解 Spring 事务管理原理有帮助。
    - 声明式事务 ： 在 XML 配置文件中配置或者直接基于注解（推荐使用） : 实际是通过 AOP 实现（基于@Transactional 的全注解方式使用最多）
      - @Transactional(rollbackFor = Exception.class): 在 @Transactional 注解中如果不配置rollbackFor属性,那么事务只会在遇到RuntimeException的时候才会回滚，加上 rollbackFor=Exception.class,可以让事务在遇到非运行时异常时也回滚。
 - 事务失效的原因
    - 事务不生效
      - 访问权限问题: spring要求必须是public
      - 方法用final修饰: 代理类无法重写该方法
      - 方法内部调用: Spring AOP 自调用问题若同一类中的其他没有 @Transactional 注解的方法内部调用有 @Transactional 注解的方法，有@Transactional 注解的方法的事务会失效。这是由于Spring AOP代理的原因造成的，因为只有当 @Transactional 注解的方法在类以外被调用的时候，Spring 事务管理才生效。
      - 未被spring管理: 不是bean
      - 多线程调用: 我们说的同一个事务，其实是指同一个数据库连接，只有拥有同一个数据库连接才能同时提交和回滚。如果在不同的线程，拿到的数据库连接肯定是不一样的，所以是不同的事务。
    - 事务不回滚
      - 错误的传播特性: 事务传播特性定义成了Propagation.NOT_SUPPORTED，这种类型的传播特性不支持事务，如果有事务则会抛异常。 目前只有这三种传播特性才会创建新事务：NESTED,REQUIRES_NEW,REQUIRED。
      - 自己吞了异常: 把异常吃了，然后又不抛出来，事务也不会回滚
      - 手动抛了别的异常: 默认回滚的是RunTimeException。如果想触发其他异常的回滚，需要在注解上配置一下，如：@Transactional(rollbackFor = Exception.class)
      - 自定义了回滚异常: 指定了回滚异常时，发生其他异常不在范围内，就不会回滚。可定义为Exception.class 或者Throwable.class
      - 嵌套事务回滚多了: 方法出现了异常，没有手动捕获，会继续往上抛，到外层方法的代理方法中捕获了异常。所以，这种情况是直接回滚了整个事务，不只回滚单个保存点。想要回滚到保存点时，可以将内部嵌套事务放在try/catch中，并且不继续往上抛异常。这样就能保证，如果内部嵌套事务中出现异常，只回滚内部事务，而不影响外部事务。
      - ![springTxFail.png](imgs%2FspringTxFail.png)
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
- SpringBoot 定义了一套接口规范，这套规范规定：SpringBoot 在启动时会扫描外部引用 jar 包中的META-INF/spring.factories文件，将文件中配置的类型信息加载到 Spring 容器，并执行类中定义的各种操作。对于外部 jar 来说，只需要按照 SpringBoot 定义的标准，就能将自己的功能装置进 SpringBoot。
- Spring Boot 通过@EnableAutoConfiguration开启自动装配，通过 SpringFactoriesLoader 最终加载META-INF/spring.factories中的自动配置类实现自动装配，自动配置类其实就是通过@Conditional按需加载的配置类，想要其生效必须引入spring-boot-starter-xxx包实现起步依赖
  - 核心注解 @SpringBootApplication
    - 可以把 @SpringBootApplication看作是 @Configuration、@EnableAutoConfiguration、@ComponentScan 注解的集合。
    - 这三个注解的作用分别是：
      - @EnableAutoConfiguration：启用 SpringBoot 的自动配置机制
      - @Configuration：允许在上下文中注册额外的 bean 或导入其他配置类
      - @ComponentScan： 扫描被@Component (@Service,@Controller)注解的 bean，注解默认会扫描启动类所在的包下所有的类 ，可以自定义不扫描某些 bean。如下图所示，容器中将排除TypeExcludeFilter和AutoConfigurationExcludeFilter。
- SpringBoot自动配置流程
  1. 判断自动装配开关是否打开。默认spring.boot.enableautoconfiguration=true，可在 application.properties 或 application.yml 中设置
  2. 获取EnableAutoConfiguration注解中的 exclude 和 excludeName。
  3. 获取需要自动装配的所有配置类，读取META-INF/spring.factories
  4. 筛选真正需要被加载的配置类，@ConditionalOnXXX 中的所有条件都满足，该类才会生效
------

## MyBatis
- MyBatis 的 xml 映射文件和 MyBatis 内部数据结构之间的映射关系？
- 答：MyBatis 将所有 xml 配置信息都封装到 All-In-One 重量级对象 Configuration 内部。 在 xml 映射文件中， 
  - < parameterMap> 标签会被解析为 ParameterMap 对象，其每个子元素会被解析为 ParameterMapping 对象。 
  - < resultMap> 标签会被解析为 ResultMap 对象，其每个子元素会被解析为 ResultMapping 对象。
  - 每一个 < select>、< insert>、< update>、< delete> 标签均会被解析为 MappedStatement 对象，标签内的 sql 会被解析为 BoundSql 对象。
- MyBatis 都有哪些 Executor 执行器？它们之间的区别是什么？
- 答：MyBatis 有三种基本的 Executor 执行器：
  - SimpleExecutor： 每执行一次 update 或 select，就开启一个 Statement 对象，用完立刻关闭 Statement 对象。
  - ReuseExecutor： 执行 update 或 select，以 sql 作为 key 查找 Statement 对象，存在就使用，不存在就创建，用完后，不关闭 Statement 对象，而是放置于 Map<String, Statement>内，供下一次使用。简言之，就是重复使用 Statement 对象。
  - BatchExecutor ：执行 update（没有 select，JDBC 批处理不支持 select），将所有 sql 都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个 Statement 对象，每个 Statement 对象都是 addBatch()完毕后，等待逐一执行 executeBatch()批处理。与 JDBC 批处理相同。
  - 作用范围：Executor 的这些特点，都严格限制在 SqlSession 生命周期范围内


# SpringCloud&分布式
## 分布式理论
### CAP理论
- Consistency一致性:  所有节点访问同一份最新的数据副本
- Availability可用性: 非故障的节点在合理的时间内返回合理的响应（不是错误或者超时的响应）
- Partition Tolerance分区容错: 分布式系统出现网络分区的时候，仍然能够对外提供服务。
- 网络分区: 分布式系统中，多个节点之前的网络本来是连通的，但是因为某些故障（比如部分节点网络出了问题）某些节点之间不连通了，整个网络就分成了几块区域，这就叫 网络分区。
- CAP理论: **当发生网络分区的时候，如果我们要继续服务，那么强一致性和可用性只能 2 选 1。** 也就是说当网络分区之后 P 是前提，决定了 P 之后才有 C 和 A 的选择。也就是说分区容错性（Partition tolerance）我们是必须要实现的。简而言之就是：CAP 理论中分区容错性 P 是一定要满足的，在此基础上，只能满足可用性 A 或者一致性 C。
- 因此，分布式系统理论上不可能选择 CA 架构，只能选择 CP 或者 AP 架构。 比如 ZooKeeper、HBase 就是 CP 架构，Cassandra、Eureka 就是 AP 架构，Nacos 不仅支持 CP 架构也支持 AP 架构。
- 为啥不可能选择 CA 架构呢？ 举个例子：若系统出现“分区”，系统中的某个节点在进行写操作。为了保证 C， 必须要禁止其他节点的读写操作，这就和 A 发生冲突了。如果为了保证 A，其他节点的读写操作正常的话，那就和 C 发生冲突了。
- 选择 CP 还是 AP 的关键在于当前的业务场景，没有定论，比如对于需要确保强一致性的场景如银行一般会选择保证 CP 。另外，需要补充说明的一点是： 如果网络分区正常的话（系统在绝大部分时候所处的状态），也就说不需要保证 P 的时候，C 和 A 能够同时保证。
### BASE理论
- BASE 是 Basically Available（基本可用） 、Soft-state（软状态） 和 Eventually Consistent（最终一致性） 三个短语的缩写。
- BASE 理论是对 CAP 中一致性 C 和可用性 A 权衡的结果，其来源于对大规模互联网系统分布式实践的总结，是基于 CAP 定理逐步演化而来的，它大大降低了我们对系统的要求。
- 核心思想: BASE 理论的核心思想即使无法做到强一致性，但每个应用都可以根据自身业务特点，采用适当的方式来使系统达到最终一致性。也就是牺牲数据的一致性来满足系统的高可用性，系统中一部分数据不可用或者不一致时，仍需要保持系统整体“主要可用”。
- BASE 理论本质上是对 CAP 的延伸和补充，更具体地说，是对 CAP 中 AP 方案的一个补充.AP 方案只是在系统发生分区的时候放弃一致性，而不是永远放弃一致性。在分区故障恢复后，系统应该达到最终一致性。这一点其实就是 BASE 理论延伸的地方。
- Basically Available: 基本可用,是指分布式系统在出现不可预知故障的时候，允许损失部分可用性。但是，这绝不等价于系统不可用。
  - 什么叫允许损失部分可用性呢？ 
    - 响应时间上的损失: 正常情况下，处理用户请求需要 0.5s 返回结果，但是由于系统出现故障，处理用户请求的时间变为 3 s。 
    - 系统功能上的损失：正常情况下，用户可以使用系统的全部功能，但是由于系统访问量突然剧增，系统的部分非核心功能无法使用。
- Soft-state: 软状态,指允许系统中的数据存在中间状态（CAP 理论中的数据不一致），并认为该中间状态的存在不会影响系统的整体可用性，即允许系统在不同节点的数据副本之间进行数据同步的过程存在延时。
- Eventually Consistent: 最终一致性强调的是系统中所有的数据副本，在经过一段时间的同步后，最终能够达到一个一致的状态。因此，最终一致性的本质是需要系统保证最终数据能够达到一致，而不需要实时保证系统数据的强一致性。
  - 一致性的三种级别
    - 强一致性 ：系统写入了什么，读出来的就是什么。
    - 弱一致性 ：不一定可以读取到最新写入的值，也不保证多少时间之后读取到的数据是最新的，只是会尽量保证某个时刻达到数据一致的状态。 
    - 最终一致性 ：弱一致性的升级版，系统会保证在一定时间内达到数据一致的状态。
    - 业界比较推崇是最终一致性级别，但是某些对数据一致要求十分严格的场景比如银行转账还是要保证强一致性。
  - 实现最终一致性的方式
    - 读时修复 : 在读取数据时，检测数据的不一致，进行修复。比如 Cassandra 的 Read Repair 实现，具体来说，在向 Cassandra 系统查询数据的时候，如果检测到不同节点的副本数据不一致，系统就自动修复数据。
    - 写时修复 : 在写入数据，检测数据的不一致时，进行修复。比如 Cassandra 的 Hinted Handoff 实现。具体来说，Cassandra 集群的节点之间远程写数据的时候，如果写失败 就将数据缓存下来，然后定时重传，修复数据的不一致性。 
    - 异步修复 : 这个是最常用的方式，通过定时对账检测副本数据的一致性，并修复。
- **ACID 是数据库事务完整性的理论，CAP 是分布式系统设计理论，BASE 是 CAP 理论中 AP 方案的延伸。**
### 分布式系统共识算法
- Paxos
  - 提议者、接受者（投票）、学习者（运算）
- Raft

### 分布式事务
- 基础理论
  - CAP&BASE
  - 一致性
  - 柔性事务
  - 刚性事务
- 分布式事务解决方案
  - 2PC(两阶段提交协议)
    - 准备阶段(Prepare)
    - 提交阶段(Commit)
  - 3PC(三阶段提交协议)
    - 准备阶段(CanCommit)
    - 预提交阶段(PreCommit)
    - 提交阶段(DoCommit)
  - TCC(补偿事务)
    - Try-Confirm-Cancel
  - MQ(消息队列事务)
  - SAGA(长事务解决方案)
    - 长事务拆分成多个本地短事务并配合补偿动作
    - T1...Tn
    - C1...Cn
------

### 消息队列
- 定义
- 使用场景
- 消息丢失
- 消息顺序性
- 重复消费
- 消息积压
- 消息队列选型
  - kafka
  - RocketMQ
  - RabbitMQ
- 高可用
- 数据一致性，事务消息
- 原理，实现

## SpringCloud&&微服务
### 服务注册与发现
- Eureka
- Nacos
- ZooKeeper
### 统一配置管理
- SpringCloud Config
- Nacos
- Apollo
- Zookeeper
### API网关
- Spring Gateway
- Zuul
- Nginx
### RPC调用
- Feign,HttpRestTemplate
- Dubbo
### 负载均衡
- Ribbon
### 熔断降级
- Hystrix
### 链路跟踪
- Spring Cloud Sleuth

### Dubbo+Zookpper




# MySQL
### 三大范式
- 第一范式(确保每列保持原子性),属性（对应于表中的字段）不能再被分割
- 第二范式(确保表中的每列都和主键相关),第二范式在第一范式的基础上增加了一个列，这个列称为主键，非主属性都依赖于主键。消除乐函数依赖。
- 第三范式(确保每列都和主键列直接相关,而不是间接相关)，消除传递函数依赖。
### 日志
- binlog(二进制日志)
  - 逻辑日志
  - 主从复制
- redo log(重做日志)
```
redo日志
1.先将原始数据从磁盘读入内存（缓冲池 Buffer pool），事务发生时，会修改内存中的拷贝，此时还未写入磁盘
2.生成redo日志，写入redo log buffer
3.当事务commit之后，以一定的频率 （innodb_flush_log_at_trx_commit也就是刷盘策略）写入redo log file（在磁盘中）
4.innodb_flush_log_at_trx_commit
设置 0 不刷盘，操作系统默认每1秒进行同步
                                   1 事务提交就刷盘（其实就是写入PageCache 立即刷盘 写入redo log file ）
                                    2 事务提交将redo log buffer写入PageCache  每秒刷新一次到磁盘
5.redo log file里有个checkpoint（之前都写入磁盘），write pos（写入位置）保证事物的持久性 
```

  - 物理日志，记录缓存中数据页的变更
  - 保证事务的持久性


- undo log(撤销日志)
  - 逻辑日志，操作记录的逆运算，补偿动作
  - 保证事务的原子性
  - 用于MVCC（多版本并发控制）,实现非锁定读取
### 框架
- ![mysqlStruct.png](imgs%2FmysqlStruct.png)
### 引擎
### 事务
### 隔离级别
### 锁
### 索引
### 分库分表
### SQL优化
- 时间优化
- 空间优化
- 设计优化

# Redis
### 特性
- 缓存
- 单线程
- IO多路复用
- 事务
  - Redis 可以通过 MULTI，EXEC，DISCARD 和 WATCH 等命令来实现事务(transaction)功能。
  ```
    1. 开始事务（MULTI）；
    2. 命令入队(批量操作 Redis 的命令，先进先出（FIFO）的顺序执行)；
    3. 执行事务(EXEC)。
  ```
- 用途
  - 缓存
  - 限流
  - 排行
  - 消息队列
  - 分布式锁
### 数据结构
- 5 种基础数据结构 ：String（字符串）、List（列表）、Set（集合）、Hash（散列）、Zset（有序集合）。
- 3 种特殊数据结构 ：HyperLogLogs（基数统计）、Bitmap （位存储）、Geospatial (地理位置)。
- Redis 共有 5 种基本数据结构：String（字符串）、List（列表）、Set（集合）、Hash（散列）、Zset（有序集合）。
- 这 5 种数据结构是直接提供给用户使用的，是数据的保存形式.
- 其底层实现主要依赖这 8 种数据结构：简单动态字符串（SDS）、LinkedList（双向链表）、Hash Table（哈希表）、SkipList（跳跃表）、Intset（整数集合）、ZipList（压缩列表）、QuickList（快速列表）。
- Redis 基本数据结构的底层数据结构实现如下：

|  String   |   List  |    Hash |  Set   |  Zset   |
|-----|-----|-----|-----|-----|
|   SDS  |  LinkedList/ZipList/QuickList   |   Hash Table、ZipList  |  ZipList、Intset   |  ZipList、SkipList   |



### 持久化
- RDB，bgsave保存快照，二进制数据
- AOF，类似binlog，记录操作记录
### 过期删除策略
- 过期字典，Redis 通过一个叫做过期字典（可以看作是 hash 表）来保存数据过期的时间。过期字典的键指向 Redis 数据库中的某个 key(键)，过期字典的值是一个 long long 类型的整数，这个整数保存了 key 所指向的数据库键的过期时间（毫秒精度的 UNIX 时间戳）。
- 定期删除
- 惰性删除，访问时检查
### 内存淘汰机制
- 按key使用频率
- 按过期时间
### 性能优化
- bigKey
  - 如果一个 key 对应的 value 所占用的内存比较大，那这个 key 就可以看作是 bigkey。
  - 具体多大才算大呢？有一个不是特别精确的参考标准：string 类型的 value 超过 10 kb，复合类型的 value 包含的元素超过 5000 个（对于复合类型的 value 来说，不一定包含的元素越多，占用的内存就越多）。
  - bigkey 有什么危害？除了会消耗更多的内存空间，bigkey 对性能也会有比较大的影响。因此，我们应该尽量避免写入 bigkey！
  - 

- 大量key集中过期的问题
### 缓存穿透、击穿、雪崩
- 缓存穿透,说简单点就是大量请求的 key 是不合理的，根本不存在于缓存中，也不存在于数据库中 。这就导致这些请求直接到了数据库上，根本没有经过缓存这一层，对数据库造成了巨大的压力，可能直接就被这么多请求弄宕机了。
- 解决办法
  - 缓存无效 key，如果缓存和数据库都查不到某个 key 的数据就写一个到 Redis 中去并设置过期时间
  - **布隆过滤器**，布隆过滤器说某个元素存在，小概率会误判。布隆过滤器说某个元素不在，那么这个元素一定不在。
  - 当一个元素加入布隆过滤器中的时候，会进行哪些操作： 
    1. 使用布隆过滤器中的哈希函数对元素值进行计算，得到哈希值（有几个哈希函数得到几个哈希值）。 
    2. 根据得到的哈希值，在位数组中把对应下标的值置为 1。
  - 当我们需要判断一个元素是否存在于布隆过滤器的时候，会进行哪些操作：
    1. 对给定元素再次进行相同的哈希计算；
    2. 得到值之后判断位数组中的每个元素是否都为 1，如果值都为 1，那么说明这个值在布隆过滤器中，如果存在一个值不为 1，说明该元素不在布隆过滤器中。
  - ![bloomFilter.png](imgs%2FbloomFilter.png)
- 缓存击穿，请求的 key 对应的是 热点数据 ，该数据 存在于数据库中，但不存在于缓存中（通常是因为缓存中的那份数据已经过期） 。这就可能会导致瞬时大量的请求直接打到了数据库上，对数据库造成了巨大的压力，可能直接就被这么多请求弄宕机了。
  - 举个例子 ：秒杀进行过程中，缓存中的某个秒杀商品的数据突然过期，这就导致瞬时大量对该商品的请求直接落到数据库上，对数据库造成了巨大的压力
- 解决办法
  1. 设置热点数据永不过期或者过期时间比较长。
  2. 针对热点数据提前预热，将其存入缓存中并设置合理的过期时间比如秒杀场景下的数据在秒杀结束之前不过期。
  3. 请求数据库写数据到缓存之前，先获取互斥锁，保证只有一个请求会落到数据库上，减少数据库的压力

- 缓存雪崩，缓存在同一时间大面积的失效，导致大量的请求都直接落到了数据库上，对数据库造成了巨大的压力。 这就好比雪崩一样，摧枯拉朽之势，数据库的压力可想而知，可能直接就被这么多请求弄宕机了。
  - 缓存服务宕机也会导致缓存雪崩现象，导致所有的请求都落到了数据库上。
- 解决办法
  - 针对 Redis 服务不可用的情况：
    1. 采用 Redis 集群，避免单机出现问题整个缓存服务都没办法使用。
    2. 限流，避免同时处理大量的请求。
  - 针对热点缓存失效的情况：
    1. 设置不同的失效时间比如随机设置缓存的失效时间。
    2. 缓存永不失效（不太推荐，实用性太差）。
    3. 设置二级缓存

    
### 3种常用的缓存读写策略详解
- Cache Aside Pattern（旁路缓存模式）
  - Cache Aside Pattern 是我们平时使用比较多的一个缓存读写模式，比较适合读请求比较多的场景。
  - Cache Aside Pattern 中服务端需要同时维系 db 和 cache，并且是以 db 的结果为准。
  - 下面我们来看一下这个策略模式下的缓存读写步骤。
    - 写：先更新 db然后直接删除 cache 。
    - 读：
      - 从 cache 中读取数据，读取到就直接返回
      - cache 中读取不到的话，就从 db 中读取数据返回再把数据放到 cache 中。
  - “在写数据的过程中，可以先删除 cache ，后更新 db 么？”
  - 答案： 那肯定是不行的！因为这样可能会造成 数据库（db）和缓存（Cache）数据不一致的问题。
    > 如：请求 1 先把 cache 中的 A 数据删除 -> 请求 2 从 db 中读取数据->请求 1 再把 db 中的 A 数据更新
  - “在写数据的过程中，先更新 db，后删除 cache 就没有问题了么？”
  - 答案： 理论上来说还是可能会出现数据不一致性的问题，不过概率非常小，因为缓存的写入速度是比数据库的写入速度快很多。
- Cache Aside Pattern 的缺陷。
  - 缺陷 1：首次请求数据一定不在 cache 的问题解决办法：可以将热点数据可以提前放入 cache 中。
  - 缺陷 2：写操作比较频繁的话导致 cache 中的数据会被频繁被删除，这样会影响缓存命中率 。
  - 解决办法：
  - 数据库和缓存数据强一致场景 ：更新 db 的时候同样更新 cache，不过我们需要加一个锁/分布式锁来保证更新 cache 的时候不存在线程安全问题。
  - 可以短暂地允许数据库和缓存数据不一致的场景 ：更新 db 的时候同样更新 cache，但是给缓存加一个比较短的过期时间，这样的话就可以保证即使数据不一致的话影响也比较小。

- Read/Write Through Pattern（读写穿透）
  - Read/Write Through Pattern 中服务端把 cache 视为主要数据存储，从中读取数据并将数据写入其中。cache 服务负责将此数据读取和写入 db，从而减轻了应用程序的职责。
  - Read-Through Pattern 实际只是在 Cache-Aside Pattern 之上进行了封装。在 Cache-Aside Pattern 下，发生读请求的时候，如果 cache 中不存在对应的数据，是由客户端自己负责把数据写入 cache，而 Read Through Pattern 则是 cache 服务自己来写入缓存的，这对客户端是透明的。
    - 写（Write Through）：、
    - 先查 cache，cache 中不存在，直接更新 db。
    - cache 中存在，则先更新 cache，然后 cache 服务自己更新 db（同步更新 cache 和 db）。
    - 读(Read Through)：
    - 从 cache 中读取数据，读取到就直接返回 。
    - 读取不到的话，先从 db 加载，写入到 cache 后返回响应。
- Write Behind Pattern（异步缓存写入）
  - Write Behind Pattern 和 Read/Write Through Pattern 很相似，两者都是由 cache 服务来负责 cache 和 db 的读写。
  - 但是，两个又有很大的不同：Read/Write Through 是同步更新 cache 和 db，而 Write Behind 则是只更新缓存，不直接更新 db，而是改为异步批量的方式来更新 db。
  - 很明显，这种方式对数据一致性带来了更大的挑战，比如 cache 数据可能还没异步更新 db 的话，cache 服务可能就就挂掉了。
  - 这种策略在我们平时开发过程中也非常非常少见，但是不代表它的应用场景少，比如消息队列中消息的异步写入磁盘、MySQL 的 Innodb Buffer Pool 机制都用到了这种策略。
  - Write Behind Pattern 下 db 的写性能非常高，非常适合一些数据经常变化又对数据一致性要求没那么高的场景，比如浏览量、点赞量。


### 缓存一致性
- 如何保证缓存和数据库数据的一致性？
    - Cache Aside Pattern（旁路缓存模式）
    - Cache Aside Pattern 中遇到写请求是这样的：更新 DB，然后直接删除 cache 。如果更新数据库成功，而删除缓存这一步失败的情况的话，简单说两个解决方案：缓存失效时间变短（不推荐，治标不治本） ：我们让缓存数据的过期时间变短，这样的话缓存就会从数据库中加载数据。另外，这种解决办法对于先操作缓存后操作数据库的场景不适用。增加 cache 更新重试机制（常用）： 如果 cache 服务当前不可用导致缓存删除失败的话，我们就隔一段时间进行重试，重试次数可以自己定。如果多次重试还是失败的话，我们可以把当前更新失败的 key 存入队列中，等缓存服务可用之后，再将缓存中对应的 key 删除即可。
### 主从复制
### 集群，哨兵


## Netty
### NIO
### 线程模型


## 分布式定时任务

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
### 定时任务&&批处理
#### XXL-JOB
- 使用方法
  - 引入依赖，添加配置{调度中心地址、执行器信息、日志信息、token}
  - 读取配置，创建xxl-executor 执行器，创建了 Spring 容器下的 XXL-JOB 执行器 Bean 对象。要注意，方法上添加了的 @Bean 注解，配置了启动和销毁方法。
  - 继承 XXL-JOB IJobHandler 抽象类，实现定时任务的逻辑。实现 #execute(String param) 方法。
    在方法上，添加 @JobHandler 注解，设置 JobHandler 的名字。后续，我们在调度中心的控制台中，新增任务时，需要使用到这个名字。

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
