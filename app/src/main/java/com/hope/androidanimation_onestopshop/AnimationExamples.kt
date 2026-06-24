package com.hope.androidanimation_onestopshop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hope.androidanimation_onestopshop.animations.*

enum class AnimationCategory(val title: String) {
    MODIFIER("Modifier Animations"),
    CONTENT_TRANSITION("Content Transitions"),
    VALUE_ANIMATION("Value Animations"),
    LOW_LEVEL("Low-level APIs & Specs"),
    LAYOUT("Layout & List Animations"),
    GRAPHICS("Graphics & Canvas")
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
            AnimationCategory.LOW_LEVEL -> Category5LowLevelAnimations()
            AnimationCategory.LAYOUT -> Category6LayoutAnimations()
            AnimationCategory.GRAPHICS -> Category7GraphicsAnimations()
        }
    }
}
