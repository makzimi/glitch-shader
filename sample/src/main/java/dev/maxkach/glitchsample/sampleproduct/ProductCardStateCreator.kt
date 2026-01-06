package dev.maxkach.glitchsample.sampleproduct

import dev.maxkach.glitchsample.R

object ProductCardStateCreator {
    fun create(
    ): ProductCardState {
        return ProductCardState(
            title = "Cozy Cat Hat",
            image = ProductCardState.ImageState(R.drawable.ic_cat_1),
            description = """"
                Welcome to our exclusive collection of feline headwear! 
                Each hat is designed with your sophisticated cat in mind, 
                offering both comfort and undeniable style. 
                Let your beloved companion express their unique personality with a purr-fectly 
                fitted and fashionable accessory!
                """,
        )
    }
}