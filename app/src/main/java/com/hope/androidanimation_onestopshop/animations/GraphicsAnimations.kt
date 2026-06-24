package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun Category7GraphicsAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        // Canvas Animation
        Text("Canvas Animation (DrawScope)", style = MaterialTheme.typography.titleMedium)
        val infiniteTransition = rememberInfiniteTransition(label = "canvas")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
            label = "rotation",
        )
        
        Canvas(modifier = Modifier.size(100.dp)) {
            withTransform(
                transformBlock = {
                    rotate(rotation)
                },
            ) {
                drawRect(
                    color = Color.Blue,
                    topLeft = Offset(25.dp.toPx(), 25.dp.toPx()),
                    size = androidx.compose.ui.geometry.Size(50.dp.toPx(), 50.dp.toPx()),
                )
            }
            drawCircle(
                color = Color.Red,
                radius = 10.dp.toPx(),
                center = Offset(50.dp.toPx(), 50.dp.toPx()),
            )
        }

        HorizontalDivider()

        Text("AnimatedVectorDrawable (AVD)", style = MaterialTheme.typography.titleMedium)
        Text("AVDs are typically used for icon animations. Implementation requires an XML resource.", 
            style = MaterialTheme.typography.bodySmall, 
            color = MaterialTheme.colorScheme.outline)
    }
}
