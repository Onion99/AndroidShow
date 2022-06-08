package com.onion.versionplugin

// ------------------------------------------------------------------------
// 统一依赖管理:Kotlin + BuildSrc，支持双向跟踪，依赖更新时会重新构建整个项目
// ------------------------------------------------------------------------
object Version {
    const val compileSdkVersion = 30
    const val minSdkVersion     = 21
    const val targetSdkVersion  = 29
    const val kotlin = "1.3.72"
    const val coroutines = "1.6.1"

    // These versions are linked: lint should be X+23.Y.Z of gradle_plugin version, according to:
    // https://github.com/alexjlockwood/android-lint-checks-demo/blob/0245fc027463137b1b4afb97c5295d60dce998b6/dependencies.gradle#L3
    const val android_gradle_plugin = "4.1.1"
    const val android_hilt_plugin = "2.31-alpha"
    const val android_spotless_plugin = "5.8.2"
    const val android_string_care_plugin = "4.2.1"
    const val android_versions_plugin = "0.28.0"
    const val android_lint_api = "30.0.0"

    const val sentry = "5.6.2"
    const val leakcanary = "2.8.1"
    const val osslicenses_plugin = "0.10.4"
    const val detekt = "1.19.0"
    const val jna = "5.8.0"

    const val androidx_compose = "1.1.1"
    const val androidx_appcompat = "1.3.0"
    const val androidx_benchmark = "1.0.0"
    const val androidx_biometric = "1.1.0"
    const val androidx_coordinator_layout = "1.1.0"
    const val androidx_constraint_layout = "2.0.4"
    const val androidx_preference = "1.1.1"
    const val androidx_legacy = "1.0.0"
    const val androidx_annotation = "1.1.0"
    const val androidx_lifecycle = "2.4.0"
    const val androidx_fragment = "1.3.4"
    const val androidx_navigation = "2.3.3"
    const val androidx_recyclerview = "1.2.1"
    const val androidx_core = "1.3.2"
    const val androidx_paging = "3.1.1"
    const val androidx_transition = "1.4.0"
    const val androidx_work = "2.7.1"
    const val androidx_datastore = "1.0.0"
    const val google_material = "1.2.1"

    const val adjust = "4.18.3"
    const val installreferrer = "1.0"

    const val junit = "5.5.2"
    const val mockk = "1.12.0"

    const val mockwebserver = "4.9.0"
    const val uiautomator = "2.2.0"
    const val robolectric = "4.6.1"

    const val google_ads_id_version = "16.0.0"

    const val google_play_store_version = "1.8.0"

    const val protobuf = "3.11.4" // keep in sync with the version used in AS.
}

@Suppress("unused")
object DependencyManager {
    const val tools_android_gradle = "com.android.tools.build:gradle:${Version.android_gradle_plugin}"
    const val tools_kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val tools_versions_gradle = "com.github.ben-manes:gradle-versions-plugin:${Version.android_versions_plugin}"
    const val tools_hilt_gradle = "com.google.dagger:hilt-android-gradle-plugin:${Version.android_hilt_plugin}"
    const val tools_spotless_gradle = "com.diffplug.spotless:spotless-plugin-gradle:${Version.android_spotless_plugin}"
    const val tools_string_care_gradle = "io.github.stringcare:plugin:${Version.android_string_care_plugin}"
    const val tools_benchmark_gradle = "androidx.benchmark:benchmark-gradle-plugin:${Version.androidx_benchmark}"
    const val kotlin_stdlib= "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlin}"
    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Version.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"
    const val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    const val kotlin_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

    const val osslicenses_plugin = "com.google.android.gms:oss-licenses-plugin:${Version.osslicenses_plugin}"

    const val sentry = "io.sentry:sentry-android:${Version.sentry}"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android-core:${Version.leakcanary}"

    const val androidx_compose_ui = "androidx.compose.ui:ui:${Version.androidx_compose}"
    const val androidx_compose_ui_test = "androidx.compose.ui:ui-test-junit4:${Version.androidx_compose}"
    const val androidx_compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Version.androidx_compose}"
    const val androidx_compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Version.androidx_compose}"
    const val androidx_compose_foundation = "androidx.compose.foundation:foundation:${Version.androidx_compose}"
    const val androidx_compose_material = "androidx.compose.material:material:${Version.androidx_compose}"
    const val androidx_annotation = "androidx.annotation:annotation:${Version.androidx_annotation}"
    const val androidx_benchmark_junit4 = "androidx.benchmark:benchmark-junit4:${Version.androidx_benchmark}"
    const val androidx_biometric = "androidx.biometric:biometric:${Version.androidx_biometric}"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:${Version.androidx_fragment}"
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Version.androidx_appcompat}"
    const val androidx_coordinator = "androidx.coordinatorlayout:coordinatorlayout:${Version.androidx_coordinator_layout}"
    const val androidx_constraint= "androidx.constraintlayout:constraintlayout:${Version.androidx_constraint_layout}"
    const val androidx_legacy = "androidx.legacy:legacy-support-v4:${Version.androidx_legacy}"
    const val androidx_lifecycle_common = "androidx.lifecycle:lifecycle-common:${Version.androidx_lifecycle}"
    const val androidx_lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.androidx_lifecycle}"
    const val androidx_lifecycle_process = "androidx.lifecycle:lifecycle-process:${Version.androidx_lifecycle}"
    const val androidx_lifecycle_view_model = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.androidx_lifecycle}"
    const val androidx_lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.androidx_lifecycle}"
    const val androidx_paging = "androidx.paging:paging-runtime-ktx:${Version.androidx_paging}"
    const val androidx_preference = "androidx.preference:preference-ktx:${Version.androidx_preference}"
    const val androidx_safeargs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.androidx_navigation}"
    const val androidx_navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Version.androidx_navigation}"
    const val androidx_navigation_ui = "androidx.navigation:navigation-ui:${Version.androidx_navigation}"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Version.androidx_recyclerview}"
    const val androidx_core = "androidx.core:core:${Version.androidx_core}"
    const val androidx_core_ktx = "androidx.core:core-ktx:${Version.androidx_core}"
    const val androidx_transition = "androidx.transition:transition:${Version.androidx_transition}"
    const val androidx_work_ktx = "androidx.work:work-runtime-ktx:${Version.androidx_work}"
    const val androidx_work_testing = "androidx.work:work-testing:${Version.androidx_work}"
    const val androidx_datastore = "androidx.datastore:datastore:${Version.androidx_datastore}"
    const val google_material = "com.google.android.material:material:${Version.google_material}"

    const val protobuf_javalite = "com.google.protobuf:protobuf-javalite:${Version.protobuf}"
    const val protobuf_compiler = "com.google.protobuf:protoc:${Version.protobuf}"

    const val adjust = "com.adjust.sdk:adjust-android:${Version.adjust}"
    const val installreferrer = "com.android.installreferrer:installreferrer:${Version.installreferrer}"

    const val jna = "net.java.dev.jna:jna:${Version.jna}@jar"

    const val junit = "junit:junit:${Version.junit}"
    const val mockk = "io.mockk:mockk:${Version.mockk}"

    // --- START AndroidX test dependencies --- //
    // N.B.: the versions of these dependencies appear to be pinned together. To avoid bugs, they
    // should always be updated together based on the latest version from the Android test releases page:
    //   https://developer.android.com/jetpack/androidx/releases/test
    // For the full IDs of these test dependencies, see:
    //   https://developer.android.com/training/testing/set-up-project#android-test-dependencies
    private const val androidx_test_shared_version = "1.4.0" // this appears to be shared with many deps.
    const val androidx_test_core = "androidx.test:core:$androidx_test_shared_version"
    private const val androidx_espresso_version = "3.4.0"
    const val espresso_core = "androidx.test.espresso:espresso-core:$androidx_espresso_version"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:$androidx_espresso_version"
    const val espresso_idling_resources = "androidx.test.espresso:espresso-idling-resource:$androidx_espresso_version"
    const val espresso_intents = "androidx.test.espresso:espresso-intents:$androidx_espresso_version"
    const val androidx_junit = "androidx.test.ext:junit:1.1.2-alpha05"
    const val androidx_test_extensions = "androidx.test.ext:junit-ktx:1.1.3"
    // Monitor is unused
    const val orchestrator = "androidx.test:orchestrator:$androidx_test_shared_version"
    const val tools_test_runner = "androidx.test:runner:$androidx_test_shared_version"
    const val tools_test_rules = "androidx.test:rules:$androidx_test_shared_version"
    // Truth is unused
    // Test services is unused
    // --- END AndroidX test dependencies --- //

    const val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Version.mockwebserver}"
    const val uiautomator = "androidx.test.uiautomator:uiautomator:${Version.uiautomator}"
    const val robolectric = "org.robolectric:robolectric:${Version.robolectric}"

    const val google_ads_id = "com.google.android.gms:play-services-ads-identifier:${Version.google_ads_id_version}"

    // Required for in-app reviews
    const val google_play_store = "com.google.android.play:core:${Version.google_play_store_version}"

    const val detektApi = "io.gitlab.arturbosch.detekt:detekt-api:${Version.detekt}"
    const val detektTest = "io.gitlab.arturbosch.detekt:detekt-test:${Version.detekt}"
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Version.junit}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Version.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Version.junit}"
}
