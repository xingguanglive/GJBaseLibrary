package tv.guojiang.baselib.message;

/**
 * 消息实体
 * author  KK
 * date 2017/8/17
 */
public class MessageInfo {

    public String messageType;
    public String msgText;//json
    public Object object;
//    public int errno;
//    public String msg;

    public MessageInfo(String messageType, String msgText, Object object) {
        this.messageType = messageType;
        this.msgText = msgText;
        this.object = object;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
