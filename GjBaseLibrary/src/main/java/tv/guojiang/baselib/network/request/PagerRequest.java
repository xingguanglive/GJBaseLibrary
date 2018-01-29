package tv.guojiang.baselib.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * 分页请求基类
 *
 * @author leo
 */
public class PagerRequest extends BaseRequest {

    @SerializedName("_page")
    public int pager;

    @SerializedName("_limit")
    public int pagerSize;
}
