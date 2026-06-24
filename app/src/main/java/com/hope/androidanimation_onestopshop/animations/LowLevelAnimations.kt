package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Category5LowLevelAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        // updateTransition - Multi-property
        Text("updateTransition (Multi-property)", style = MaterialTheme.typography.titleMedium)
        var isExpanded by remember { mutableStateOf(value = false) }
        val transition = updateTransition(targetState = isExpanded, label = "multiTransition")
        
        val transitionColor by transition.animateColor(label = "color") { state ->
            if (state) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        }
        val transitionSize by transition.animateDp(label = "size") { state ->
            if (state) 100.dp else 60.dp
        }

        Button(onClick = { isExpanded = !isExpanded }) {
            Text("Toggle multi-property")
        }
        Box(
            modifier = Modifier
                .size(transitionSize)
                .background(transitionColor, MaterialTheme.shapes.medium),
        )

        HorizontalDivider()

        // Infinite Transition
        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val infiniteScale by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "scale",
        )
        Text("InfiniteTransition (Pulsing)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(60.dp)
                .graphicsLayer(scaleX = infiniteScale, scaleY = infiniteScale)
                .background(Color.Magenta, shape = MaterialTheme.shapes.extraLarge),
        )

        HorizontalDivider()

        // Animatable - Low level control
        Text("Animatable (Low-level Control)", style = MaterialTheme.typography.titleMedium)
        val color = remember { Animatable(initialValue = Color.Gray) }
        LaunchedEffect(Unit) {
            while(true) {
                color.animateTo(targetValue = Color.Green, animationSpec = tween(1000))
                color.animateTo(targetValue = Color.Gray, animationSpec = tween(1000))
            }
        }
        Box(Modifier.size(100.dp, 50.dp).background(color.value, MaterialTheme.shapes.medium))

        HorizontalDivider()

        // animateDecay - Physics based decay
        Text("animateDecay (Physics-based Decay)", style = MaterialTheme.typography.titleMedium)
        val offset = remember { Animatable(initialValue = 0f) }
        val decay = rememberSplineBasedDecay<Float>()
        LaunchedEffect(Unit) {
            while(true) {
                offset.animateDecay(initialVelocity = 2000f, animationSpec = decay) // Start with high velocity
                offset.snapTo(targetValue = 0f)
            }
        }
        Box(Modifier.offset(x = offset.value.dp).size(40.dp).background(Color.Yellow))

        HorizontalDivider()

        // AnimationSpec Comparison
        Text("AnimationSpec Comparison", style = MaterialTheme.typography.titleMedium)
        var trigger by remember { mutableStateOf(value = false) }
        
        // Tween
        val tweenValue by animateDpAsState(
            targetValue = if (trigger) 200.dp else 0.dp,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            label = "tween",
        )
        // Spring
        val springValue by animateDpAsState(
            targetValue = if (trigger) 200.dp else 0.dp,
            animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow),
            label = "spring",
        )
        // Keyframes
        val keyframeValue by animateDpAsState(
            targetValue = if (trigger) 200.dp else 0.dp,
            animationSpec = keyframes {
                durationMillis = 1000
                150.dp at 500 using FastOutLinearInEasing
            },
            label = "keyframes",
        )

        Button(onClick = { trigger = !trigger }) { Text("Animate All") }
        
        Text("Tween (1s, Easing)", style = MaterialTheme.typography.bodySmall)
        Box(Modifier.offset(x = tweenValue).size(40.dp).background(Color.Red))
        
        Text("Spring (Bouncy)", style = MaterialTheme.typography.bodySmall)
        Box(Modifier.offset(x = springValue).size(40.dp).background(Color.Blue))
        
        Text("Keyframes (150dp at 500ms)", style = MaterialTheme.typography.bodySmall)
        Box(Modifier.offset(x = keyframeValue).size(40.dp).background(Color.Green))
    }
}
