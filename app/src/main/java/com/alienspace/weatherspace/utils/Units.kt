package com.alienspace.weatherspace.utils

enum class Units(val value: String, val tempLabel: String) {

    METRIC(value = "celsius", tempLabel = "°C"),
    DEGREE(value = "degree", tempLabel = "°"),
    IMPERIAL(value = "imperial", tempLabel = "°F"),
}