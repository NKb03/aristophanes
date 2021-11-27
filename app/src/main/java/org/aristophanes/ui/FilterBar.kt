package org.aristophanes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aristophanes.EventType
import org.aristophanes.Filter
import org.aristophanes.UserData

@Composable
fun FilterBar(
    filterState: MutableState<Filter>,
    data: UserData
) {
    var filter by filterState
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(5.dp)) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Nur Favoriten",
                tint = if (filter.onlyFavorites) Color.Magenta else defaultTint(),
                modifier = Modifier
                    .size(26.dp)
                    .clickable {
                        filter = filter.copy(onlyFavorites = !filter.onlyFavorites)
                    }
            )
        }
        Spacer(Modifier.width(7.dp))
        for (type in EventType.values()) {
            val color =
                if (type in filter.types) Color.Magenta
                else MaterialTheme.colors.surface
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = color,
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 5.dp)
                    .clickable {
                        var types = filter.types + type
                        if (types == filter.types) types = types - type
                        if (types.size == EventType.values().size) types = emptySet()
                        filter = filter.copy(types = types)
                    }
            ) {
                Box(Modifier.padding(5.dp)) {
                    Text(
                        text = type.name,
                        fontSize = 12.sp,
                    )
                }
            }
        }
        Spacer(Modifier.width(7.dp))
        for (house in data.houses) {
            val color =
                if (filter.house == house) Color.Magenta
                else MaterialTheme.colors.surface
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = color,
                modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 5.dp)
                    .clickable {
                        filter = if (filter.house == house) filter.copy(house = null)
                        else filter.copy(house = house)
                    }
            ) {
                Box(Modifier.padding(5.dp)) {
                    Text(
                        text = house.name,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}