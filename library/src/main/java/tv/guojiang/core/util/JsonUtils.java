package tv.guojiang.core.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.lang.reflect.Type;

/**
 * 提供Gson对象
 */
public class JsonUtils {

    // 所有注解都生成json
    private Gson mGson = new Gson();

    // 只有expose注解的生成json
    private Gson mWithExposeGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .create();

    // 没有expose注解的生成json
    private Gson mWithoutExposeGson = new GsonBuilder()
        .addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // 排除Expose注解
                Expose expose = f.getAnnotation(Expose.class);
                return expose != null && expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
        .create();

    private JsonUtils() {
    }

    public static JsonUtils getInstance() {
        return Singleton.INSTANCE;
    }

    private static final class Singleton {

        private static final JsonUtils INSTANCE = new JsonUtils();
    }

    /**
     * 将Json字符串转换为对对象
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return mGson.fromJson(json, clazz);
    }

    public <T> T fromJson(String json, Type type) {
        return mGson.fromJson(json, type);
    }

    /**
     * 将对象转换为Json字符串
     */
    public String toJson(Object object) {
        return mGson.toJson(object);
    }

    /**
     * 将Json字符串转换为对象.<em>带{@link Expose}注解的有效</em>
     */
    public <T> T fromJsonWithExpose(String json, Class<T> clazz) {
        return mWithExposeGson.fromJson(json, clazz);
    }

    /**
     * 将对象转换为Json字符串.<em>带{@link Expose}注解的有效</em>
     */
    public String toJsonWithExpose(Object object) {
        return mWithExposeGson.toJson(object);
    }

    /**
     * 将Json字符串转换为对象.<em>不带{@link Expose}注解的有效</em>
     */
    public <T> T fromJsonWithoutExpose(String json, Class<T> clazz) {
        return mWithoutExposeGson.fromJson(json, clazz);
    }

    /**
     * 将对象转换为Json字符串.<em>不带{@link Expose}注解的有效</em>
     */
    public String toJsonWithoutExpose(Object object) {
        return mWithoutExposeGson.toJson(object);
    }

}
