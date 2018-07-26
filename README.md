# DepSwitchPlugin
One-step to switch from aar dependency to source dependency.

### How it works

DepSwitch will handle the same config name int the dependency of build.gradle and ```dep-switch.json``` config file.
By default, the dependencies of the DepSwitch configuration in build.gradle are used. The dependency switch is defined by ```status``` field in ```dep-switch.json``` .

For example:

in example project, mock dependency is
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
      }
    ]

```

the result of "module_a" will be configured as ```implementation project(":example_module")```
to complete dependency switch.

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
