# GJBaseLibrary Version:1.0.2
1. 描述
----
用于Android项目开发基础库：Utils、Widget、NetWork、Message、Database、Log、Image、Lib的基础封装

2. 集成
----
(1) Project--build.gradle
```
allprojects {
  repositories {
    ...
    maven { url 'https://www.jitpack.io' }
  }
}
```
(2) module--build.gradle
```
dependencies {
  compile 'com.github.xingguanglive:GJBaseLibrary:1.0.2'
}
```
3. 完成功能
----
Utils:工具
----
  ToastUtils:Toast工具类
  
Message：消息分发
----
