package tv.guojiang.core.message;

/**
 * author  KK
 * date 2017/8/17
 */

public interface IMessageHelper {

    public void addMessageRecievedListener(String messageType, MessageReceivedListener ls);

    public void removeMessageRecievedListener(String messageType, MessageReceivedListener ls);

    public void addMessageRecievedListener(String[] messageTypes, MessageReceivedListener ls);

    public void removeMessageRecievedListener(String[] messageTypes, MessageReceivedListener ls);

    public void post(MessageInfo info);

    //    public static interface MessageReceivedListener {
    //        void onMessageRecieved(MessageInfo message);
    //
    //    }
}
