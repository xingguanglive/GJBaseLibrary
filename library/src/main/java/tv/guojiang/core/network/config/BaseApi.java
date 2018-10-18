/*
 * BaseApi.java
 * business
 *
 * Created by ChenTao on 2017/7/21.
 *
 * Copyright (c) 2017年 yidiandao. All rights reserved.
 */

package tv.guojiang.core.network.config;

import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    // POST普通表单
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> post(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @FieldMap Map<String, String> params
    );

    // POST Json
    @POST
    Observable<ResponseBody> post(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @Body RequestBody body);

    // Download
    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);

    // Post Multipart
    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @PartMap Map<String, RequestBody> params,
        @Part MultipartBody.Part file
    );

    // Post Multipart
    @POST
    @Multipart
    Observable<ResponseBody> uploadFiles(
        @Url String url,
        @HeaderMap Map<String, String> headers,
        @PartMap Map<String, RequestBody> params,
        @Part List<MultipartBody.Part> files
    );
}
