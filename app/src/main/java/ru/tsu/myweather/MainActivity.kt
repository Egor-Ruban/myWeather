package ru.tsu.myweather

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.tsu.myweather.activities.MenuActivity
import ru.tsu.myweather.models.Data

//TODO добавить обработку неправильно введенных городов
//TODO добавить прогноз
//TODO добавить спиннер
//tODO добавить удаление ненужных городов
//TODO зменить стартовую страницу
//TODO перенести меню

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val int = Intent(this, MenuActivity::class.java)
        updateWeather()
        startActivity(int)
    }

    fun updateWeather(){
        val dbHelper = CitiesDBHelper(baseContext)
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
                beginSearch(getString(getColumnIndexOrThrow(DBCities.COLUMN_NAME_CITY)))
            }
        }
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
                    Log.d("M_MainActivity","response code " + response.code())
                }
            }

            override fun onFailure(
                call: Call<Data.Model>?,
                t: Throwable
            ) {
                Log.d("M_MainActivity","failure $t")
            }
        })
    }
}
