plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.truvideo.camera"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

// Create configuration for copyDependencies
configurations {
    create("copyDependencies")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.truvideo.sdk.android.camera)
    
    // Coil3 Android module (required for ImageLoader.Builder(Context) constructor)
    //implementation("io.coil-kt.coil3:coil-android:3.3.0")
    /*configurations["copyDependencies"].dependencies.add(
        project.dependencies.create("com.github.Truvideo:truvideo-sdk-android-camera:79.2.2")
    )*/

    // Coil3 dependencies - Android-specific module needed for ImageLoader.Builder(Context)
//    configurations["copyDependencies"].dependencies.add(
//        project.dependencies.create("io.coil-kt.coil3:coil:3.3.0")
//    )
//    // Android-specific module (provides ImageLoader.Builder(Context) constructor)
//    configurations["copyDependencies"].dependencies.add(
//        project.dependencies.create("io.coil-kt.coil3:coil-android:3.3.0")
//    )
//    configurations["copyDependencies"].dependencies.add(
//        project.dependencies.create("io.coil-kt.coil3:coil-compose:3.3.0")
//    )
//    configurations["copyDependencies"].dependencies.add(
//        project.dependencies.create("io.coil-kt.coil3:coil-video:3.3.0")
//    )
//
//    configurations["copyDependencies"].dependencies.add(
//        project.dependencies.create("androidx.work:work-runtime-ktx:2.11.0")
//    )
//

    implementation("com.google.code.gson:gson:2.10.1")
}
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.skiko") {
            useTarget("androidx.compose.ui:ui:1.6.1") // Replace with a suitable alternative
        }
    }
}


// Copy dependencies for binding library
project.afterEvaluate {
    tasks.register<Copy>("copyDeps") {
        // Copy both .aar and .jar files to build outputs
        from(configurations["copyDependencies"].filter {
            it.name.endsWith(".aar") || it.name.endsWith(".jar")
        })
        into("${buildDir}/outputs/deps")
    }
    
    // Also copy coil-android to libs folder for binding project
    tasks.register<Copy>("copyCoilAndroidToLibs") {
        from(configurations["copyDependencies"].filter {
            it.name.contains("coil-android") && it.name.endsWith(".aar")
        })
        into("libs")
    }
    
    tasks.named("preBuild") { 
        finalizedBy("copyDeps")
        finalizedBy("copyCoilAndroidToLibs")
    }
}