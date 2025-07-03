package com.example.m_commerce.core.utils

fun containsPositiveNumber(input: String): Boolean {
    val regex = Regex("""\d+(\.\d+)?""")  // matches numbers like 10, 10.5, 0.00
    val number = regex.findAll(input)
        .mapNotNull { it.value.toFloatOrNull() }
        .firstOrNull { it > 0 }

    return number != null
}