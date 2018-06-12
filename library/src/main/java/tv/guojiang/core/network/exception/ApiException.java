package tv.guojiang.core.network.exception;

/**
 * 接口业务错误.
 */
public class ApiException extends Exception {

    private int code;

    private String message;

    private Object data;

    public ApiException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }
}
