package tv.guojiang.core.message;

/**
 * author KK
 * date 2017/8/21
 */
public interface MessageReceivedListener {

    void onMessageReceive(MessageInfo info);

    //    @Override
    //    public boolean equals(Object obj) {
    //        return super.equals(obj);
    //    }
}
