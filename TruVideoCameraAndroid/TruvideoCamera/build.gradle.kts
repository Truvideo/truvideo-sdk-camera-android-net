plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.truvideo.camera"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
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
        //jvmTarget = "17"
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
    implementation(libs.androidx.startup.runtime)
    configurations["copyDependencies"].dependencies.add(
        project.dependencies.create("com.truvideo.com:sdk-camera:1.0.5")
    )
    implementation(libs.gson)
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