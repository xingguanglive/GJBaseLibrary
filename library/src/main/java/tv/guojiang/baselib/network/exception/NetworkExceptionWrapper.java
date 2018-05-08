/*
 * ExceptionUtil.java
 * business
 *
 * Created by ChenTao on 2017/5/25.
 *
 * Copyright (c) 2017年 yidiandao. All rights reserved.
 */

package tv.guojiang.baselib.network.exception;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertPathValidatorException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.HttpException;

/**
 * 处理网络异常,对所有的网络异常进行封装
 */
public class NetworkExceptionWrapper {

    // http 的 状态码
    interface HTTP {

        int ACCESS_DENIED = 302;

        int UNAUTHORIZED = 401;

        int FORBIDDEN = 403;

        int NOT_FOUND = 404;

        int HANDEL_ERROR = 417;

        int REQUEST_TIMEOUT = 408;

        int INTERNAL_SERVER_ERROR = 500;

        int BAD_GATEWAY = 502;

        int SERVICE_UNAVAILABLE = 503;

        int GATEWAY_TIMEOUT = 504;

    }


    /**
     * 自定义网络相关异常状态码
     */
    public interface SERVER_ERROR {

        /**
         * 无网络连接
         */
        int NO_CONNECT = 1000;

        /**
         * 解析错误
         */
        int PARSE_ERROR = 1001;

        /**
         * 网络错误
         */
        int NETWORK_ERROR = 1002;

        /**
         * 协议出错
         */
        int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        int TIMEOUT_ERROR = 1006;

        /**
         * 证书未找到
         */
        int SSL_NOT_FOUND = 1007;

        /**
         * 格式错误
         */
        int FORMAT_ERROR = 1008;

        /**
         * 未知错误
         */
        int UNKNOWN = 1009;

        /**
         * 出现空值
         */
        int NULL = -100;
    }

    /**
     * 对网络相关错误进行封装
     */
    public static NetworkException wrapException(Throwable e) {

        NetworkException networkException;

        if (e instanceof UnknownHostException) {
            networkException = new NetworkException(e, SERVER_ERROR.NO_CONNECT);
            networkException.setMessage("暂无网络连接，请检查网络设置");
            return networkException;
        } else if (e instanceof HttpException) {
            // retrofit 抛出的 网络错误

            HttpException httpException = (HttpException) e;
            networkException = new NetworkException(e, SERVER_ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case HTTP.UNAUTHORIZED:
                    networkException.setMessage("未授权的请求");
                case HTTP.FORBIDDEN:
                    networkException.setMessage("禁止访问");
                case HTTP.NOT_FOUND:
                    networkException.setMessage("资源不存在");
                case HTTP.REQUEST_TIMEOUT:
                    networkException.setMessage("请求超时");
                case HTTP.GATEWAY_TIMEOUT:
                    networkException.setMessage("网关响应超时");
                case HTTP.INTERNAL_SERVER_ERROR:
                    networkException.setMessage("服务器出错");
                case HTTP.BAD_GATEWAY:
                    networkException.setMessage("无效的请求");
                case HTTP.SERVICE_UNAVAILABLE:
                    networkException.setMessage("服务器不可用");
                case HTTP.ACCESS_DENIED:
                    networkException.setMessage("网络错误");
                case HTTP.HANDEL_ERROR:
                    networkException.setMessage("接口处理失败");
                default:
                    networkException.setMessage(e.getMessage());
                    break;
            }
            networkException.setCode(httpException.code());
            return networkException;
        } else if (e instanceof ApiException) {
            // 业务错误
            ApiException api = (ApiException) e;
            networkException = new NetworkException(api, api.getCode());
            networkException.setMessage(api.getMessage());
            return networkException;
        } else if (e instanceof JsonParseException
            || e instanceof JSONException
            || e instanceof ParseException) {
            // throw after Parse exception
            networkException = new NetworkException(e, SERVER_ERROR.PARSE_ERROR);
            networkException.setMessage("解析错误");
            return networkException;
        } else if (e instanceof ConnectException) {
            networkException = new NetworkException(e, SERVER_ERROR.NETWORK_ERROR);
            networkException.setMessage("连接失败");
            return networkException;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            networkException = new NetworkException(e, SERVER_ERROR.SSL_ERROR);
            networkException.setMessage("证书验证失败");
            return networkException;
        } else if (e instanceof CertPathValidatorException) {
            networkException = new NetworkException(e, SERVER_ERROR.SSL_NOT_FOUND);
            networkException.setMessage("证书路径没找到");
            return networkException;
        } else if (e instanceof ConnectTimeoutException) {
            networkException = new NetworkException(e, SERVER_ERROR.TIMEOUT_ERROR);
            networkException.setMessage("连接超时");
            return networkException;
        } else if (e instanceof SocketTimeoutException) {
            networkException = new NetworkException(e, SERVER_ERROR.TIMEOUT_ERROR);
            networkException.setMessage("连接超时");
            return networkException;
        } else if (e instanceof ClassCastException) {
            networkException = new NetworkException(e, SERVER_ERROR.FORMAT_ERROR);
            networkException.setMessage("类型转换出错");
            return networkException;
        } else if (e instanceof NullPointerException) {
            networkException = new NetworkException(e, SERVER_ERROR.NULL);
            networkException.setMessage("数据有空");
            return networkException;
        } else {
            networkException = new NetworkException(e, SERVER_ERROR.UNKNOWN);
            networkException.setMessage(e.getLocalizedMessage());
            return networkException;
        }
    }
}
