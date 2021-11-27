package org.aristophanes.houses

import org.aristophanes.Address
import org.aristophanes.Event
import org.aristophanes.EventType
import org.aristophanes.House

object Gaertnerplatz: House(
    "Gärtnerplatztheater",
    "https://gaertnerplatztheater.de",
    setOf(EventType.Play, EventType.Concert, EventType.Opera),
    Address("Gärtnerplatz 3", "80469", "München", "Germany"),
    "089 21851960"
) {
    override fun getEvents(): List<Event> {
        return emptyList()
    }
}