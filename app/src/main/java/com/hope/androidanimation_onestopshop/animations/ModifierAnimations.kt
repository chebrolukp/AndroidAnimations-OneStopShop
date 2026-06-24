package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Category1ModifierAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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

        // animateBounds (Simulated)
        Text("animateBounds (Simulated)", style = MaterialTheme.typography.titleMedium)
        var alignmentLeft by remember { mutableStateOf(true) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                .clickable { alignmentLeft = !alignmentLeft }
        ) {
            val offset by animateDpAsState(if (alignmentLeft) 0.dp else 200.dp, label = "bounds")
            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            )
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
