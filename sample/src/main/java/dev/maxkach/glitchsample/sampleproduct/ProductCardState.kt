package dev.maxkach.glitchsample.sampleproduct

import androidx.compose.runtime.Immutable

@Immutable
data class ProductCardState(
    val title: String,
    val description: String,
    val image: ImageState,
) {
    data class ImageState(
        val imageRes: Int,
    )
}
