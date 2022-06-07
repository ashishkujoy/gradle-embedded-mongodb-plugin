package com.github.ashishkujoy

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongoCmdOptions
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.config.Storage
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.system.measureTimeMillis

open class StartMongoDbTask : DefaultTask() {

    @TaskAction
    fun task() {
        val extension = project.extensions.getByName("embeddedMongoDb") as MongoDbPluginExtension
        val cmdOptions = MongoCmdOptions
            .builder()
            .useNoPrealloc(false)
            .useSmallFiles(false)
            .isVerbose(false)
            .master(false)
            .syncDelay(0)
            .build()

        val config = MongodConfig
            .builder()
            .version(Version.Main.PRODUCTION)
            .net(Net("localhost", extension.port, Network.localhostIsIPv6()))
            .replication(Storage(null, "rs0", 5000))
            .cmdOptions(cmdOptions)
            .build()


        println("Monogdb will be started on port ${extension.port}")
        println("Starting mongodb.....")
        val mongoStarter = MongodStarter.getDefaultInstance()
        val mongod = mongoStarter.prepare(config)
        val time = measureTimeMillis {
            mongod.start()
        }
        println("Mongodb is started in $time milliseconds ....")
        project.extensions.add("mongod", mongod)
    }
}