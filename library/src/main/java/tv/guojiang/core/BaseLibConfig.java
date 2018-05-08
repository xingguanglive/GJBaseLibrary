package tv.guojiang.core;

public class BaseLibConfig {

    public static ConfigBuilder mConfigBuilder;

    public static void init(ConfigBuilder configBuilder) {
        mConfigBuilder = configBuilder;
    }
}
