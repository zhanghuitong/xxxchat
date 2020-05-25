package com.xxxhub.xxxchat.server;

import com.xxxhub.xxxchat.server.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * * HT
 * *  2020/5/24
 **/
public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        int port=10086; //服务端默认端口
        // 通信地址 http://localhost:10086/im
        new WebSocketServer().bind(port);
    }

    public void bind(int port) throws Exception{
        //1用于服务端接受客户端的连接
        EventLoopGroup acceptorGroup = new NioEventLoopGroup();
        //2用于进行SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //Netty用于启动NIO服务器的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            //将两个NIO线程组传入辅助启动类中
            sb.group(acceptorGroup, workerGroup)
                    //设置创建的Channel为NioServerSocketChannel类型
                    .channel(NioServerSocketChannel.class)
                    //配置NioServerSocketChannel的TCP参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置绑定IO事件的处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
                        @Override
                        protected void initChannel(SocketChannel arg0) throws Exception {

                            ChannelPipeline pipeline = arg0.pipeline();

                            //Http请求处理的编解碼器
                            pipeline.addLast(new HttpServerCodec());
                            //用于将HTTP请求进行封装为FullHttpRequest对象
                            pipeline.addLast(new HttpObjectAggregator(1024*64));

                            //支持WebSocket协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
                            pipeline.addLast(new WebSocketHandler());
                        }
                    });
            //绑定端口，同步等待成功（sync()：同步阻塞方法，等待bind操作完成才继续）
            //ChannelFuture主要用于异步操作的通知回调
            ChannelFuture cf = sb.bind(port).sync();
            //等待服务端监听端口关闭
            cf.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程池资源
            acceptorGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
