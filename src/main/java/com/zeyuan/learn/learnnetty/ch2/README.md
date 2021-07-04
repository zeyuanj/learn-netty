# Netty 传统IO到netty
1. 监听端口 Thread -> NioEventLoop
    - Thread处理连接的读写,包含2个线程
    - 服务端accept时新建一个线程,Client自身新建一个线程
    - NioEventLoop同时起这两种类型的线程:监听客户端连接 & 处理客户端读写

2. 新连接 Socket -> SocketChannel -> Channel(一个Channel对应一个Socket)
   - 对连接的封装, 进行对数据的读写
   - processSelectedKey
   - 底层Channel封装成NioSocketChannel
    
3. 服务端接收数据: ByteBuf
   - read, write

4. 服务端业务逻辑处理: ClientHandler -> ChannelHandler 
   - ChannelHandler表示一个逻辑
   - ChannelHandler Pipeline 对应业务逻辑链
   - PipeLine加入客户端连接的处理过程, DefaultChannelPipeline
   
