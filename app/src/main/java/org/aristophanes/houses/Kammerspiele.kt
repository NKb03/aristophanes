package org.aristophanes.houses

import org.aristophanes.Address
import org.aristophanes.Event
import org.aristophanes.EventType
import org.aristophanes.House

object Kammerspiele : House(
    "Kammerspiele",
    "https://muenchner-kammerspiele.de",
    setOf(EventType.Play),
    Address("Maximilianstraße 26-28", "", "München", "Germany"),
    "089 23396600"
) {
    override fun getEvents(): List<Event> {
        return emptyList()
    }
}