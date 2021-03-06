package ru.tsu.myweather.activities


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import ru.tsu.myweather.Api
import ru.tsu.myweather.R
import ru.tsu.myweather.models.DBCities
import ru.tsu.myweather.models.DBWeather
import ru.tsu.myweather.models.WeatherDBHelper


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
            db.delete(DBCities.TABLE_NAME,null,null)
            db.delete(DBWeather.TABLE_NAME,null,null)
            addCity("fetch:ip")
        }
        val citiesAdapter =
            ArrayAdapter.createFromResource(baseContext,
                R.array.cities,
                android.R.layout.simple_spinner_item)
        spinner.adapter = citiesAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                addCity((itemSelected as TextView).text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
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
            val dbr = dbHelper.readableDatabase
            var cursor = dbr.query(
                DBCities.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            with(cursor) {
                while (moveToNext()) {
                    Log.d("M_MenuActivity", getString(getColumnIndexOrThrow(DBCities.COLUMN_NAME_CITY)))
                }
            }
            Log.d("M_MenuActivity", "hey")
            cursor = dbr.query(
                DBWeather.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            with(cursor) {
                while (moveToNext()) {
                    Log.d("M_MenuActivity", getString(getColumnIndexOrThrow(DBWeather.LOCATION_NAME)))
                }
            }

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