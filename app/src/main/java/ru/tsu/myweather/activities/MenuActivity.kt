package ru.tsu.myweather.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import ru.tsu.myweather.*
import ru.tsu.myweather.models.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btn_show.setOnClickListener {
            val int = Intent(baseContext, SlideActivity::class.java)
            startActivity(int)
        }
        btn_search.setOnClickListener {
            val city = edit_search.text.toString()
            addCity(city)
        }
        reset_db.setOnClickListener {
            val dbHelper = WeatherDBHelper(baseContext)
            val db = dbHelper.writableDatabase
            db.execSQL(DBCities.SQL_DELETE_ENTRIES)
            db.execSQL(DBCities.SQL_CREATE_ENTRIES)
            db.execSQL(DBWeather.SQL_DELETE_ENTRIES)
            db.execSQL(DBWeather.SQL_CREATE_ENTRIES)
            val values = ContentValues().apply {
                put(DBCities.COLUMN_NAME_CITY, "fetch:ip")
            }
            db.insert(DBCities.TABLE_NAME, null, values)
            Api.beginSearch(baseContext, "fetch:ip")
        }
    }

    private fun addCity(city: String) {
        if(city.toLowerCase() == "meow"){
            iv_cat.visibility = View.VISIBLE
            return
        }
        if (checkCity(city)) {
            val dbHelper = WeatherDBHelper(baseContext)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(DBCities.COLUMN_NAME_CITY, city.toLowerCase())
            }
            db?.insert(DBCities.TABLE_NAME, null, values)

            Api.beginSearch(baseContext, city) // вынести в проверку
            Toast.makeText(this,"city added",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"city not added",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCity(city : String) : Boolean{
        return checkCityInDB(city)
    }

    private fun checkCityInDB(city: String): Boolean {
        val dbHelper = WeatherDBHelper(baseContext)
        val dbr = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, DBCities.COLUMN_NAME_CITY)
        val selection = "${DBCities.COLUMN_NAME_CITY} = ?"
        val selectionArgs = arrayOf(city.toLowerCase())
        val cursor = dbr.query(
            DBCities.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.count == 0
    }
}