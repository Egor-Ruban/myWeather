package ru.tsu.myweather.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import ru.tsu.myweather.*
import ru.tsu.myweather.models.Data

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
            val dbHelper = CitiesDBHelper(baseContext)
            val db = dbHelper.writableDatabase
            db.execSQL(DBCities.SQL_DELETE_ENTRIES)
            db.execSQL(DBCities.SQL_CREATE_ENTRIES)
            container = mutableListOf()
        }
    }



    fun addCity(city : String){
        if(checkCity(city)){
            val dbHelper = CitiesDBHelper(baseContext)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(DBCities.COLUMN_NAME_CITY, city.toLowerCase())
            }
            db?.insert(DBCities.TABLE_NAME, null, values)

            beginSearch(city) // вынести в проверку
        }
    }

    private fun checkCity(city : String) : Boolean{
        val dbHelper = CitiesDBHelper(baseContext)
        val dbr = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, DBCities.COLUMN_NAME_CITY)
        val selection = "${DBCities.COLUMN_NAME_CITY} = ?"
        val selectionArgs = arrayOf(city.toLowerCase())
        val cursor = dbr.query(
            DBCities.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null              // The sort order
        )
        return cursor.count == 0
    }

    private fun beginSearch(searchString: String) {
        val res = Api.api.getModel("6da4cdcc62d4d30bdd4a317b7dea6ecf", searchString)
        res.enqueue(object : Callback<Data.Model> {
            override fun onResponse(
                call: Call<Data.Model>?,
                response: Response<Data.Model>
            ) {
                if (response.isSuccessful) {
                    container.add(response.body()!!)
                } else {
                    Log.d("M_MenuActivity","response code " + response.code())
                }
            }

            override fun onFailure(
                call: Call<Data.Model>?,
                t: Throwable
            ) {
                Log.d("M_MenuActivity","failure $t")
            }
        })
    }
}