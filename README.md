# DepSwitchPlugin
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![](https://jitpack.io/v/qiugang/DepSwitchPlugin.svg)](https://jitpack.io/#qiugang/DepSwitchPlugin)

One-step to switch aar dependency or source dependency.

[中文文档](https://github.com/qiugang/DepSwitchPlugin/blob/master/README-zh.md)

For a slightly larger Android projects, packaging business modules into aar dependencies is a common operation, uploading to public or self-built maven, and the main project will rely on these aars. But for the actual development process, developers often need to switch dependencies from aar to source dependencies to complete new features or debug.

DepSwicth can use its own configuration file to switch to local source dependencies in one step, without modifying the specific build.gradle to improve development efficiency. It is also highly recommended to add the configuration file to ```.gitignore``` to resolve conflicts caused by modifying the build.gradle file in multiplayer development mode.

### How it works

DepSwitch will handle the same config alias in the dependency of build.gradle and ```dep-switch.json``` config file.
By default, the dependencies of the DepSwitch configuration in build.gradle are used. The dependency switch is defined by ```status``` field in ```dep-switch.json``` .

For example:

in example project, we add normal dependency，alias is ```module_a```
```
depSwitch {
    "module_a" {
        action = "implementation"
        dependency = "com.android.support:appcompat-v7:26.1.0"
    }
}
```
and the DepSwitchPlugin config file(```dep-switch.json```) is

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

the result of alias named ```module_a``` will be configured as ```implementation project(":example_module")```
to complete dependency switch.

### How to use

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
add ```dep-switch.json``` to sub project directory

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

See example for more details, have fun😊


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
