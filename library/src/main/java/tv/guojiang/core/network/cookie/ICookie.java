package tv.guojiang.core.network.cookie;

import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author leo
 */
public interface ICookie {

    /**
     * 根据key来获取Cookie
     */
    Cookie getCookie(HttpUrl url, String key);

    /**
     * 获取所有的Cookie
     */
    List<Cookie> getCookies(HttpUrl httpUrl);

    /**
     * 清除指定域名下的Cookie
     */
    void clearCookie(HttpUrl url);

    List<Cookie> getAllCookies();
}
