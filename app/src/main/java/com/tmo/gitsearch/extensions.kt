package com.tmo.gitsearch

import com.squareup.moshi.Moshi
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension functions allow us to extend the functionality of a class by adding new functions.
 */

fun String.formatDate(): String {
    val initialFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH)
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    val date = initialFormat.parse(this)
    return format.format(date).toString()
}

inline fun <reified T> Moshi.fromJson(json: String): T = this.adapter(T::class.java).fromJson(json)