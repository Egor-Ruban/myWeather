package ru.tsu.myweather.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_slide.*
import ru.tsu.myweather.MainActivity
import ru.tsu.myweather.R
import ru.tsu.myweather.container


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
        val view: View = inflater.inflate(R.layout.fragment_slide, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_city.text = container[pageNumber].location.name
        tv_temperature.text = container[pageNumber].current.temperature
        val img = container[pageNumber].current.weather_icons[0]
        Picasso.with(requireContext()).load(img).into(iv_icon)
        tv_desc.text = container[pageNumber].current.weather_descriptions[0]
    }
}