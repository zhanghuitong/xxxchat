package com.xxxhub.xxxchat.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerDemo {



    public static void bind(Integer port) throws InterruptedException {

        // 用于服务端接受客户端连接
        EventLoopGroup parentGroup = new NioEventLoopGroup();

        // 用于进行socket channel的网络读写
        EventLoopGroup workGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    // 用户处理网络I/O时间,如记录日志,对消息进行编解码等
                    .childHandler(new ChildChannelHandler());

            // 绑定端口,同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();

            // 等待服务端端口关闭
            future.channel().closeFuture().sync();
        }finally {
            parentGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    /**
     * serverSocket的示例
     */
    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {

            socketChannel.pipeline().addLast(new SimpleChannelInboundHandler() {

                @Override
                protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object command) throws Exception {
                    // 读取指令
                    ByteBuf byteBuf = (ByteBuf) command;

                    byte[] request = new byte[byteBuf.readableBytes()];
                    byteBuf.readBytes(request);

                    // 服务端打印内容
                    System.out.println("server has receive a command"+new String(request,"UTF-8"));

                    // 应答
                    String result = "服务端已收到";
                    ByteBuf response = Unpooled.copiedBuffer(result.getBytes());
                    // 全部返回给客户端
                    channelHandlerContext.writeAndFlush(response);
                }
            });

        }
    }



    public static void main(String[] args) throws InterruptedException {
            ServerDemo.bind(10086);
    }
}
