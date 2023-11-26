package com.nasywa.freshfusion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nasywa.freshfusion.ui.theme.FreshFusionTheme
import com.nasywa.freshfusion.ui.theme.background1
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreshFusionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    var isLoading by remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        delay(splashTime)
                        isLoading = false
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    SplashLogo(isLoading)
                }
            }
        }
    }

    @Composable
    fun SplashLogo(isLoading: Boolean, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(background1)
        ) {
            Image(
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(paddingValues = PaddingValues(82.dp)),
                painter = painterResource(R.drawable.freshfusion_logo),
                contentDescription = "Splash Screen Logo"
            )
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(18.dp),
                    color = Color.Green
                )
            }
        }
    }
}
