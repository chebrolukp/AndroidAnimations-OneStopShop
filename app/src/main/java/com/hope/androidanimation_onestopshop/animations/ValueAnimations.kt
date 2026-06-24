package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Category3ValueAnimations() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        // animateDpAsState
        var isMoved by remember { mutableStateOf(value = false) }
        val dpOffset by animateDpAsState(if (isMoved) 100.dp else 0.dp, label = "dpOffset")
        Text("animateDpAsState (Elevation/Padding/Offset)", style = MaterialTheme.typography.titleMedium)
        Card(
            modifier = Modifier
                .padding(start = dpOffset)
                .size(100.dp, 50.dp)
                .clickable { isMoved = !isMoved },
            elevation = CardDefaults.cardElevation(defaultElevation = if (isMoved) 12.dp else 2.dp),
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tap Me", style = MaterialTheme.typography.labelSmall)
            }
        }

        HorizontalDivider()

        // animateFloatAsState
        var isRotated by remember { mutableStateOf(false) }
        val rotation by animateFloatAsState(if (isRotated) 45f else 0f, label = "rotation")
        val alpha by animateFloatAsState(if (isRotated) 0.5f else 1f, label = "alpha")
        Text("animateFloatAsState (Alpha/Rotation/Scale)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer(rotationZ = rotation, alpha = alpha)
                .background(Color.Red)
                .clickable { isRotated = !isRotated }
        )

        HorizontalDivider()

        // animateColorAsState
        var isToggled by remember { mutableStateOf(false) }
        val color by animateColorAsState(if (isToggled) Color.Blue else Color.Magenta, label = "color")
        Text("animateColorAsState", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(150.dp, 50.dp)
                .background(color, RoundedCornerShape(8.dp))
                .clickable { isToggled = !isToggled }
        )

        HorizontalDivider()

        // animateIntAsState
        var countUp by remember { mutableStateOf(false) }
        val animatedInt by animateIntAsState(if (countUp) 100 else 0, label = "int")
        Text("animateIntAsState (Counters/Text)", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Value: $animatedInt",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
            modifier = Modifier.clickable { countUp = !countUp }
        )

        HorizontalDivider()

        // animateOffsetAsState
        var isOffset by remember { mutableStateOf(false) }
        val offset by animateOffsetAsState(
            if (isOffset) Offset(100f, 50f) else Offset.Zero,
            label = "offset"
        )
        Text("animateOffsetAsState (Canvas/Graphics)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(100.dp, 60.dp)
                .background(Color.LightGray)
                .clickable { isOffset = !isOffset }
        ) {
            Box(
                modifier = Modifier
                    .offset(x = offset.x.dp, y = offset.y.dp)
                    .size(20.dp)
                    .background(Color.Black)
            )
        }

        HorizontalDivider()

        // animateIntOffsetAsState
        var isIntOffset by remember { mutableStateOf(false) }
        val intOffset by animateIntOffsetAsState(
            if (isIntOffset) IntOffset(200, 20) else IntOffset.Zero,
            label = "intOffset"
        )
        Text("animateIntOffsetAsState (Pixel-perfect Layout)", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Yellow.copy(alpha = 0.3f))
                .clickable { isIntOffset = !isIntOffset }
        ) {
            Box(
                modifier = Modifier
                    .offset { intOffset }
                    .size(40.dp)
                    .background(Color.DarkGray)
            )
        }

        HorizontalDivider()

        // animateSizeAsState
        var isLarge by remember { mutableStateOf(false) }
        val size by animateSizeAsState(
            if (isLarge) Size(200f, 100f) else Size(100f, 50f),
            label = "size"
        )
        Text("animateSizeAsState", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(width = size.width.dp, height = size.height.dp)
                .background(Color.Green)
                .clickable { isLarge = !isLarge }
        )

        HorizontalDivider()

        // animateIntSizeAsState
        var isIntSizeLarge by remember { mutableStateOf(false) }
        val intSize by animateIntSizeAsState(
            if (isIntSizeLarge) IntSize(250, 80) else IntSize(100, 40),
            label = "intSize"
        )
        Text("animateIntSizeAsState", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(width = intSize.width.dp, height = intSize.height.dp)
                .background(Color.Gray)
                .clickable { isIntSizeLarge = !isIntSizeLarge }
        )

        HorizontalDivider()

        // animateRectAsState
        var isRectToggled by remember { mutableStateOf(false) }
        val rect by animateRectAsState(
            if (isRectToggled) Rect(10f, 10f, 150f, 60f) else Rect(0f, 0f, 50f, 30f),
            label = "rect"
        )
        Text("animateRectAsState", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(Color.Cyan.copy(alpha = 0.1f))
                .clickable { isRectToggled = !isRectToggled }
        ) {
            Box(
                modifier = Modifier
                    .offset(rect.left.dp, rect.top.dp)
                    .size(rect.width.dp, rect.height.dp)
                    .background(Color.Cyan)
            )
        }
    }
}
