package com.hope.androidanimation_onestopshop.animations

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Category6LayoutAnimations() {
    var items by remember { mutableStateOf((1..5).toList()) }
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Lazy List Item Animations (Modifier.animateItem())", style = MaterialTheme.typography.titleMedium)
        Text("Click an item to remove it. Watch the others shift.", style = MaterialTheme.typography.bodySmall)
        
        Button(onClick = { items = (1..5).toList() }) {
            Text("Reset List")
        }

        LazyColumn(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 8.dp),
        ) {
            items(items, key = { it }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(
                            fadeInSpec = tween(500),
                            fadeOutSpec = tween(500),
                            placementSpec = tween(500)
                        )
                        .clickable { items = items.filter { it != item } },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Item $item", modifier = Modifier.weight(1f))
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}
