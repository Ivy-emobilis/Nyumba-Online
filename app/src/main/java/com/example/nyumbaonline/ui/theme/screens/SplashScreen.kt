package com.example.nyumbaonline.ui.theme.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyumbaonline.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToNext: () -> Unit) {
    val splashScreenDuration = 3000L

    // Color palette matching the chatroom design
    val creamBackground = Color(0xFFF5F1EC)
    val warmBrown = Color(0xFF8B5A3C)
    val lightPink = Color(0xFFE8D5D5)
    val softPurple = Color(0xFFB49BC8)

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    // Logo animations
    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        ), label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = EaseOutCubic
        ), label = "logoAlpha"
    )

    // Text animations
    val textAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 1000,
            easing = EaseOutCubic
        ), label = "textAlpha"
    )

    val textOffset by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 30.dp,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 1000,
            easing = EaseOutCubic
        ), label = "textOffset"
    )

    // Subtle floating animation for decorative elements
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "floatingOffset"
    )

    // Gentle pulsing for accent elements
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "pulseAlpha"
    )

    // Start animations and navigation
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(splashScreenDuration)
        onNavigateToNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(creamBackground),
        contentAlignment = Alignment.Center
    ) {
        // Subtle decorative circles in background
        Box(
            modifier = Modifier
                .offset(x = (-120).dp, y = (-200).dp)
                .size(180.dp)
                .alpha(pulseAlpha * 0.5f)
                .clip(CircleShape)
                .background(lightPink.copy(alpha = 0.3f))
        )

        Box(
            modifier = Modifier
                .offset(x = 100.dp, y = 180.dp)
                .size(120.dp)
                .alpha(pulseAlpha * 0.6f)
                .clip(CircleShape)
                .background(softPurple.copy(alpha = 0.25f))
        )

        Box(
            modifier = Modifier
                .offset(x = (-80).dp, y = 220.dp)
                .size(80.dp)
                .alpha(pulseAlpha * 0.4f)
                .clip(CircleShape)
                .background(warmBrown.copy(alpha = 0.15f))
        )

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.offset(y = floatingOffset.dp)
        ) {
            // Logo container with warm styling
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .scale(logoScale)
                    .alpha(logoAlpha)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Color.White.copy(alpha = 0.9f)
                    )
                    .graphicsLayer {
                        shadowElevation = 12.dp.toPx()
                        spotShadowColor = warmBrown.copy(alpha = 0.2f)
                        ambientShadowColor = warmBrown.copy(alpha = 0.1f)
                    }
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                // House icon placeholder (since we can't see the actual logo)
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    lightPink,
                                    warmBrown.copy(alpha = 0.7f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Simple house icon representation
                    Text(
                        text = "🏠",
                        fontSize = 48.sp,
                        modifier = Modifier.alpha(0.8f)
                    )
                }

                /* Original logo implementation - uncomment when you have the logo resource
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Fit
                )
                */
            }

            Spacer(modifier = Modifier.height(48.dp))

            // App name with warm styling
            Text(
                text = "Nyumba Online",
                color = warmBrown,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.5.sp,
                modifier = Modifier
                    .alpha(textAlpha)
                    .offset(y = textOffset)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your Home, Your Future",
                color = warmBrown.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .alpha(textAlpha)
                    .offset(y = textOffset)
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Warm-themed loading indicator
            Box(
                modifier = Modifier
                    .alpha(textAlpha)
            ) {
                WarmLoadingDots()
            }
        }

        // Subtle accent elements in corners
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-24).dp, y = 60.dp)
                .size(12.dp)
                .alpha(pulseAlpha * 0.6f)
                .clip(CircleShape)
                .background(softPurple)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 40.dp, y = (-100).dp)
                .size(8.dp)
                .alpha(pulseAlpha * 0.5f)
                .clip(CircleShape)
                .background(lightPink)
        )
    }
}

@Composable
fun WarmLoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dotsTransition")
    val warmBrown = Color(0xFF8B5A3C)
    val lightPink = Color(0xFFE8D5D5)

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 800,
                        delayMillis = index * 200,
                        easing = EaseInOutCubic
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = "dotScale$index"
            )

            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 800,
                        delayMillis = index * 200,
                        easing = EaseInOutCubic
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = "dotAlpha$index"
            )

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .scale(scale)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(
                        if (index % 2 == 0) warmBrown else lightPink
                    )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(onNavigateToNext = {})
    }
}