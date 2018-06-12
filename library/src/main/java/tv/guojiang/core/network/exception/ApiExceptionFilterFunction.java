package tv.guojiang.core.network.exception;

import io.reactivex.functions.Function;
import tv.guojiang.core.network.config.ServerCode;
import tv.guojiang.core.network.response.BaseResponse;
import tv.guojiang.core.util.JsonUtils;

/**
 * 接口业务错误提前过滤
 *
 * @author leo
 */
public class ApiExceptionFilterFunction implements Function<String, String> {

    @Override
    public String apply(String json) throws Exception {

        // {"errno":0, "msg":"", "data":{}}
        // {"errno":0, "msg":"", "data":[]}

        BaseResponse response = JsonUtils.getInstance().fromJson(json, BaseResponse.class);

        // JSONObject jsonObject = new JSONObject(json);

        // 封装业务错误
        // 避免后台给的 data 不是json-object/json-array 引发异常
        if (response.code != ServerCode.SUCCESS) {
            throw new ApiException(response.code, response.msg, response.data);
        }

        return json;
    }
}
