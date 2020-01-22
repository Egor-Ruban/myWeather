package ru.tsu.myweather.activities

import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.tsu.myweather.*
import ru.tsu.myweather.models.Data

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        Log.d("M_WeatherActivity", container.toString())
        for(i in container){
            tv_city.text = i.location.name
            tv_temperature.text = i.current.temperature
        }
    }


}