package tv.guojiang.baselib.network.request;

import android.support.v4.util.ArrayMap;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Request基类
 *
 * @author leo
 */
public class BaseRequest {

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
