package com.ahmed.banaochattask.core.utils

import com.ahmed.banaochattask.data.models.LastMessagesTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

fun sortLatestMessages(last: List<LastMessagesTime>): List<LastMessagesTime> {
    val defaultTime = "0000-00-00 00:00:00"
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("UTC")
    return last.sortedWith(Comparator { item1, item2 ->
        val time1 = item1.time.ifBlank { defaultTime }
        val time2 = item2.time.ifBlank { defaultTime }
        val date1 = format.parse(time1)!!
        val date2 = format.parse(time2)!!
        return@Comparator when {
            date1.time > date2.time -> -1 // Sort in descending order (latest time first)
            date1.time < date2.time -> 1  // Sort in ascending order (earliest time first)
            else -> 0
        }
    })
}

fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return currentDateTime.format(formatter)
}

fun formattedDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEE, MMM d HH:mm:ss", Locale.getDefault())

    return try {
        val parsedDate = inputFormat.parse(date)
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        e.printStackTrace()
        "Invalid Date"
    }
}