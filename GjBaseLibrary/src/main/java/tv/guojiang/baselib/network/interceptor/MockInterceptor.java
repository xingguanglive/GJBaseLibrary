package tv.guojiang.baselib.network.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author leo
 */
public class MockInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        ResponseBody body = ResponseBody.create(MediaType.parse("json"),
            "{\"errno\":0, \"msg\":\"\", \"data\":{\"name\":\"chentao\",\"age\":24}}");

        return new Response.Builder()
            .code(200)
            .message("OK")
            .body(body)
            .request(chain.request())
            .build();
    }
}
