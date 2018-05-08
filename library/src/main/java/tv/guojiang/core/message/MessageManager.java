package tv.guojiang.core.message;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * author KK
 * date 2017/8/17
 */
public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();

    private Map<String, List<MessageReceivedListener>> mListenerMap;
    volatile MessageQueen mMessageQueen;
    MessageLooper mMessageLooper;
    private ExecutorService mExecutorService;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    protected MessageManager() {
        mListenerMap = new HashMap<>();
        mMessageQueen = new MessageQueen();
        mMessageLooper = new MessageLooper();
        mExecutorService = Executors.newCachedThreadPool();
        mExecutorService.execute(mMessageLooper);
    }

    /**
     * 注册消息监听
     *
     * @param messageType 关系的消息
     */
    protected void addListener(String messageType, MessageReceivedListener ls) {
        synchronized (mListenerMap) {
            List<MessageReceivedListener> listeners = mListenerMap.get(messageType);
            if (null == listeners) {
                listeners = new CopyOnWriteArrayList<>();
                listeners.add(ls);
                mListenerMap.put(messageType, listeners);
            } else {
                if (!listeners.contains(ls)) {
                    listeners.add(ls);
                }

            }
        }
    }

    /**
     * 注销消息，关闭activity一定要注销，不然会内存泄露
     */
    protected void removeListener(String messageType, MessageReceivedListener ls) {
        synchronized (mListenerMap) {
            List<MessageReceivedListener> listeners = mListenerMap.get(messageType);
            if (null != listeners) {
                listeners.remove(ls);
            }
        }
    }

    protected Map<String, List<MessageReceivedListener>> getListener() {
        return mListenerMap;
    }

    /**
     * 收到消息后，加入消息队列
     */
    void post(MessageInfo info) {
        mMessageQueen.enqueue(MessageLink.obtainMessageLink(info));
    }

    /**
     * 发送消息给关心的监听者
     */
    synchronized void sendMsg(final MessageInfo info) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                List<MessageReceivedListener> listeners = mListenerMap.get(info.messageType);
                if (null != listeners) {
                    for (MessageReceivedListener listener : listeners) {
                        listener.onMessageReceive(info);
                    }
                }
            }
        });
    }


    /**
     * 处理消息队列的线程
     */
    class MessageLooper implements Runnable {

        @Override
        public void run() {

            while (true) {

                MessageLink link = mMessageQueen.poll(100);
                if (null == link) {
                    link = mMessageQueen.poll();
                }

                if (null != link) {
                    sendMsg(link.messageInfo);
                }
            }
        }
    }
}
