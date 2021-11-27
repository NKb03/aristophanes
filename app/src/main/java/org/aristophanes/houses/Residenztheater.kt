package org.aristophanes.houses

import org.aristophanes.Address
import org.aristophanes.Event
import org.aristophanes.EventType
import org.aristophanes.House

object Residenztheater : House(
    "Residenztheater",
    "https://residenztheater.de",
    setOf(EventType.Play),
    Address("Max-Joseph-Platz 1", "80539", "München", "Germany"),
    "089 21851940"
) {
    override fun getEvents(): List<Event> {
        return emptyList()
    }
}