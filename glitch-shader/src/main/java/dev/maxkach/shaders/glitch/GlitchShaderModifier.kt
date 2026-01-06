package dev.maxkach.shaders.glitch

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.random.Random

@Composable
fun Modifier.glitchShader(
    intensity: Float = 1f,
    slices: Float = 16f,
    frameDuration: Int = 16,
    noiseIntensity: Float = 1f,
    colorBarsEnabled: Boolean = false,
    rgbSplitIntensity: Float = 1f,
    isEnabled: Boolean = true,
): Modifier {
    val time: Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }
    LaunchedEffect(frameDuration) {
        while (true) {
            time.animateTo(
                targetValue = time.value + 1f,
                animationSpec = tween(durationMillis = frameDuration, easing = LinearEasing)
            )
        }
    }

    val shader = remember { RuntimeShader(GLITCH_SHADER) }

    return this.then(
        Modifier.graphicsLayer {
            val timeValue = time.value
            shader.setFloatUniform("time", timeValue)
            shader.setFloatUniform("imageSize", size.width, size.height)
            shader.setFloatUniform("intensity", intensity)
            shader.setFloatUniform("slices", slices)
            shader.setFloatUniform("noiseIntensity", noiseIntensity)
            shader.setFloatUniform("colorBarsEnabled", if (colorBarsEnabled) 1f else 0f)
            shader.setFloatUniform("rgbSplitIntensity", rgbSplitIntensity)
            shader.setFloatUniform("realRandom", Random(seed = timeValue.toInt()).nextFloat())

            renderEffect = if (isEnabled) {
                RenderEffect
                    .createRuntimeShaderEffect(shader, "image")
                    .asComposeRenderEffect()
            } else {
                null
            }
        }
    )
}
