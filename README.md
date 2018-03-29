# GJBaseLibrary

- 开发基础库
- 包括网络、消息分发、图片加载



## Setup

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

## Feature

#### 网络
- 基于RxJava、Retrofit
- 支持Cookie
- 支持文件缓存，可配置实现数据库缓存等
  - [接口需要缓存时配置](https://github.com/chentao7v/GJBaseLibrary/blob/master/app/src/main/java/tv/guojiang/network/TestRequest.java)
  - [多种缓存策略](https://github.com/chentao7v/GJBaseLibrary/blob/master/GjBaseLibrary/src/main/java/tv/guojiang/baselib/network/cache/CacheState.java)
- 支持文件上传与下载

[网络加载示例](https://github.com/chentao7v/GJBaseLibrary/blob/master/app/src/main/java/tv/guojiang/sample/NetworkSampleActivity.java)
