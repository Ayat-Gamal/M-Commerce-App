package com.example.m_commerce.core.utils.extentions

import java.text.SimpleDateFormat
import java.util.Locale


fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd.MMM.yyyy", Locale.ENGLISH)

    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}