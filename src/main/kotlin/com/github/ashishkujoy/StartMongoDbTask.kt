package com.github.ashishkujoy

import com.mongodb.client.MongoClients
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.Defaults
import de.flapdoodle.embed.mongo.config.MongoCmdOptions
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.packageresolver.Command
import de.flapdoodle.embed.process.extract.NoopTempNaming
import de.flapdoodle.embed.process.extract.TempNaming
import de.flapdoodle.embed.process.extract.UserTempNaming
import de.flapdoodle.embed.process.runtime.Network
import org.bson.Document
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import kotlin.system.measureTimeMillis

open class StartMongoDbTask : DefaultTask() {

    @TaskAction
    fun task() {
        val extension = project.extensions.getByName("embeddedMongoDb") as MongoDbPluginExtension
        val command = Command.MongoD
        val noopTempNaming = NoopTempNaming()
        val runTimeConfig = Defaults.runtimeConfigFor(command)
            .artifactStore(Defaults.extractedArtifactStoreFor(command)
                .withDownloadConfig(Defaults.downloadConfigFor(command).build())
                .executableNaming { prefix, postfix ->
                    extension.executableName?.let { "${prefix}_${it}_$postfix" } ?: noopTempNaming.nameFor(
                        prefix,
                        postfix
                    )
                }
            ).build()

        val config = MongodConfig.builder()
            .version(Version.Main.V4_0)
            .cmdOptions(MongoCmdOptions.builder().useNoJournal(false).build())
            .net(Net(extension.port, Network.localhostIsIPv6()))


        val configWithReplicaSet = config
            .putArgs("--replSet", "rs0")
            .build()

        val configWithoutReplicaSet = config.build()


        println("Monogdb will be started on port ${extension.port}")
        println("Starting mongodb.....")
        val mongoStarter = MongodStarter.getInstance(runTimeConfig)
        val mongod = if (extension.createReplicaSet) {
            mongoStarter.prepare(configWithReplicaSet)
        } else {
            mongoStarter.prepare(configWithoutReplicaSet)
        }

        val time = measureTimeMillis {
            mongod.start()
            val client = MongoClients.create("mongodb://localhost:${extension.port}")
            if (extension.createReplicaSet) {
                kotlin.runCatching {
                    client.getDatabase("admin").runCommand(Document("replSetInitiate", Document()))
                }.onFailure {
                    println(it)
                }
            }
        }
        println("Mongodb is started in $time milliseconds ....")
        project.extensions.add("mongod", mongod)
    }
}