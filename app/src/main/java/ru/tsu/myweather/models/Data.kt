package ru.tsu.myweather.models

object Data{
    data class Model(
        val request : Request,
        val location: Location,
        val current: Current,
        val error: Error
    )

    data class Request(
        val type : String,
        val query : String,
        val language : String,
        val unit : String
    )

    data class Location(
        val name : String,
        val country : String,
        val region : String,
        val lat : String,
        val lon : String,
        val timezone_id : String,
        val localtime : String,
        val localtime_epoch : Int,
        val utc_offset : String
    )

    data class Current(
        val observation_time : String,
        val temperature : String,
        val weather_code : String,
        val weather_icons : List<String>,
        val weather_descriptions : List<String>,
        val wind_speed : String,
        val wind_degree : String,
        val wind_dir : String,
        val pressure : String,
        val precip : String,
        val humidity : String,
        val cloudcover : String,
        val feelslike : String,
        val uv_index : String,
        val visibility : String,
        val is_day : String
    )

    data class Error(
        val code : String,
        val type : String,
        val info : String
    )
}
