package ru.tsu.myweather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.tsu.myweather.models.Data
//TODO добавить картинку
//TODO добавить прогноз
//TODO добавить спиннер
//TODO добавить сохранение городов
//TODO добавить автообновление
//TODO добавить анимацию
//TODO добавить меню

class MainActivity : AppCompatActivity() {

    val api by lazy {
        Api.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                beginSearch(edit_search.text.toString())
            }
        }
    }

    private fun beginSearch(searchString: String) {
        val res = api.getModel("6da4cdcc62d4d30bdd4a317b7dea6ecf", searchString)
        res.enqueue(object : Callback<Data.Model> {
            override fun onResponse(
                call: Call<Data.Model>?,
                response: Response<Data.Model>
            ) {
                if (response.isSuccessful) {
                    Log.d("M_MainActivity","response " + response.body())
                    txt_search_result.text = response.body()?.current?.temperature
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
