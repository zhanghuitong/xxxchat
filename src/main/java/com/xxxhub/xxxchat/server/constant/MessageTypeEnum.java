package com.xxxhub.xxxchat.server.constant;

/**
 * * HT
 * *  2020/5/24
 **/
public enum MessageTypeEnum {

    /**
     * 消息的类型 0 系统信息 1:个人消息,2:群聊消息
     */

    SYSTEM_TYPE(0),

    PRIVATE_TYPE(1),


    GROUP_TYPE(2)


    ;




    private Integer type;

    MessageTypeEnum(Integer type) {
        this.type = type;
    }


    public Integer getType() {
        return type;
    }
}
