package ru.tsu.myweather

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.tsu.myweather.models.Current
import ru.tsu.myweather.models.Model
import java.util.*

interface Api {

    @GET("current")
    fun getModel(@Query("access_key") key : String,
        @Query("query") city : String

    ) : Call<Model>
    api.php?action=query&format=json&list=search&srsearch=Trump,
    companion object {
        fun create(): Api {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.weatherstack.com/")
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}