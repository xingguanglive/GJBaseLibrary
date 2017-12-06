package tv.guojiang.baselib.image;

import tv.guojiang.baselib.image.factory.ImageFactory;

/**
 * @author Elvis
 * @date 30/11/2017
 * @description tv.guojiang.baselib.image
 */

public class ConfigBuilder {
	public ImageFactory imageFactory;

	public ConfigBuilder imageFactory(ImageFactory factory) {
		imageFactory = factory;
		return this;
	}
}
