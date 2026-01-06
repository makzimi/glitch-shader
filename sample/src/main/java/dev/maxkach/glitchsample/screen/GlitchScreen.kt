package dev.maxkach.glitchsample.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.maxkach.glitchsample.sampleproduct.ProductCard
import dev.maxkach.glitchsample.sampleproduct.ProductCardStateCreator

@Composable
fun GlitchScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ProductCard(
            state = ProductCardStateCreator.create(),
            stepTitle = "Glitch shader",
            onBackPressed = onBackPressed,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}


