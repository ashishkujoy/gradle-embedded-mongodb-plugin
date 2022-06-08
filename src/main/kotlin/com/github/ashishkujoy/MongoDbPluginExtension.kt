package com.github.ashishkujoy

open class MongoDbPluginExtension {
    var port: Int = 27017
    var createReplicaSet: Boolean = false
    var replicaSetName: String = "rs0"
    var executableName: String? = null
}