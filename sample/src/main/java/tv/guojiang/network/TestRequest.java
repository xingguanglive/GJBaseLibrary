package tv.guojiang.network;

import java.util.concurrent.TimeUnit;
import tv.guojiang.core.network.annotation.Cache;
import tv.guojiang.core.network.cache.CacheState;
import tv.guojiang.core.network.request.BaseRequest;

/**
 * @author leo
 */
@Cache(maxAge = 30, timeUnit = TimeUnit.DAYS, state = CacheState.FOCUS_CACHE)
public class TestRequest extends BaseRequest {

}
