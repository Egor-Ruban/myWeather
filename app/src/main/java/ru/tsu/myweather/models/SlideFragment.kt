package ru.tsu.myweather.models

import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_slide.*
import kotlinx.android.synthetic.main.weather_activity.*
import ru.tsu.myweather.R


class SlideFragment : Fragment() {
    val ARGUMENT_PAGE_NUMBER = "arg_page_number"

    var pageNumber = 0

    fun newInstance(page: Int): SlideFragment? {
        val pageFragment = SlideFragment()
        val arguments = Bundle()
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
        pageFragment.arguments = arguments
        return pageFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_slide, vp_weather)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbHelper = WeatherDBHelper(context!!)
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBWeather.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToPosition(pageNumber)
        tv_city.text = cursor.getString(cursor.getColumnIndexOrThrow(DBWeather.LOCATION_NAME))
        tv_temperature.text = cursor.getString(cursor.getColumnIndexOrThrow(DBWeather.CURRENT_TEMPERATURE))
        val img = cursor.getString(cursor.getColumnIndexOrThrow(DBWeather.CURRENT_WEATHER_ICONS))
        Picasso.with(requireContext()).load(img).into(iv_icon)
        tv_desc.text = cursor.getString(cursor.getColumnIndexOrThrow(DBWeather.CURRENT_WEATHER_DESCRIPTIONS))
    }
}