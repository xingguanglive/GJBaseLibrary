package tv.guojiang.baselib.network.cookie;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import java.net.CookieStore;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Cookie由旧的网络框架迁移到新的网络框架
 *
 * @author leo
 */
public class CookieAdapter implements ClearableCookieJar, ICookie {

    private ApiCookie mNewCookie;

    private CookieStore mOldCookie;

    public CookieAdapter(CookieStore oldCookie, ApiCookie newCookie) {
        mOldCookie = oldCookie;
        mNewCookie = newCookie;
    }

    @Override
    public void clearSession() {
        mNewCookie.clearSession();
    }

    @Override
    public void clear() {
        mNewCookie.clear();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mNewCookie.saveFromResponse(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return mNewCookie.loadForRequest(url);
    }

    @Override
    public Cookie getCookie(String key) {
        return mNewCookie.getCookie(key);
    }

    @Override
    public String getCookieValue(String key) {
        return mNewCookie.getCookieValue(key);
    }
}
