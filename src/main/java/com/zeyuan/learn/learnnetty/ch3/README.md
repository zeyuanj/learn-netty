

## 创建与初始化


bind()
- initAndRegister() 初始化并注册
    - newChannel() 创建服务端channel
    - init() 初始化Channel



1. 服务端socket在哪里初始化?  ----- 即服务端创建channel
   - 也就是什么时候调用JDK的socket?
   - 首相在bootstrap.bind()的时候调用initAndRegister, 反射调用NioSocketChannel构造方法
   - NioSocketChannel构造方法
      - newSocket()通过jdk创建底层jdk channel
      - NioServerSocketChannelConfig() 负责channel中tcp参数配置
      - AbstractNioChannel() 
         - configureBlocking(false) 非 阻塞模式
         - AbstractChannel() 创建id, unsafe, pipeline
    
2. 在哪里accept连接   --- 即服务端初始化Channel
    - init()
        - set ChannelOptions, ChannelAttrs 一般不用
        - set ChildOptions, ChildAttrs 新的连接到来,配置用户自定义属性
        - config handler 配置服务端pipeline
        - add ServerBootstrapAcceptor 特殊处理器,为新连接分配一个Nio线程,并做一些配置
    
## 注册Selector
- 位置: initAndRegister -> register
- 过程:
    - AbstractChannel.register(channel) 入口
        - this.eventLoop = eventLoop 绑定线程 
        - register0()
            - doRegister() 调用jdk底层注册,注册到一个selector上 AbstractNioChannel
              (把当前的channel作为attachment绑定到selector上面去)
            - invokeHandlerAddedIfNeeded() 事件回调,添加ChannelHandler的时候会触发回调,用户代码里的handler(ServerHandler),对应handlerAdded
            - fireChannelRegistered() 触发回调成功事件, 对应channelRegistered 
- handlerAdded->channelRegistered->channelActive


## 端口绑定
- 过程:
    - AbstractUnsafe.bind() 入口
        - doBind0()
            - javaChannel().bind() jdk底层绑定
        - pipeline.fireChannelActive() 传播事件
            HeadContext.readIfIsAutoRead()  select事件绑定成accept事件
- 重要:
    
    - 1. 端口完成绑定会触发一个active事件
    - 2. active事件最终会触发调用channel的read事件
    
## 总结:
newChannel -> init -> register -> doBind

    