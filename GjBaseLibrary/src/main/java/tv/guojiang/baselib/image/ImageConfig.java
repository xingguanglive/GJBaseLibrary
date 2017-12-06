package tv.guojiang.baselib.image;

/**
 * @author Elvis
 * @date 24/11/2017
 * @description Image初始化配置
 */
public class ImageConfig {
	public static ConfigBuilder mConfigBuilder;

	public static void init(ConfigBuilder configBuilder) {
		mConfigBuilder = configBuilder;
	}
}
