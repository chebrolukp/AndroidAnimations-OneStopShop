package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Category4AdvancedAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Infinite Transition
        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "scale",
        )
        Text("Infinite Transition (Pulsing)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(80.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale)
                .background(Color.Magenta, shape = MaterialTheme.shapes.extraLarge),
        )

        HorizontalDivider()

        // updateTransition
        var isExpanded by remember { mutableStateOf(value = false) }
        val transition = updateTransition(targetState = isExpanded, label = "multiTransition")
        
        val transitionColor by transition.animateColor(label = "color") { state ->
            if (state) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        }
        val transitionSize by transition.animateDp(label = "size") { state ->
            if (state) 150.dp else 80.dp
        }

        Text("updateTransition (Multi-property)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isExpanded = !isExpanded }) {
            Text("Toggle State")
        }
        Box(
            modifier = Modifier
                .size(transitionSize)
                .background(transitionColor, MaterialTheme.shapes.medium),
        )
    }
}
