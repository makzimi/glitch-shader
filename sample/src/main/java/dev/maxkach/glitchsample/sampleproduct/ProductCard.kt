package dev.maxkach.glitchsample.sampleproduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.maxkach.glitchsample.R
import dev.maxkach.shaders.glitch.glitchShader

@Composable
fun ProductCard(
    state: ProductCardState,
    stepTitle: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    defaultSlices: Float = 16f,
    defaultFrameDuration: Int = 16,
    defaultNoiseIntensity: Float = 1f,
    defaultColorBarsEnabled: Boolean = false,
    defaultRgbSplitIntensity: Float = 1f,
) {
    var glitchIntensity by remember { mutableFloatStateOf(1f) }
    var slicesCount by remember { mutableFloatStateOf(defaultSlices) }
    var frameDurationMs by remember { mutableIntStateOf(defaultFrameDuration) }
    var noiseIntensity by remember { mutableFloatStateOf(defaultNoiseIntensity) }
    var colorBarsEnabled by remember { mutableStateOf(defaultColorBarsEnabled) }
    var rgbSplitIntensity by remember { mutableFloatStateOf(defaultRgbSplitIntensity) }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(stepTitle, onBackPressed) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        top = innerPadding.calculateTopPadding() + 12.dp,
                        bottom = innerPadding.calculateBottomPadding() + 12.dp,
                    ),
            ) {
                Image(
                    painter = painterResource(state.image.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .glitchShader(
                            intensity = glitchIntensity,
                            slices = slicesCount,
                            frameDuration = frameDurationMs,
                            noiseIntensity = noiseIntensity,
                            colorBarsEnabled = colorBarsEnabled,
                            rgbSplitIntensity = rgbSplitIntensity
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Intensity:",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Slider(
                            value = glitchIntensity,
                            onValueChange = { glitchIntensity = it },
                            valueRange = 0f..2f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%.1f".format(glitchIntensity),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Slices:",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Slider(
                            value = slicesCount,
                            onValueChange = { slicesCount = it },
                            valueRange = 1f..100f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%d".format(slicesCount.toInt()),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Frame:",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Slider(
                            value = frameDurationMs.toFloat(),
                            onValueChange = { frameDurationMs = it.toInt() },
                            valueRange = 16f..600f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%dms".format(frameDurationMs),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Noise:",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Slider(
                            value = noiseIntensity,
                            onValueChange = { noiseIntensity = it },
                            valueRange = 0f..10f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%.1f".format(noiseIntensity),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Color Bars:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = colorBarsEnabled,
                            onCheckedChange = { colorBarsEnabled = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "RGB Split:",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Slider(
                            value = rgbSplitIntensity,
                            onValueChange = { rgbSplitIntensity = it },
                            valueRange = 0f..5f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%.1f".format(rgbSplitIntensity),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            Text(
                text = title,
                modifier = modifier,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
        }
    )
}
