package tv.guojiang.core;

import tv.guojiang.core.image.factory.ImageFactory;

/**
 * @author Elvis
 */

public class ConfigBuilder {

    public ImageFactory imageFactory;

    public ConfigBuilder imageFactory(ImageFactory factory) {
        imageFactory = factory;
        return this;
    }
}
