# Glitch Shader

[![Maven Central](https://img.shields.io/maven-central/v/io.github.makzimi/glitch-shader)](https://central.sonatype.com/artifact/io.github.makzimi/glitch-shader)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

An Android Compose library that applies the Glitch visual effect.

<image width="200px" src="https://github.com/user-attachments/assets/dbacb4ab-41bc-488b-ad04-953dae7208d8"/>
<image width="200px" src="https://github.com/user-attachments/assets/7af3d1ec-3196-409b-9d1c-e20ea828b425"/>
<image width="200px" src="https://github.com/user-attachments/assets/c1e54018-4bca-48cb-817c-1cadb37d313f"/>

## Project Structure

- **`glitch-shader/`** - Library module with AGSL shader implementation
- **`sample/`** - Demo app showcasing the glitch shader

## Installation

The library is on Maven Central. Make sure `mavenCentral()` is in your repositories in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

Add the dependency:

```kotlin
dependencies {
    implementation("io.github.makzimi:glitch-shader:0.1.0")
}
```

Or with a version catalog:

```toml
[versions]
glitchShader = "0.1.0"

[libraries]
glitch-shader = { group = "io.github.makzimi", name = "glitch-shader", version.ref = "glitchShader" }
```

```kotlin
dependencies {
    implementation(libs.glitch.shader)
}
```

Requires Android 13 (`minSdk 33`).

## Usage

```kotlin
Modifier.glitchShader(intensity = 1f, colorBarsEnabled = true)

// Animated intensity, read in the draw phase so a burst never recomposes:
Modifier.glitchShader(intensity = { burst.value })
```
