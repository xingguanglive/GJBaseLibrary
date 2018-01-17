package tv.guojiang.baselib.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author leo
 */
public class Response {

    @SerializedName("errno")
    public int code;

    @SerializedName("msg")
    public String msg;

}
