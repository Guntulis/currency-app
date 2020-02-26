package com.example.currencyapp.ui.util

import android.widget.EditText

fun EditText.moveCursorAtEnd() {
    val textValue = text
    if (textValue.isNotBlank()) {
        setSelection(textValue.length, textValue.length)
    }
}