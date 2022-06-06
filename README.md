### Embedded Mongo Plugin
This plugin provides two tasks `startEmbeddedMongo` and `stopEmbeddedMongo` for starting and stoping embedded mongo. This plugin is wrapper over https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo.

### Motivation behind the Plugin.
I was using embedded mongo in my spring boot project, over a period of time we started observing huge delay in closing embedded mongo by spring boot test. On debugging we found that spring boot test was creating multiple instance of embedded mongo whenever there were any change in application properties for a test. We tried test container mongodb module as solution for this, we were using colima for provisioning docker on mac os, colima was creating certain connectivity issue for which were not able to find out any solution. As a we result we decided to create this plugin, to make sure we are creating a only a single instance of mongodb.

#### Using locally
For using plugin locally we first need to build and publish it locally

```bash
./gradlew clean build publish
```

This command will publish the plugin to your `./build/repo` directory.

Now in consumer project you need to add the following code snippet to your `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        maven {
            url = uri("$AbosolutePathToMyPluginDirectory/build/repo/")
        }
        gradlePluginPortal()
    }
}
```

and then in `build.gradle.kts` apply the plugin
```kotlin
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.71"
    id("io.github.ashishkujoy.embeddedMongodb") version "0.0.1"
    application
}

embeddedMongodb {
    port = 9999 // default value 9092
}
```

