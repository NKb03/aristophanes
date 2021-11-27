package org.aristophanes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aristophanes.Event
import org.aristophanes.Filter
import org.aristophanes.R
import org.aristophanes.UserData
import org.aristophanes.WEEKDAYS
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DisplayEvent(
    data: UserData,
    ev: Event,
    filterState: MutableState<Filter>,
    searchActive: MutableState<Boolean>
) {
    val uriHandler = LocalUriHandler.current
    var filter by filterState
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .clickable {
                uriHandler.openUri(ev.website)
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 3.dp, vertical = 2.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val weekday = WEEKDAYS[ev.date.get(Calendar.DAY_OF_WEEK) - 1]
                        Text(text = weekday, fontSize = 24.sp)
                    }
                    Column(verticalArrangement = Arrangement.Center) {
                        val date =
                            SimpleDateFormat("dd.mm.yyyy", Locale.GERMAN).format(ev.date.time)
                        Text(text = date, fontSize = 11.sp)
                        Text(text = ev.time, fontSize = 11.sp)
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(0.22f), contentAlignment = Alignment.Center) {
                    Text(
                        text = ev.house.name,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            uriHandler.openUri(ev.house.website)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(text = ev.title.chunked(20).joinToString("\n"), fontSize = 18.sp)
                Text(text = ev.description.chunked(40).joinToString("\n"), fontSize = 12.sp)
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Nach Aufführungen suchen",
                        modifier = Modifier.clickable {
                            filter = filter.copy(searchText = ev.title)
                            searchActive.value = true
                        }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_money),
                        tint = if (ev.ticketsURL == null) Color.Gray else defaultTint(),
                        contentDescription = "Tickets kaufen",
                        modifier = Modifier.clickable(enabled = ev.ticketsURL != null) {
                            uriHandler.openUri(ev.ticketsURL!!)
                        }
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Zu Favoriten hinzufügen",
                        tint = if (ev.title in data.favorites) Color.Magenta else defaultTint(),
                        modifier = Modifier.clickable {
                            if (ev.title in data.favorites) data.favorites.remove(ev.title)
                            else data.favorites.add(ev.title)
                        }
                    )
                }
            }
        }
    }
}