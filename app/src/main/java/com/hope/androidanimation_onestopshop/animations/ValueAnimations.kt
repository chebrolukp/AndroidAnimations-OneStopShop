package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Category3ValueAnimations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // animateDpAsState
        var moved by remember { mutableStateOf(value = false) }
        val offset by animateDpAsState(if (moved) 150.dp else 0.dp, label = "offset")
        Text("animateDpAsState (Offset)", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { moved = !moved }) {
            Text("Move Box")
        }
        Box(
            modifier = Modifier
                .offset(x = offset)
                .size(60.dp)
                .background(Color.Cyan, MaterialTheme.shapes.medium),
        )

        HorizontalDivider()

        // animateColorAsState
        var isToggled by remember { mutableStateOf(value = false) }
        val color by animateColorAsState(if (isToggled) Color.Red else Color.Green, label = "color")
        Text("animateColorAsState", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isToggled = !isToggled }) {
            Text("Change Color")
        }
        Box(
            modifier = Modifier
                .size(150.dp, 60.dp)
                .background(color, MaterialTheme.shapes.medium),
        )
    }
}
