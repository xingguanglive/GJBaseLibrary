package tv.guojiang.core.message;

/**
 * 消息队列
 * author KK
 * date 2017/8/17
 */
public class MessageQueen {

    public MessageLink head;
    public MessageLink tail;

    public MessageLink poll() {
        MessageLink messageLink = head;
        synchronized (this) {
            if (null != head) {
                head = head.next;
                if (null == head) {
                    tail = null;
                }
            }
        }
        return messageLink;
    }

    /**
     * 延迟弹出队列
     */
    public synchronized MessageLink poll(int waitTime) {
        try {
            wait(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return poll();
    }

    /**
     * 加入队列
     */
    public void enqueue(MessageLink link) {
        synchronized (this) {
            if (null == link) {
                return;
            }
            if (null != tail) {
                tail.next = link;
                tail = link;
            } else if (null == head) {
                tail = head = link;
            }
            notifyAll();
        }

    }

}
