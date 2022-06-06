package com.github.ashishkujoy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import kotlin.system.*

open class StopMongoDbTask : DefaultTask(){

    @TaskAction
    fun task() {
        
        println("Stoping Kafka.....")
        
        println("MongoDb stopped ...")
    }
}