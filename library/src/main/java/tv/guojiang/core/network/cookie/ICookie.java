package tv.guojiang.core.network.cookie;

import okhttp3.Cookie;

/**
 * @author leo
 */
public interface ICookie {

    /**
     * 根据key来获取Cookie
     */
    Cookie getCookie(String key);

    /**
     * 根据key来获取Cookie中对应的值
     */
    String getCookieValue(String key);

}
