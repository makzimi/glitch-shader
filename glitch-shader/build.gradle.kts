plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.mavenPublish)
}

android {
    namespace = "dev.maxkach.shaders.glitch"
    compileSdk = 36

    defaultConfig {
        minSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.animation.core)
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }

    coordinates("io.github.makzimi", "glitch-shader", "0.1.0")

    pom {
        name.set("Glitch Shader")
        description.set("An AGSL glitch effect for Jetpack Compose: slice displacement, RGB split, scanline noise and colour bars.")
        inceptionYear.set("2025")
        url.set("https://github.com/makzimi/glitch-shader")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/makzimi/glitch-shader/blob/main/LICENSE")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("makzimi")
                name.set("Maxim Kachinkin")
                url.set("https://github.com/makzimi")
            }
        }
        scm {
            url.set("https://github.com/makzimi/glitch-shader")
            connection.set("scm:git:git://github.com/makzimi/glitch-shader.git")
            developerConnection.set("scm:git:ssh://git@github.com/makzimi/glitch-shader.git")
        }
    }
}
