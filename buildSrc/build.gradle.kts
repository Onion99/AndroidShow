import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//apply {
//    from("${rootDir.parent}/dependencies.gradle")
//}

plugins {
    `kotlin-dsl`
//    kotlin("jvm") version "1.5.30"
//    kotlin("kapt")
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    compileOnly("commons-io:commons-io:2.6")
    compileOnly("commons-codec:commons-codec:1.15")
    compileOnly("org.ow2.asm:asm-commons:9.2")
    compileOnly("org.ow2.asm:asm-tree:9.2")
}