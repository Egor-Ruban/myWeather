package ru.tsu.myweather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.tsu.myweather.models.Model


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
        res.enqueue(object : Callback<Model> {
            override fun onResponse(
                call: Call<Model>?,
                response: Response<Model>
            ) {
                if (response.isSuccessful()) {
                    Log.d("M_MainActivity","response " + response.body())
                } else {
                    Log.d("M_MainActivity","response code " + response.code())
                }
            }

            override fun onFailure(
                call: Call<Model>?,
                t: Throwable
            ) {
                Log.d("M_MainActivity","failure $t")
            }
        })
        //txt_search_result.text = res.current?.weatherDescriptions.toString()
                //{ result -> txt_search_result.text = "${result.query.searchinfo.totalhits} result found" },

    }
    override fun onPause() {
        super.onPause()
    }
}
