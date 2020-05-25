package com.xxxhub.xxxchat.server.handler;

import com.xxxhub.xxxchat.server.config.ServerChannelConfig;
import com.xxxhub.xxxchat.server.processor.MessageProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

/**
 * * HT
 * *  2020/5/24
 * websocket协议的处理器
 **/
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final MessageProcessor messageProcessor = new MessageProcessor();

    private AttributeKey<String> userId = AttributeKey.valueOf("userId");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // TODO 看看channel能不能设置属性,直接从channel获取userId
        ServerChannelConfig.users.add(ctx.channel());

        messageProcessor.messageHandler(ctx.channel(),msg.text());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 下线处理
        //TODO userChannelMap 无法处理下线
        ServerChannelConfig.users.remove(ctx.channel());

    }
}
