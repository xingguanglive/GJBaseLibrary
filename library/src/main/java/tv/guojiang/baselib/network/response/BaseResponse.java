package tv.guojiang.baselib.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Response基类
 *
 * @author leo
 */
public class BaseResponse<T> extends Response {

    @SerializedName("data")
    public T data;

}
