package com.example.m_commerce.core.utils.extentions

fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}

fun String.capitalizeFirstLetters(): String {
    return this
        .lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}
