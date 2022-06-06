plugins {
    `java-gradle-plugin`
    `kotlin-dsl-base`
    java
    id("org.jetbrains.kotlin.jvm") version "1.3.71"
    id("maven-publish") 
    id("com.gradle.plugin-publish") version "0.18.0" 
}

group = "io.github.ashishkujoy.embeddedMongoDb"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
}

gradlePlugin {
    val greeting by plugins.creating {
        id = "io.github.ashishkujoy.embeddedMongodb"
        implementationClass = "com.github.ashishkujoy.EmbeddedMongoDbPlugin"
        version = version
        description = "A plugin to start/stop embedded mongodb"
        displayName = "Embedded Mongodb"
    }
}



pluginBundle {
    website = "https://github.com/ashishkujoy/gradle-embedded-mongodb-plugin"   
    vcsUrl = "https://github.com/ashishkujoy/gradle-embedded-mongodb-plugin"   
    tags = listOf("mongodb", "embedded mongodb") 
}

