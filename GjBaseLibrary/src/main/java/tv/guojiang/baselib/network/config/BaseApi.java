/*
 * BaseApi.java
 * business
 *
 * Created by ChenTao on 2017/7/21.
 *
 * Copyright (c) 2017年 yidiandao. All rights reserved.
 */

package tv.guojiang.baselib.network.config;

import io.reactivex.Observable;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit统一发送请求的Api.
 */
public interface BaseApi {

    @GET
    Observable<ResponseBody> get(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @QueryMap Map<String, String> params
    );

    @POST
    @FormUrlEncoded
    Observable<ResponseBody> post(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @FieldMap Map<String, String> params
    );

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);

    Observable<ResponseBody> upload(@Url String url);
}
