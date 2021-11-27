package org.aristophanes

import java.util.*

enum class EventType {
    Concert, Play, Opera;
}

data class Address(
    val streetAndNr: String,
    val zipCode: String,
    val city: String,
    val country: String
)

abstract class House(
    val name: String,
    val website: String,
    val eventTypes: Set<EventType>,
    val address: Address,
    val telephone: String
) {
    abstract fun getEvents(): List<Event>

    override fun toString(): String = name
}

data class Event(
    val title: String, val type: EventType, val description: String,
    val house: House, val date: Calendar, val time: String,
    val ticketsURL: String?, val website: String
)


data class Filter(
    val house: House?,
    val types: Set<EventType>,
    val onlyFavorites: Boolean,
    val searchText: String
)

val MONTHS = listOf(
    "Januar", "Februar", "März", "April", "Mai", "Juni",
    "Juli", "August", "September", "Oktober", "November", "Dezember"
)

val WEEKDAYS = listOf(
    "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"
)