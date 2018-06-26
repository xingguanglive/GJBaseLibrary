package tv.guojiang.core.network.exception;

import io.reactivex.functions.Function;
import org.json.JSONObject;
import tv.guojiang.core.network.config.ServerCode;

import static tv.guojiang.core.network.exception.NetworkExceptionWrapper.SERVER_ERROR.PARSE_ERROR;

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

        // BaseResponse response = JsonUtils.getInstance().fromJson(json, BaseResponse.class);

        JSONObject jsonSource = new JSONObject(json);

        int code = jsonSource.optInt("errno", PARSE_ERROR);
        // 封装业务错误
        // 避免后台给的 data 不是json-object/json-array 引发异常
        if (code != ServerCode.SUCCESS) {

            String msg = jsonSource.optString("msg");
            boolean hasData = jsonSource.has("data");
            Object data = null;
            if (hasData) {
                JSONObject jsonObject = jsonSource.optJSONObject("data");
                data = jsonObject != null ? jsonObject : jsonSource.optJSONArray("data");
            }
            throw new ApiException(code, msg, data);
        }

        return json;
    }
}
