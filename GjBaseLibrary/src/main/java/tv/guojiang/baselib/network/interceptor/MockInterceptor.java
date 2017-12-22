package tv.guojiang.baselib.network.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 模拟网络的{@link Interceptor}
 *
 * @author leo
 */
public class MockInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String json = "{\"errno\":0, \"msg\":\"\", \"data\":{\"name\":\"snow\",\"age\":24}}";
        String pagerJson = "{\"errno\":0, \"msg\":\"\", \"data\":[{\"name\":\"snow\",\"age\":24}]}";

        ResponseBody body = ResponseBody.create(MediaType.parse("json"), pagerJson);

        return new Response.Builder()
            .code(200)
            .message("OK")
            .protocol(Protocol.HTTP_1_1)
            .body(body)
            .request(chain.request())
            .build();
    }
}
