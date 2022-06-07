package com.github.ashishkujoy

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class EmbeddedMongoDbPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create<MongoDbPluginExtension>("embeddedMongoDb")

        project.tasks.register("startEmbeddedMongoDb", StartMongoDbTask::class.java) {
            group = "embeddedMongoDbTasks"
        }

        project.tasks.register("stopEmbeddedMongoDb", StopMongoDbTask::class.java) {
            group = "embeddedMongoDbTasks"
        }
    }



}

fun Project.`embeddedMongoDb`(configure: Action<MongoDbPluginExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("embeddedMongoDb", configure)
