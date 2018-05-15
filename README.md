# GJBaseLibrary [ ![Download](https://api.bintray.com/packages/xingguanglive/maven/GJBaseLibrary/images/download.svg) ](https://bintray.com/xingguanglive/maven/GJBaseLibrary/_latestVersion)

- 开发基础库
- 包括网络、消息分发、图片加载



## Setup

In your app level build.gradle :
```
dependencies {
  compile 'tv.guojiang.core:GJBaseLibrary:latest_version'
}
```

## Feature

### 网络
- 基于RxJava、Retrofit
- 支持Cookie
- 支持文件缓存，可配置实现数据库缓存等
  - 使用注解的方式配置缓存，可配置缓存的有效期与缓存策略。[示例](https://github.com/chentao7v/GJBaseLibrary/blob/master/app/src/main/java/tv/guojiang/network/TestRequest.java)
  - 多种缓存策略。[示例](https://github.com/chentao7v/GJBaseLibrary/blob/master/GjBaseLibrary/src/main/java/tv/guojiang/baselib/network/cache/CacheState.java)
- 支持文件上传与下载

[网络加载示例](https://github.com/chentao7v/GJBaseLibrary/blob/master/app/src/main/java/tv/guojiang/sample/NetworkSampleActivity.java)
