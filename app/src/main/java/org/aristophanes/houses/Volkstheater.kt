package org.aristophanes.houses

import org.aristophanes.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object Volkstheater : House(
    "Volkstheater",
    "https://muenchner-volkstheater.de",
    setOf(EventType.Play),
    Address("Tumblingerstraße 29", "80337", "München", "Germany"),
    "089 5234655"
) {
    override fun getEvents(): List<Event> {
        val doc = Jsoup.parse(URL(schedule), 15000)
        return extractEvents(doc)
    }

    private fun extractEvents(doc: Document): List<Event> {
        return doc
            .select("div.schedule--month-wrapper")
            .flatMap { monthWrapper -> extractEvents(monthWrapper) }
    }

    private fun extractEvents(monthWrapper: Element): List<Event> {
        val monthName = monthWrapper.selectFirst("div.schedule--month-title")!!.ownText()
        val month = MONTHS.indexOf(monthName) + 1
        return monthWrapper.select("div.schedule--day-wrapper").flatMap { dayWrapper ->
            val date = extractDate(dayWrapper, month)
            dayWrapper.select("div.schedule--day-content").map { event ->
                extractEvent(event, date)
            }
        }
    }

    private fun extractDate(
        dayWrapper: Element,
        month: Int,
    ): Calendar {
        val day = dayWrapper.selectFirst("span.schedule--day")!!.ownText().toInt()
        val date = Calendar.getInstance()
        date.set(year, month, day)
        if (date < today) date.set(Calendar.YEAR, year + 1)
        return date
    }

    private fun extractEvent(eventContent: Element, date: Calendar): Event {
        val time = eventContent.selectFirst("div.field-event-time")!!.text()
        val titleLink = eventContent.selectFirst("span.schedule--title")!!.selectFirst("a")!!
        val url = "$rootUrl/${titleLink.attr("href")}"
        val title = titleLink.ownText()
        val description = eventContent.selectFirst("span.field-subtitle")?.text().orEmpty()
        val ticketsUrl = eventContent
            .selectFirst("div.field-event-ticket-link")
            ?.child(0)?.child(0)?.attr("href").orEmpty()
        val ticketLink = ticketsUrl.takeIf { it.isNotBlank() }
        val type = EventType.Play
        return Event(title, type, description, this, date, time, ticketLink, url)
    }


    private const val rootUrl = "https://www.muenchner-volkstheater.de"
    private const val schedule = "$rootUrl/programm/spielplan"

    private val today = Calendar.getInstance()
    private val year = today.get(Calendar.YEAR)

    @JvmStatic
    fun main(args: Array<String>) {
        val lastDate = Calendar.getInstance()
        lastDate.set(2022, 4, 1)
        println(getEvents())
    }
}