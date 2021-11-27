package org.aristophanes.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.aristophanes.Filter

@Composable
fun RegularAppBar(searchActive: MutableState<Boolean>) {
    TopAppBar(
        title = { Text(text = "Aristophanes", fontSize = 18.sp) },
        actions = {
            IconButton(onClick = { searchActive.value = true }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}

@Composable
fun SearchAppBar(searchActive: MutableState<Boolean>, filterState: MutableState<Filter>) {
    var filter by filterState
    TopAppBar {
        IconButton(onClick = {
            searchActive.value = false
            filter = filter.copy(searchText = "")
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        TextField(
            value = filter.searchText,
            onValueChange = { filter = filter.copy(searchText = it) },
            label = { Text(text = "Suchen...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
    }
}