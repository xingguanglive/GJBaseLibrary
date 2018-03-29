# GJBaseLibrary

- 开发基础库
- 包括网络、消息分发、图片加载



##Setup
----

In your project level build.gradle :
```java
allprojects {
  repositories {
    ...
    maven { url 'https://www.jitpack.io' }
  }
}
```

In your app level build.gradle :
```
dependencies {
  compile 'com.github.xingguanglive:GJBaseLibrary:1.1.0'
}
```

#### Feature

网络加载

ImageLoader
```
  // 在Application 配置图片加载工厂GlideFactory。如需要替换第三方库，自己实现ImageFactory并设置给BaselibConfig就OK。
  // 如果不配置默认为GlideFactory
  BaseLibConfig.init(new ConfigBuilder().imageFactory(new GlideFactory())); 
  // 图片显示、加载具体使用方式
  ImageDirector.getInstance(this)
                .imageBuilder()   // builder构建图片对象
                .imageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png") //图片地址
                .errorImage(R.mipmap.ic_launcher) // 加载失败图片
                .loadingImage(R.mipmap.ic_launcher) //加载成功图片
                .imageTransformation(ImageConstants.IMAGE_TRANSFOR_CROP_CORNER) //圆角
                .cornerType(RoundedCornersTransformation.CornerType.ALL)
                .radius(10) //弧度
                .imageLoadingListener(new ImageLoadingListener() {
                  @Override
                  public void onLoadStarted(Drawable placeholder) {
                    
                  }
                  @Override
                  public void onLoadFailed(Drawable errorDrawable) {

                  }
                  @Override
                  public void onLoadingComplete(Drawable resource) {

                  }

                  @Override
                  public void onLoadCleared(Drawable placeholder) {

                  }
                })  //加载状态监听
                .into((ImageView) findViewById(R.id.iv_main_corner));//显示控件，如果为空则只加载。intoSyn()同步加载图片
```

