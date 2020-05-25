package com.xxxhub.xxxchat.server.model;

import lombok.Data;

/**
 * * HT
 * *  2020/5/24
 **/
@Data
public class MessageDTO {
    /**
     * 消息的类型
     */
    private Integer msgType;
    private Long userId;

    /**
     * 私聊中要发送的对象
     */
    private Long toUserId;
    private String nickName;
    /**
     * 传递的内容
     */
    private String content;
}
