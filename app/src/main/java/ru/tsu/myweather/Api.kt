package ru.tsu.myweather

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.tsu.myweather.models.Data



interface Api {

    @GET("current")
    fun getModel(@Query("access_key") key : String,
                  @Query("query") city : String
    ) : Call<Data.Model>

    companion object {

        val api by lazy {
            Api.create()
        }

        fun create(): Api {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("https://en.wikipedia.org/w/")
                .baseUrl("http://api.weatherstack.com/")
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}