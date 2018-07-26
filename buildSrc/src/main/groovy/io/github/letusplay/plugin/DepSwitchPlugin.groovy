/*
 *    Copyright 2018 qiugang(thisisqg@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.letusplay.plugin

import groovy.json.JsonSlurper
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project

class DepSwitchPlugin implements Plugin<Project> {

    private static final String DEP_SWITCH_FILE = "dep-switch.json"

    void apply(Project project) {

        project.extensions.add('depSwitch', project.container(DepConfig))

        println("#############################")
        println("##### Dep Switch Plugin #####")
        println("#############################")

        project.afterEvaluate {

            // process local config
            def jsonPayload = project.file(DEP_SWITCH_FILE)
            def localDepMap = new HashMap<String, String>()
            if (jsonPayload != null) {
                def states = new JsonSlurper().parseText(jsonPayload.text)
                states.each {
                    state ->
                        if (state.status) {
                            localDepMap.put(state.name, state.depNotation)
                        }
                }
            }

            final NamedDomainObjectContainer<DepConfig> container =
                    project.extensions.getByName('depSwitch')

            // replace and config
            container.toList().each {
                config ->
                    if (localDepMap.size() > 0 && localDepMap.containsKey(config.name)) {
                        println("... ${config.name} use local dependency")
                        project.dependencies.add(config.action,
                                project.getRootProject().project(localDepMap.get(config.name)))
                    } else {
                        project.dependencies.add(config.action, config.dependency)
                    }
            }
        }
    }
}
