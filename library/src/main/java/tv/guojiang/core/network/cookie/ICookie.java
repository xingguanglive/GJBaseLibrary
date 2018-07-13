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
     * 根据key来获取Cookie中对应的值
     */
    String getCookieValue(HttpUrl url, String key);

    /**
     * 获取所有的Cookie
     * @param httpUrl
     */
    List<Cookie> getCookies(HttpUrl httpUrl);

    /**
     * 获取本地所有的Cookie，不区分域名
     */
    List<Cookie> getCookies();

    /**
     * 清除指定域名下的Cookie
     * @param url
     */
    void clearCookie(HttpUrl url);
}
