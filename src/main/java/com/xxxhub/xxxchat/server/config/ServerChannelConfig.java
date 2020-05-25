package com.xxxhub.xxxchat.server.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * * HT
 * *  2020/5/24
 **/
public class ServerChannelConfig {

    // 用于记录,管理所有在线的客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 存放每个user对应的channel,用于一对一发送消息
    public static Map<Long, Channel> userChannelMap = new ConcurrentHashMap<>(64);



}
