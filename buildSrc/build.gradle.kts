import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    `kotlin-dsl`
//    kotlin("jvm") version com.onion.plugin.dependencies.Versions.kotlin
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
apply(from = "${rootDir.parent}/dependencies_manager.gradle")
@Suppress("GradleDependency")
dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    val asmVersion = "7.0"
    compileOnly("commons-io:commons-io:2.6")
    compileOnly("commons-codec:commons-codec:1.15")
    compileOnly("org.ow2.asm:asm-commons:$asmVersion")
    compileOnly("org.ow2.asm:asm-tree:$asmVersion")
}
apply(from = "${rootDir.parent}/exclude_other_version.gradle")
