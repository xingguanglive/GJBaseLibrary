package tv.guojiang.core.network.request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * RetrofitFormWrapper,主要用来封装文件上传相关的数据
 */
public class RetrofitFormWrapper {

    /*
     * @Part('key') RequestBody
     * @Part MultipartBody.Part
     *
     * 单独的RequestBody未封装key，只有value，可以通过给@Part添加参数完成
     * 或者通过MultipartBody.Part构建一个带有key与value的Part。
     * 封装过程也需要使用到RequestBody
     *
     * MultipartBody.Part 是构建表单的单个类似key-value的结构
     *
     * MultipartBody 才是最终的表单，可以通过MultipartBody.Builder构建
     *
     * 注意:使用@Part("fileKey") RequestBody 无法完成文件的上传,只有使用@Part MultiPartBody.Part才行
     */

    /**
     * 将单个文件转换成 {@link MultipartBody.Part}
     * <p><em>一个key对应一个文件</em>
     * <pre>
     * 使用时注意：
     *     1. Service对应的方法上需使用 @MultiPart @POST注解
     *     2. 方法参数中使用 @Part MultipartBody.Part file 来上传文件
     * </pre>
     *
     * @param key 上传文件的Key
     * @param file 上传的单个文件
     * @param mediaType 文件类型,可通过{@link MediaType#parse(String)}方法转换
     */
    public static MultipartBody.Part file2Part(String key, File file, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(mediaType, file);
        // 这里的作用应该和@Part("key")是一样的
        // RequestBody只是封装了值，并没有封装key
        // key需要通过MultipartBody.Part来完成封装
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(),
            requestBody);
        return part;
    }

    /**
     * 将多个文件转换成 {@link List<MultipartBody.Part>}
     * <p><em>一个key对应多个文件</em>
     * <pre>
     * 使用时注意：
     *     1. Service对应的方法上需使用 @MultiPart @POST注解
     *     2. 方法参数中使用 @Part List<MultipartBody.Part> files 来上传文件
     * </pre>
     *
     * @param key 文件上传的Key
     * @param files 同一个Key对应的多个文件
     * @param mediaType 文件类型,可通过{@link MediaType#parse(String)}方法转换
     */
    public static List<MultipartBody.Part> files2Parts(String key, List<File> files,
                                                       MediaType mediaType) {

        if (files == null || files.size() == 0) {
            throw new NullPointerException("Can not upload without files");
        }

        List<MultipartBody.Part> parts = new ArrayList<>(files.size());

        for (File file : files) {
            MultipartBody.Part part = file2Part(key, file, mediaType);
            parts.add(part);
        }

        return parts;
    }

    /**
     * 将多个文件及参数转换成{@link MultipartBody}
     * <p><em>一个key对应多个文件</em>
     * <pre>
     * 使用时注意：
     *     1. Service对应的方法上需使用 @POST注解 , <b>不需要使用@MultiPart注解</b>
     *     2. 方法参数中使用 @Body MultipartBody body 来上传文件且传递参数
     * </pre>
     *
     * @param params 除文件外的其他参数
     * @param fileKey 文件上传的Key
     * @param files 同一个Key对应多个文件
     * @param fileMediaType 文件类型,可通过{@link MediaType#parse(String)}方法转换
     */
    public static MultipartBody getParamsFormData(Map<String, String> params, String fileKey,
                                                  List<File> files, MediaType fileMediaType) {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        // 文件数据
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(fileMediaType, file);
            builder.addFormDataPart(fileKey, file.getName(), requestBody);
        }

        if (params != null && params.size() != 0) {
            // 参数数据
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
                builder.addFormDataPart(key, null, requestBody);
            }
        }
        builder.setType(MultipartBody.FORM);

        return builder.build();
    }

    /**
     * 将单个文件及参数转换成{@link MultipartBody}
     * <p><em>一个key对应多个文件</em>
     * <pre>
     * 使用时注意：
     *     1. Service对应的方法上需使用 @POST注解 , <b>不需要使用@MultiPart注解</b>
     *     2. 方法参数中使用 @Body MultipartBody body 来上传文件且传递参数
     * </pre>
     *
     * @param params 除文件外的其他参数
     * @param fileKey 文件上传的Key
     * @param file 上传的文件
     * @param fileMediaType 文件类型,可通过{@link MediaType#parse(String)}方法转换
     */
    public static MultipartBody getFormData(Map<String, String> params, String fileKey, File file,
                                            MediaType fileMediaType) {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        // 文件数据
        RequestBody fileBody = RequestBody.create(fileMediaType, file);
        builder.addFormDataPart(fileKey, file.getName(), fileBody);

        if (params != null && params.size() != 0) {
            // 参数数据
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                RequestBody paramBody = RequestBody.create(MediaType.parse("text/plain"), value);
                builder.addFormDataPart(key, null, paramBody);
            }
        }
        builder.setType(MultipartBody.FORM);

        return builder.build();
    }

    /**
     * 获取 key 的 {@link RequestBody}
     * <p><em>主要用于 文件上传 与 普通key-value 分开 的情况</em>
     * <pre>
     * 使用时注意：
     *      1. 对于单个文件，一般结合{@link #file2Part(String, File, MediaType)}一起使用
     *      2. 对于多个文件，一般结合{@link #files2Parts(String, List, MediaType)}一起使用
     *      3. 对于文件之外的参数，使用 @Part("key") RequestBody value 来传递
     * </pre>
     *
     * @param value 对应key的value
     */
    public static RequestBody param2RequestBody(String value) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody requestBody = RequestBody.create(textType, value);
        return requestBody;
    }

    /**
     * 将普通的key-value转为 {@link MultipartBody.Part}
     * <p><em>可用于文件与key-value分开使用参数的情况</em>
     * <pre>
     * 使用时注意：
     *      1. 参数的@Part注解，不需要参数，因为key就是其参数
     *      2. 使用@Part MultipartBody.Part既可用完成使用
     * </pre>
     */
    public static MultipartBody.Part param2Part(String key, String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        return part;
    }

    /**
     * 生成一个json Body
     */
    public static RequestBody getJsonBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }
}
