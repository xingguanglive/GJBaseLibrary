package tv.guojiang.core.network.exception;

/**
 * 接口网络错误的封装
 */
public class NetworkException extends Exception {

    private int code;

    /**
     * 展示给用户的提示信息.<br/>真实的报错信息会在控制台打印
     */
    private String message;

    public NetworkException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
