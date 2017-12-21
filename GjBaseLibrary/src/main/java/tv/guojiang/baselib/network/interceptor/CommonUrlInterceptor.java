package tv.guojiang.baselib.network.interceptor;

/**
 * 拼接在接口上的公共请求参数.
 *
 * @author leo
 */
public class CommonUrlInterceptor implements UrlInterceptor {

    /**
     * App版本
     */
    public String version;

    /**
     * App平台(Android)
     */
    public String platform;

    /**
     * App的包名
     */
    public String packageId;

    /**
     * App的渠道
     */
    public String channel;

    /**
     * 设备名称
     */
    public String deviceName;

    /**
     * 设备Android版本
     */
    public String androidVersion;

    /**
     * 拼接公共参数到url中
     */
    public String getJoinUrl(String url) {
        StringBuilder sb = new StringBuilder();
        if (!url.contains("?")) {
            sb.append("?");
        } else {
            sb.append("&");
        }

        sb.append("version=").append(version)
            .append("&platform=").append(platform)
            .append("&packageId=").append(packageId)
            .append("&channel=").append(channel)
            .append("&deviceName=").append(deviceName)
            .append("&androidVersion=").append(androidVersion);

        return sb.toString();
    }

    @Override
    public String intercept(String url) {
        return getJoinUrl(url);
    }
}
