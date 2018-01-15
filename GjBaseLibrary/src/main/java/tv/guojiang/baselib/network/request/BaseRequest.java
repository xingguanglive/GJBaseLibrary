package tv.guojiang.baselib.network.request;

import android.support.v4.util.ArrayMap;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.util.Map;
import tv.guojiang.baselib.network.annotation.Filter;
import tv.guojiang.baselib.network.annotation.Header;

/**
 * Request基类.封装了单个请求的header和参数
 *
 * 默认情况下会将所有的字段当做请求参数传递到后台；如果
 * 如果不想最后的参数被当做请求参数传递到服务器，请使用{@link com.google.gson.annotations.Expose}注解指明
 *
 * @author leo
 */
public class BaseRequest {

    @Filter
    private boolean isParsing;

    @Filter
    private Map<String, String> params = new ArrayMap<>();

    @Filter
    private Map<String, String> headers = new ArrayMap<>();


    /**
     * 获取请求参数
     */
    public Map<String, String> getParams() {
        parseAnnotation();
        return params;
    }

    /**
     * 获取请求Headers
     */
    public Map<String, String> getHeaders() {
        parseAnnotation();
        return headers;
    }

    private void parseAnnotation() {
        // 确保只解析一次注解
        if (isParsing) {
            return;
        }
        isParsing = true;
        parse(getClass());
    }

    /**
     * 解析注解，获取参数
     */
    private void parse(Class clazz) {

        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);

            try {
                // 对不符合要求的字段进行过滤
                if (doFilter(field, this)) {
                    continue;
                }

                SerializedName name = field.getAnnotation(SerializedName.class);
                Header header = field.getAnnotation(Header.class);
                if (header == null) {
                    // 请求参数
                    if (name == null) {
                        params.put(field.getName(), String.valueOf(field.get(this)));
                    } else {
                        params.put(name.value(), String.valueOf(field.get(this)));
                    }
                } else {
                    // Header
                    headers.put(header.value(), String.valueOf(field.get(this)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 获取父类上的请求
        // 排除Object类中的字段
        Class superClazz = clazz.getSuperclass();
        if (superClazz == null || superClazz.equals(Object.class)) {
            return;
        }

        parse(superClazz);
    }

    /**
     * 字段过滤
     */
    private static boolean doFilter(Field field, Object obj) throws IllegalAccessException {
        Object value = field.get(obj);
        if (value == null) {
            // 过滤掉没有值的
            return true;
        } else if (field.getName().contains("serialVersionUID")) {
            // 过滤掉serialVersionUID
            return true;
        } else if (field.getAnnotation(Filter.class) != null) {
            // 过滤掉有Filter注解的
            return true;
        } else if (field.getType() == String.class) {
            // 非空字符串不过滤
            return false;
        } else if (value instanceof Number) {
            // 数字Byte,Short,Integer,Long,Float,Double 不过滤
            return false;
        } else if (value instanceof Boolean) {
            // Boolean 不过滤
            return false;
        }

        // 其他的都过滤掉
        return true;
    }
}
