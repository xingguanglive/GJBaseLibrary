package tv.guojiang.core.network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Cookie的处理
 *
 * @author leo
 */
public class ApiCookie implements ClearableCookieJar, ICookie {

    /**
     * 有效Cookie
     */
    private PersistentCookieJar mCookieJar;

    public ApiCookie(Context context) {
        this(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }

    public ApiCookie(SharedPreferences preferences) {
        this(new SetCookieCache(), new SharedPrefsCookiePersistor(preferences));
    }

    private ApiCookie(CookieCache cookieCache, CookiePersistor cookiePersistor) {
        mCookieJar = new PersistentCookieJar(cookieCache, cookiePersistor);
    }

    /**
     * 保存Response header中的Cookie.(先保存到内存中，再将未过期的Cookie保存到本地)
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCookieJar.saveFromResponse(url, cookies);
    }

    /**
     * 加载Cookie到Request的Header中，并清除过期的Cookie
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return mCookieJar.loadForRequest(url);
    }

    /**
     * 清除内存中的Cookie，并加载本地的Cookie到内存中.
     */
    @Override
    public void clearSession() {
        mCookieJar.clearSession();
    }

    /**
     * 清除所有的Cookie.包括内存与本地的
     */
    @Override
    public void clear() {
        mCookieJar.clear();
    }

    @Override
    public Cookie getCookie(HttpUrl url, String key) {
        List<Cookie> cookies = mCookieJar.loadForRequest(url);
        if (cookies == null || cookies.size() == 0 || TextUtils.isEmpty(key)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (key.equals(cookie.name())) {
                return cookie;
            }
        }

        return null;
    }

    @Override
    public List<Cookie> getCookies(HttpUrl url) {
        return mCookieJar.loadForRequest(url);
    }

    @Override
    public void clearCookie(HttpUrl url) {
        List<Cookie> cookies = getCookies(url);
        if (cookies == null || cookies.size() == 0) {
            return;
        }

        List<Cookie> clearedCookies = new ArrayList<>();

        for (Cookie cookie : cookies) {
            Cookie clearedCookie = new Cookie.Builder()
                .domain(cookie.domain())
                .expiresAt(System.currentTimeMillis())
                .name(cookie.name())
                .value(cookie.value())
                .path(cookie.path())
                .build();

            clearedCookies.add(clearedCookie);
        }

        // 清除cookies
        mCookieJar.saveFromResponse(url, clearedCookies);
    }
}
