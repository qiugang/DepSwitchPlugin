# DepSwitchPlugin

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![](https://jitpack.io/v/qiugang/DepSwitchPlugin.svg)](https://jitpack.io/#qiugang/DepSwitchPlugin)

一步切换 aar 依赖或者源码依赖

对于稍微大一点的 Android 项目，将业务模块打包成aar依赖项是一种常见的操作，上传到公共或自建 maven，主项目将依赖于这些 aar。但是对于实际的开发过程，开发者经常需要切换 aar 依赖方式到源码，完成新功能开发或调试。

DepSwicth 可以使用自己的配置文件一步切换到本地源依赖项，而无需修改特定的 build.gradle 来提高开发效率。强烈建议将 DepSwicth 配置文件 ```dep-switch.json``` 添加到 .gitignore，以解决在多人开发模式下不小心修改 build.gradle 所导致的冲突。

### 流程

DepSwitch 将会处理 build.gradle 和 ```dep-switch.json``` 文件中相同依赖别名的依赖项。
默认情况下使用 build.gradle 中 DepSwitch 配置的依赖项。依赖切换开关 ```dep-switch.json``` 中的 ```status``` 字段定义。

例子:

在 example 中, 我们添加了正常的远程依赖项，别名为 "module_a"
```
depSwitch {
    "module_a" {
        action = "implementation"
        dependency = "com.android.support:appcompat-v7:26.1.0"
    }    
}
```
DepSwitchPlugin 的配置文件(```dep-switch.json```)如下

```
[
    {
        "name": "module_a",
        "depNotation": "example_module",
        "status": true
    },
    {
        "name": "module_b",
        "depNotation": "${your local src module}",
        "status": false
    }
]

```
最终依赖别名为 ```module_a``` 的依赖项会被处理为 : ```implementation project(":example_module")```，这样就完成了依赖切换

### 使用

with gradle
```
apply plugin: 'dep-switch'

buildscript {
    repositories {
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath "com.github.qiugang:DepSwitchPlugin:${lastest-verion}"
    }
}

depSwitch {
    "module_a" {
        action = "implementation"
        dependency = "com.android.support:appcompat-v7:26.1.0"
    }
    "module_b" {
        action = "implementation"
        dependency = "com.squareup.retrofit2:retrofit:2.4.0"
    }
    //...
}
```
在 project 目录下添加 ```dep-switch.json``` 配置文件

```
[
    {
        "name": "module_a",
        "depNotation": "example_module",
        "status": true
    },
    {
        "name": "module_b",
        "depNotation": "${your local src module}",
        "status": false
    }
]
```

详细可以查看 example~


### License

    Copyright 2018 qiugang(thisisqg@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
