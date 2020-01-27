package ru.tsu.myweather.activities

import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.weather_activity.*
import ru.tsu.myweather.Api
import ru.tsu.myweather.R
import ru.tsu.myweather.models.DBCities
import ru.tsu.myweather.models.SlideFragment
import ru.tsu.myweather.models.WeatherDBHelper

class SlideActivity : FragmentActivity() {
    //TODO добавить обработку неправильно введенных городов
//TODO добавить прогноз
//TODO добавить спиннер
//tODO добавить удаление ненужных городов
//TODO зменить стартовую страницу
//TODO перенести меню
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.weather_activity)
            updateWeather()
            val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
            vp_weather.adapter = pagerAdapter
        }

    fun updateWeather(){
        val dbHelper = WeatherDBHelper(baseContext)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, DBCities.COLUMN_NAME_CITY)
        val cursor = db.query(
            DBCities.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                Api.beginSearch(baseContext, getString(getColumnIndexOrThrow(DBCities.COLUMN_NAME_CITY)))
            }
        }
    }

        private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
            override fun getCount(): Int = WeatherDBHelper.getSize(baseContext)

            override fun getItem(position: Int) : Fragment? = SlideFragment().newInstance(position)
        }
}