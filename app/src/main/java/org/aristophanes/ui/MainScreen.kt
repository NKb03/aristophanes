package org.aristophanes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.aristophanes.Event
import org.aristophanes.Filter
import org.aristophanes.UserData

@Composable
fun MainScreen(data: UserData, events: List<Event>) {
    val filterState = remember { mutableStateOf(Filter(null, emptySet(), false, "")) }
    val searchActiveState = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            if (searchActiveState.value) SearchAppBar(searchActiveState, filterState)
            else RegularAppBar(searchActiveState)
        }
    ) {
        Column {
            FilterBar(filterState, data)
            LazyColumn {
                val filter = filterState.value
                val filtered = events
                    .filter { ev -> ev.title.startsWith(filter.searchText, ignoreCase = true) }
                    .filter { ev -> filter.types.isEmpty() || ev.type in filter.types }
                    .filter { ev -> filter.house == null || ev.house == filter.house }
                    .filter { ev -> !filter.onlyFavorites || ev.title in data.favorites }
                items(filtered) { ev ->
                    DisplayEvent(data, ev, filterState, searchActiveState)
                }
            }
        }
    }
}

