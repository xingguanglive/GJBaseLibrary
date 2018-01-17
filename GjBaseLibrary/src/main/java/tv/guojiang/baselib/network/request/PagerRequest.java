package tv.guojiang.baselib.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * 分页请求基类
 *
 * @author leo
 */
public class PagerRequest extends BaseRequest {

    @SerializedName("page")
    public int pager;

    @SerializedName("limit")
    public int pagerSize;
}
