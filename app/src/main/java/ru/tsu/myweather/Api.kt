package ru.tsu.myweather

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.tsu.myweather.models.Data
import ru.tsu.myweather.models.WeatherDBHelper


interface Api {

    @GET("current")
    fun getModel(
        @Query("access_key") key: String,
        @Query("query") city: String
    ): Call<Data.Model>

    companion object {
        val api by lazy {
            create()
        }
        fun beginSearch(ctx : Context, searchString: String) {
            val res = api.getModel("6da4cdcc62d4d30bdd4a317b7dea6ecf", searchString)
            res.enqueue(object : Callback<Data.Model> {
                override fun onResponse(
                    call: Call<Data.Model>?,
                    response: Response<Data.Model>
                ) {
                    if (response.isSuccessful) {
                        WeatherDBHelper.insertWeather(ctx, response.body()!!)
                    } else {
                        Log.d("M_MenuActivity", "response code " + response.code())
                    }
                }

                override fun onFailure(
                    call: Call<Data.Model>?,
                    t: Throwable
                ) {
                    Log.d("M_MenuActivity", "failure $t")
                }
            })
        }
        private fun create(): Api {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.weatherstack.com/")
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}