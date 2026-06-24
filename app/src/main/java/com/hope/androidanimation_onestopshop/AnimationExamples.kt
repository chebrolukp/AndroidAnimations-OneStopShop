package com.hope.androidanimation_onestopshop

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimationDashboard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text("Compose Animation Showcase", style = MaterialTheme.typography.headlineMedium)

        Category1ModifierAnimations()
        HorizontalDivider()
        Category2ContentTransitions()
        HorizontalDivider()
        Category3ValueAnimations()
    }
}

@Composable
fun Category1ModifierAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("1. Modifier Animations", style = MaterialTheme.typography.titleLarge)
        
        // animateContentSize()
        var expanded by remember { mutableStateOf(value = false) }
        Text("animateContentSize()", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .animateContentSize()
                .clickable { expanded = !expanded }
                .padding(8.dp),
        ) {
            Text(
                if (expanded) "This is a much longer piece of text that will cause the box to expand when clicked. Click again to shrink it back down to its original size."
                else "Click to expand me!",
            )
        }

        // Other modifier: graphicsLayer for rotation
        var rotated by remember { mutableStateOf(value = false) }
        val rotation by animateFloatAsState(if (rotated) 360f else 0f, label = "rotation")
        Text("Modifier.graphicsLayer (Rotation)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { rotated = !rotated }) {
            Text("Rotate Icon")
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer(rotationZ = rotation)
                .background(Color.Magenta),
            contentAlignment = Alignment.Center,
        ) {
            Text("↺", color = Color.White, fontSize = 24.sp)
        }

        // AnimatedVisibility (Another common "modifier-like" high level)
        var visible by remember { mutableStateOf(value = true) }
        Text("AnimatedVisibility", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Hide" else "Show")
        }
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .size(100.dp, 50.dp)
                    .background(Color.Yellow),
            ) {
                Text("I'm Visible!", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun Category2ContentTransitions() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("2. Content Transitions", style = MaterialTheme.typography.titleLarge)

        // AnimatedContent
        var state by remember { mutableIntStateOf(0) }
        Text("AnimatedContent", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { state = (state + 1) % 3 }) {
            Text("Next Content")
        }
        AnimatedContent(
            targetState = state,
            label = "animatedContent",
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
            }
        ) { targetState ->
            when (targetState) {
                0 -> Text("Initial State", modifier = Modifier.padding(8.dp))
                1 -> CircularProgressIndicator()
                2 -> Text("Success!", color = Color.Green, modifier = Modifier.padding(8.dp))
            }
        }

        // Crossfade
        var isFirst by remember { mutableStateOf(value = true) }
        Text("Crossfade", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isFirst = !isFirst }) {
            Text("Toggle Crossfade")
        }
        Crossfade(targetState = isFirst, label = "crossfade") { screen ->
            if (screen) {
                Box(Modifier.size(100.dp, 40.dp).background(Color.Blue)) {
                    Text("Blue View", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            } else {
                Box(Modifier.size(100.dp, 40.dp).background(Color.Red)) {
                    Text("Red View", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun Category3ValueAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("3. Value Animations (animate*AsState)", style = MaterialTheme.typography.titleLarge)

        // animateDpAsState
        var moved by remember { mutableStateOf(value = false) }
        val offset by animateDpAsState(if (moved) 100.dp else 0.dp, label = "offset")
        Text("animateDpAsState (Offset)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { moved = !moved }) {
            Text("Move Box")
        }
        Box(
            modifier = Modifier
                .offset(x = offset)
                .size(50.dp)
                .background(Color.Cyan)
        )

        // animateColorAsState
        var isToggled by remember { mutableStateOf(value = false) }
        val color by animateColorAsState(if (isToggled) Color.Red else Color.Green, label = "color")
        Text("animateColorAsState", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isToggled = !isToggled }) {
            Text("Change Color")
        }
        Box(
            modifier = Modifier
                .size(100.dp, 50.dp)
                .background(color)
        )
    }
}
