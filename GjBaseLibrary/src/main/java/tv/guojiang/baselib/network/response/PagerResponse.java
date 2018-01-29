package tv.guojiang.baselib.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * 分页Response 基类
 *
 * @author leo
 */
public class PagerResponse<T> extends Response {

    @SerializedName("data")
    public List<T> data;

    /**
     * 当前页数据是否为空
     */
    public boolean isEmpty() {
        return data != null && data.isEmpty();
    }
}
