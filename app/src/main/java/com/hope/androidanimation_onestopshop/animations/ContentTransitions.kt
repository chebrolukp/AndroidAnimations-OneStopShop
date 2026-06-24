package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Category2ContentTransitions() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Shared Element Transition
        Text("Shared Element Transition", style = MaterialTheme.typography.titleMedium)
        var showDetails by remember { mutableStateOf(false) }
        SharedTransitionLayout {
            AnimatedContent(
                targetState = showDetails,
                label = "shared_element",
                modifier = Modifier.fillMaxWidth().height(150.dp)
            ) { isDetail ->
                if (!isDetail) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray, MaterialTheme.shapes.medium)
                            .clickable { showDetails = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "box"),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .size(40.dp)
                                .background(Color.Red, CircleShape)
                        )
                        Text("Click for Detail", modifier = Modifier.padding(top = 60.dp))
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray, MaterialTheme.shapes.medium)
                            .clickable { showDetails = false },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "box"),
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .size(100.dp)
                                .background(Color.Red, CircleShape)
                        )
                        Text("Click to go Back", color = Color.White, modifier = Modifier.padding(top = 110.dp))
                    }
                }
            }
        }

        HorizontalDivider()

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

        HorizontalDivider()

        // Reveal transition with skipToLookaheadPosition()
        Text("Reveal Transition (skipToLookaheadPosition)", style = MaterialTheme.typography.titleMedium)
        var isExpanded by remember { mutableStateOf(false) }

        SharedTransitionLayout {
            AnimatedContent(
                targetState = isExpanded,
                label = "reveal_transition",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) { targetExpanded ->
                if (!targetExpanded) {
                    Box(
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(key = "reveal_card"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                            .background(Color.LightGray, MaterialTheme.shapes.medium)
                            .size(100.dp)
                            .clickable { isExpanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tap to Expand")
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .sharedBounds(
                                rememberSharedContentState(key = "reveal_card"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                            .background(Color.LightGray, MaterialTheme.shapes.medium)
                            .fillMaxSize()
                            .clickable { isExpanded = false }
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color.DarkGray, MaterialTheme.shapes.small)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "This text stays pinned to its final position using skipToLookaheadPosition() while the container expands.",
                            modifier = Modifier.skipToLookaheadPosition(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        HorizontalDivider()

        // Velocity-based transition
        Text("Velocity Handoff (prepareTransitionWithInitialVelocity)", style = MaterialTheme.typography.titleMedium)
        var showDetailsVelocity by remember { mutableStateOf(false) }

        SharedTransitionLayout {
            val sharedState = rememberSharedContentState(key = "velocity_box")
            AnimatedContent(
                targetState = showDetailsVelocity,
                label = "velocity_transition",
                modifier = Modifier.fillMaxWidth().height(150.dp)
            ) { isDetail ->
                if (!isDetail) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray, MaterialTheme.shapes.medium),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .sharedElement(
                                    sharedState,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .size(50.dp)
                                .background(Color.Blue, CircleShape)
                                .draggable(
                                    orientation = Orientation.Horizontal,
                                    state = rememberDraggableState { /* No-op for demo */ },
                                    onDragStopped = { velocity ->
                                        // Note: In 1.10+, you can pass the actual velocity
                                        // sharedState.prepareTransitionWithInitialVelocity(Offset(velocity, 0f))
                                        showDetailsVelocity = true
                                    }
                                )
                        )
                        Text("Swipe circle right", modifier = Modifier.padding(top = 60.dp), style = MaterialTheme.typography.bodySmall)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray, MaterialTheme.shapes.medium)
                            .clickable { showDetailsVelocity = false },
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .sharedElement(
                                    sharedState,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                                .size(80.dp)
                                .background(Color.Blue, CircleShape)
                                .padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
