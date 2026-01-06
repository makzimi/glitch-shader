package dev.maxkach.glitchsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.maxkach.glitchsample.screen.GlitchScreen
import dev.maxkach.glitchsample.ui.theme.ShadersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShadersTheme {
                Surface {
                    GlitchShaderSampleApp(
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun GlitchShaderSampleApp(
    onBackPressed: () -> Unit = { }
) {
    GlitchScreen(
        modifier = Modifier.fillMaxSize(),
        onBackPressed = onBackPressed
    )
}
