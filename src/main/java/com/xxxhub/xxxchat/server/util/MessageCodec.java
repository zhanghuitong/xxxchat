package com.xxxhub.xxxchat.server.util;

import com.google.gson.Gson;
import com.xxxhub.xxxchat.server.model.MessageDTO;

/**
 * * HT
 * *  2020/5/24
 * 这里处理协议的加密解密
 **/
public class MessageCodec {

    private static  final Gson GSON = new Gson();

    /**
     * 接受到消息时,解析为文本
     * @param message
     * @return
     */
    public static MessageDTO decode(String message){
        //TODO 加密操作

        return GSON.fromJson(message,MessageDTO.class);
    }

    /**
     * 加密成传播的消息
     */
    public static String encode(MessageDTO messageDTO){
        // TODO 解密操作

        return GSON.toJson(messageDTO);
    }
}
