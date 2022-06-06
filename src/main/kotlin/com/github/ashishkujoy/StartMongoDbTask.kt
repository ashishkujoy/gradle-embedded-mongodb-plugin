package com.github.ashishkujoy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.system.*

open class StartMongoDbTask : DefaultTask() {

    @TaskAction
    fun task() {
        project.extensions.getByName("embeddedMongoDb") as MongoDbPluginExtension
        println("Monogdb will be started")
        println("Starting mongodb.....")

        println("Mongodb is started ....")
    }
}