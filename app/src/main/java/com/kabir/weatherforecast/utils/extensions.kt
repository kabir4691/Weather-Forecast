package com.kabir.weatherforecast.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun <T> Activity.launchActivity(
    it: Class<T>, extras: Bundle = Bundle(),
    clearStack: Boolean = false
) {
    val intent = Intent(this, it)
    intent.putExtras(extras)
    if (clearStack) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Float.kelvinToCelsius(): Float {
    return this - 273.15f
}