package com.example.androidexamproject.weather
data class Forecast(
    val aqi: Int,
    val date: String,
    val fl: String,
    val fx: String,
    val high: String,
    val low: String,
    val notice: String,
    val sunrise: String,
    val sunset: String,
    val type: String,
    val week: String,
    val ymd: String
){
    override fun toString(): String {
        return "$date 号: $week 白天: $low---$high  $type"
    }
}