package tv.guojiang.baselib.network.exception;


import io.reactivex.functions.Function;
import org.json.JSONObject;
import tv.guojiang.baselib.network.config.ServerCode;

/**
 * 接口业务错误的过滤
 *
 * @author leo
 */
public class ApiExceptionFilterFunction implements Function<String, String> {

    @Override
    public String apply(String json) throws Exception {

        // todo 测试
        if(true){
            return json;
        }

        // {"errno":0, "msg":"", "data":{}}
        // {"errno":0, "msg":"", "data":[]}

        JSONObject jsonObject = new JSONObject(json);
        int code = jsonObject.optInt("errno", ServerCode.SERVER_ERROR);

        // 封装业务错误
        // 避免后台给的 data 不是object 引发异常
        if (code != ServerCode.SUCCESS) {
            String msg = jsonObject.optString("msg", "No error message from serve!");
            throw new ApiException(code, msg);
        }
        return json;
    }
}
