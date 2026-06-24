package com.hope.androidanimation_onestopshop

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AnimationCategory(val title: String) {
    MODIFIER("Modifier Animations"),
    CONTENT_TRANSITION("Content Transitions"),
    VALUE_ANIMATION("Value Animations"),
    ADVANCED("Advanced Animations")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationApp() {
    var selectedCategory by remember { mutableStateOf<AnimationCategory?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedCategory?.title ?: "Animation Showcase") },
                navigationIcon = {
                    if (selectedCategory != null) {
                        IconButton(onClick = { selectedCategory = null }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (selectedCategory == null) {
                CategoryList { selectedCategory = it }
            } else {
                CategoryDetail(category = selectedCategory!!)
            }
        }
    }
}

@Composable
fun CategoryList(onCategoryClick: (AnimationCategory) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(AnimationCategory.entries) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategoryClick(category) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Text(
                    text = category.title,
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
fun CategoryDetail(category: AnimationCategory) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        when (category) {
            AnimationCategory.MODIFIER -> Category1ModifierAnimations()
            AnimationCategory.CONTENT_TRANSITION -> Category2ContentTransitions()
            AnimationCategory.VALUE_ANIMATION -> Category3ValueAnimations()
            AnimationCategory.ADVANCED -> Category4AdvancedAnimations()
        }
    }
}

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

@Composable
fun Category2ContentTransitions() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
            },
        ) { targetState ->
            Box(modifier = Modifier.padding(8.dp)) {
                when (targetState) {
                    0 -> Text("Initial State - Stage 0")
                    1 -> CircularProgressIndicator()
                    2 -> Text("Success! - Stage 2", color = Color.Green)
                }
            }
        }

        HorizontalDivider()

        // Crossfade
        var isFirst by remember { mutableStateOf(value = true) }
        Text("Crossfade", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { isFirst = !isFirst }) {
            Text("Toggle Crossfade")
        }
        Crossfade(targetState = isFirst, label = "crossfade") { screen ->
            if (screen) {
                Box(Modifier.size(150.dp, 60.dp).background(Color.Blue, MaterialTheme.shapes.small)) {
                    Text("Blue View", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            } else {
                Box(Modifier.size(150.dp, 60.dp).background(Color.Red, MaterialTheme.shapes.small)) {
                    Text("Red View", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

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
