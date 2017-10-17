package com.efeizao.massage;

/**
 * 消息队列链表
 * author KK
 * date 2017/8/18
 */
public class MessageLink {
    public MessageLink next;
    public MessageInfo messageInfo;

    private MessageLink(MessageInfo info) {
        messageInfo = info;
    }

    public static MessageLink obtainMessageLink(MessageInfo info) {
        return new MessageLink(info);
    }
}
