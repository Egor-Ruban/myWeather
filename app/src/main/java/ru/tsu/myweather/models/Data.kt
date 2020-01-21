package ru.tsu.myweather.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Model(
    var current: Current,
    var location: Location,
    var request: Request
)

data class Current(
    var observationTime: String? = null,
var temperature: Int = 20,
var weatherCode: Int? = null,
var weatherIcons: List<String>? = null,
var weatherDescriptions: List<String>? = null,
var windSpeed: Int? = null,
var windDegree: Int? = null,
var windDir: String? = null,
var pressure: Int? = null,
var precip: Int? = null,
var humidity: Int? = null,
var cloudcover: Int? = null,
var feelslike: Int? = null,
var uvIndex: Int? = null,
var visibility: Int? = null,
var isDay: String? = null
)

data class Location(
    var name : String ,
    var country : String ,
    var region : String ,
    var lat : String ,
    var lon : String ,
    var timezoneId : String ,
    var localtime : String ,
    var localtimeEpoch : Int ,
    var utcOffset : String
)
data class Request (

    var type : String ,
    var query : String ,
    var language : String ,
    var unit : String
)