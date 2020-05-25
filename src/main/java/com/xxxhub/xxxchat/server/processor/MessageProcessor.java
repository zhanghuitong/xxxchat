package com.xxxhub.xxxchat.server.processor;

import com.xxxhub.xxxchat.server.config.ServerChannelConfig;
import com.xxxhub.xxxchat.server.constant.MessageTypeEnum;
import com.xxxhub.xxxchat.server.model.MessageDTO;
import com.xxxhub.xxxchat.server.util.MessageCodec;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * * HT
 * *  2020/5/24
 * 消息的处理
 **/
@Slf4j
public class MessageProcessor {

    private ArrayBlockingQueue<MessageDTO> blockingQueue = new ArrayBlockingQueue<>(30,false);
    /**
     * 处理消息的逻辑
     */
    public void messageHandler(Channel client,String message){
        // 定义消息协议

        log.debug("客户端信息:"+message);
        // 定义消息类型 加入群聊消息,用户的消息

        // 消息的解密
        MessageDTO messageDTO = MessageCodec.decode(message);
        ServerChannelConfig.userChannelMap.putIfAbsent(messageDTO.getUserId(),client);

        if(MessageTypeEnum.GROUP_TYPE.getType().equals(messageDTO.getMsgType())){
            handleGroupChat(messageDTO);
        }

        if(MessageTypeEnum.PRIVATE_TYPE.getType().equals(messageDTO.getMsgType())){
            handlePrivateChat(messageDTO);
        }

    }

    /**
     * 处理群聊
     */
    private void handleGroupChat(MessageDTO messageDTO){
        // 向群聊中的所有用户发请求
        for(Channel channel: ServerChannelConfig.users){
            // TODO 群聊中不在线的将消息持久化,上线后推送消息
            channel.writeAndFlush(MessageCodec.encode(messageDTO));
        }
    }

    /**
     * 处理私聊
     */
    private void handlePrivateChat(MessageDTO messageDTO){
        if(messageDTO.getToUserId()==null){
            log.error("私聊错误,没有私聊的对象");
            return;
        }
        Channel channel = ServerChannelConfig.userChannelMap.get(messageDTO.getUserId());

        if(channel==null){
            // TODO 消息的持久化处理
            blockingQueue.add(messageDTO);
            return;
        }

        channel.writeAndFlush(MessageCodec.encode(messageDTO));
    }





}
