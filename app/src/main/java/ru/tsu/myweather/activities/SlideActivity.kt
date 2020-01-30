package ru.tsu.myweather.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.transition.Slide
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.weather_activity.*
import ru.tsu.myweather.Api
import ru.tsu.myweather.R
import ru.tsu.myweather.models.DBCities
import ru.tsu.myweather.models.DBWeather
import ru.tsu.myweather.models.SlideFragment
import ru.tsu.myweather.models.WeatherDBHelper
import kotlinx.coroutines.*

var start = false

class SlideActivity : FragmentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.weather_activity)
                //updateWeather()
            val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
            vp_weather.adapter = pagerAdapter


            btn_menu.setOnClickListener {
                val intent = Intent(baseContext, MenuActivity::class.java)
                startActivity(intent)
            }

        btn_update.setOnClickListener{

            //можно перейти к первому экрану и перерисовать первый/второй

            val intent = Intent(baseContext, SlideActivity::class.java)
            start = false
            updateWeather()
            GlobalScope.launch { // launch a new coroutine in background and continue
                do {
                    Thread.sleep(150)
                } while (!start)
                startActivity(intent)
                finish()
            }

        }

        deup.setOnClickListener {
            val dbHelper = WeatherDBHelper(baseContext)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                    put(DBWeather.CURRENT_TEMPERATURE, "-100")
            }
            Log.d("M_DBWeather", "update")
            db.update(DBWeather.TABLE_NAME, values, null, null)
        }
        }

    fun updateWeather(){
        val dbHelper = WeatherDBHelper(baseContext)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, DBCities.COLUMN_NAME_CITY)
        var cursor = db.query(
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
                Api.updateData(
                    baseContext,
                    getString(getColumnIndexOrThrow(DBCities.COLUMN_NAME_CITY)),
                    cursor.isFirst
                )
            }
        }
    }

        private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
            override fun getCount(): Int = WeatherDBHelper.getSize(baseContext)

            override fun getItem(position: Int) : Fragment? = SlideFragment().newInstance(position)
        }
}