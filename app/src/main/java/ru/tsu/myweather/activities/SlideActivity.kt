package ru.tsu.myweather.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import ru.tsu.myweather.R
import ru.tsu.myweather.container
import ru.tsu.myweather.models.SlideFragment



class SlideActivity : FragmentActivity() {
        private lateinit var mPager: ViewPager

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.weather_activity)

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = findViewById(R.id.pager)

            // The pager adapter, which provides the pages to the view pager widget.
            val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
            mPager.adapter = pagerAdapter
        }

    /**
         * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
         * sequence.
         */
        private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
            override fun getCount(): Int = container.size

            override fun getItem(position: Int) : Fragment? = SlideFragment().newInstance(position)

        }
}