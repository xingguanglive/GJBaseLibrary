package tv.guojiang.baselib.network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
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

    // 内存中的Cookie
    private CookieCache mCookieCache;

    // 本地的Cookie
    private CookiePersistor mCookiePersistor;

    public ApiCookie(Context context) {
        mCookieCache = new SetCookieCache();
        mCookiePersistor = new SharedPrefsCookiePersistor(context);

        mCookieJar = new PersistentCookieJar(mCookieCache, mCookiePersistor);
    }

    public ApiCookie(SharedPreferences preferences) {
        mCookieCache = new SetCookieCache();
        mCookiePersistor = new SharedPrefsCookiePersistor(preferences);

        mCookieJar = new PersistentCookieJar(mCookieCache, mCookiePersistor);
    }

    public ApiCookie(CookiePersistor cookiePersistor) {
        mCookieCache = new SetCookieCache();
        mCookiePersistor = cookiePersistor;

        mCookieJar = new PersistentCookieJar(mCookieCache, mCookiePersistor);
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
    public Cookie getCookie(String key) {

        // 从内存中获取
        for (Cookie cookie : mCookieCache) {
            if (cookie.name().equals(key)) {
                return cookie;
            }
        }

        // 从本地获取
        List<Cookie> cookies = mCookiePersistor.loadAll();
        for (Cookie cookie : cookies) {
            if (cookie.name().equals(key)) {
                return cookie;
            }
        }

        return null;
    }

    @Override
    public String getCookieValue(String key) {
        Cookie cookie = getCookie(key);
        return cookie == null ? null : cookie.value();
    }
}
