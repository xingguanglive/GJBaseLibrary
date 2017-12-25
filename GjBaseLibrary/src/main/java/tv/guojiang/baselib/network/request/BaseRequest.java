package tv.guojiang.baselib.network.request;

import android.support.v4.util.ArrayMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Request基类.
 *
 * 默认情况下会将所有的字段当做请求参数传递到后台，如果不想最后的参数被当做请求参数传递到服务器，
 * 请使用{@link com.google.gson.annotations.Expose}注解指明
 *
 * @author leo
 */
public class BaseRequest {

    @Expose
    private Map<String, String> requestParams = new ArrayMap<>();

    /**
     * 获取请求参数
     */
    public Map<String, String> getRequestParams() {
        return getRequestParams(this.getClass());
    }

    private Map<String, String> getRequestParams(Class clazz) {

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            Expose expose = field.getAnnotation(Expose.class);
            if (expose != null) {
                continue;
            }

            SerializedName name = field.getAnnotation(SerializedName.class);
            if (name != null) {
                // 有 SerializedName 注解，读取注解与值
                try {
                    // 跳过String 为空的字段
                    if (field.getType() == String.class && field.get(this) == null) {
                        continue;
                    }
                    requestParams.put(name.value(), String.valueOf(field.get(this)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                // 没有 SerializedName 注解，读取字段与值
                try {
                    if (field.getType() == String.class && field.get(this) == null) {
                        continue;
                    }
                    requestParams.put(field.getName(), String.valueOf(field.get(this)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // 获取父类上的请求
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getRequestParams(superClazz);
        }

        return requestParams;
    }
}
