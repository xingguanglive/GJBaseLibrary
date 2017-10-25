package tv.guojiang.baselib.message;

/**
 * 消息对外处理类
 * author  KK
 * date 2017/8/17
 */
public class MessageHelper implements IMessageHelper {

    private MessageManager mMessageManager;

    public static IMessageHelper builder() {
        return getInstance();
    }

    @Override
    public void addMessageRecievedListener(String messageType, MessageReceivedListener ls) {
        mMessageManager.addListener(messageType, ls);
    }

    @Override
    public void removeMessageRecievedListener(String messageType, MessageReceivedListener ls) {
        mMessageManager.removeListener(messageType, ls);
    }

    @Override
    public void addMessageRecievedListener(String[] messageTypes, MessageReceivedListener ls) {
        if (null != messageTypes){
            for (String type : messageTypes){
                addMessageRecievedListener(type,ls);
            }
        }
    }

    @Override
    public void removeMessageRecievedListener(String[] messageTypes, MessageReceivedListener ls) {
        if (null != messageTypes){
            for (String type : messageTypes){
                removeMessageRecievedListener(type,ls);
            }
        }
    }

    @Override
    public void post(MessageInfo info) {
        mMessageManager.post(info);
    }

    private MessageHelper() {
        mMessageManager = new MessageManager();
    }

    private static MessageHelper mMessageHelper;

    private static MessageHelper getInstance() {
        if (null == mMessageHelper) {
            synchronized (MessageHelper.class) {
                mMessageHelper = new MessageHelper();
            }
        }
        return mMessageHelper;
    }


}
