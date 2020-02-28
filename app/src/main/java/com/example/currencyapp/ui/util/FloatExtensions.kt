package com.example.currencyapp.ui.util

fun Float.format(): String = if (this != 0F) {
    try {
        val formattedString = String.format("%.2f", this)
        val split = formattedString.split('.')
        val firstPart = split[0]
        val secondPart = split[1]
        if (secondPart.isEmpty() || secondPart == "00" || secondPart == "0") {
            firstPart
        } else if (formattedString[formattedString.length - 1] == '0') {
            formattedString.substring(0, formattedString.length - 1)
        } else {
            formattedString
        }
    } catch (e: Exception) {
        this.toString()
    }
} else ""