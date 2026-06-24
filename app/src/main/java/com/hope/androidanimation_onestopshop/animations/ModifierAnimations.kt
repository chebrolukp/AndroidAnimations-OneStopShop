package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun Category1ModifierAnimations() {
    val coroutineScope = rememberCoroutineScope()
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Gesture-based with Animatable
        Text("Gesture-based (Draggable + Animatable)", style = MaterialTheme.typography.titleMedium)
        val offsetX = remember { androidx.compose.animation.core.Animatable(0f) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.LightGray, MaterialTheme.shapes.small)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offsetX.value.dp)
                    .size(100.dp, 60.dp)
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                offsetX.snapTo(offsetX.value + delta)
                            }
                        },
                        onDragStopped = {
                            coroutineScope.launch {
                                offsetX.animateTo(0f, spring())
                            }
                        }
                    )
            )
        }

        HorizontalDivider()

        // animateContentSize()
        var expanded by remember { mutableStateOf(value = false) }
        Text("animateContentSize()", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                .animateContentSize()
                .clickable { expanded = !expanded }
                .padding(16.dp),
        ) {
            Text(
                if (expanded) "This is a much longer piece of text that will cause the box to expand when clicked. Click again to shrink it back down to its original size."
                else "Click to expand me!",
            )
        }

        HorizontalDivider()

        // animateBounds (Stable in 1.8+)
        Text("animateBounds (LookaheadScope)", style = MaterialTheme.typography.titleMedium)
        var isWide by remember { mutableStateOf(value = false) }
        LookaheadScope {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                    .clickable { isWide = !isWide }
            ) {
                Box(
                    modifier = Modifier
                        .animateBounds(this@LookaheadScope)
                        .padding(8.dp)
                        .size(if (isWide) 150.dp else 50.dp, 50.dp)
                        .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                )
            }
        }

        HorizontalDivider()

        // graphicsLayer for rotation
        var rotated by remember { mutableStateOf(value = false) }
        val rotation by animateFloatAsState(if (rotated) 360f else 0f, label = "rotation")
        Text("Modifier.graphicsLayer (Rotation)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { rotated = !rotated }) {
            Text("Rotate Item")
        }
        Box(
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer(rotationZ = rotation)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
        ) {
            Text("↺", color = MaterialTheme.colorScheme.onPrimary, fontSize = 24.sp)
        }

        HorizontalDivider()

        // graphicsLayer for 3D effects
        var flipped by remember { mutableStateOf(false) }
        val animatedRotationY by animateFloatAsState(if (flipped) 180f else 0f, label = "rotationY")
        Text("Modifier.graphicsLayer (3D Flip)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(100.dp, 60.dp)
                .graphicsLayer {
                    rotationY = animatedRotationY
                    cameraDistance = 12f * density
                }
                .background(if (animatedRotationY > 90f) Color.Red else Color.Blue, MaterialTheme.shapes.medium)
                .clickable { flipped = !flipped },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (animatedRotationY > 90f) "Back" else "Front",
                color = Color.White,
                modifier = Modifier.graphicsLayer { rotationY = if (flipped) 180f else 0f }
            )
        }

        HorizontalDivider()

        // AnimatedVisibility
        var visible by remember { mutableStateOf(value = true) }
        Text("AnimatedVisibility", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Hide" else "Show")
        }
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.Yellow, shape = MaterialTheme.shapes.medium),
            ) {
                Text("I'm Visible!", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
