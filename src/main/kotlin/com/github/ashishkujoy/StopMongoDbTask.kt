package com.github.ashishkujoy

import de.flapdoodle.embed.mongo.MongodExecutable
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import kotlin.system.*

open class StopMongoDbTask : DefaultTask(){

    @TaskAction
    fun task() {
        
        println("Stoping Kafka.....")
        val mongod = project.extensions.getByName("mongod") as MongodExecutable
        val time = measureTimeMillis {
            mongod.stop()
        }
        println("MongoDb stopped in $time ....")
    }
}