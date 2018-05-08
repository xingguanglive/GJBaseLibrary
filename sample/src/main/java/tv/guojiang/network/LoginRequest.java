package tv.guojiang.network;

import com.google.gson.annotations.SerializedName;
import tv.guojiang.core.network.annotation.Header;
import tv.guojiang.core.network.request.BaseRequest;

/**
 * @author leo
 */
public class LoginRequest extends BaseRequest {

    @SerializedName("name")
    public String username;
    public String password;
    public boolean remember;

    public int android;

    @Header
    public String business;

    @Header("seven-leo")
    public int seven;

}
