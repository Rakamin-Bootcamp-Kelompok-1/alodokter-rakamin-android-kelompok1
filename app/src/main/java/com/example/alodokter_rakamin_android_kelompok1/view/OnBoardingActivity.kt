package com.example.alodokter_rakamin_android_kelompok1.view

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.config.SharedPreferences
import com.example.alodokter_rakamin_android_kelompok1.config.show
import com.example.alodokter_rakamin_android_kelompok1.adapter.ViewPagerAdapter
import com.example.alodokter_rakamin_android_kelompok1.view.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: PagerAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var dotsLayout: LinearLayout
    lateinit var nextBtn: Button
    private lateinit var onBoardSlider: ViewPager
    private lateinit var listArray: Array<Int>
    private val sliderChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            if (position == listArray.size.minus(1)) {
                nextBtn.show()
            } else {
                nextBtn.show()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)
        dotsLayout = findViewById(R.id.dotsLayout)
        nextBtn = findViewById(R.id.btnOnBoarding)
        onBoardSlider = findViewById(R.id.onBoardSlider)



        init()
        dataSet()
        interactions()
    }

    private fun init() {
        /** Layouts of the three onBoarding Screens.
         *  Add more layouts if you wish.
         **/
        listArray = arrayOf(
            R.layout.item_first_onboard,
            R.layout.item_second_onboard,
            R.layout.item_third_onboard
        )

        viewPagerAdapter = ViewPagerAdapter(this, listArray)
    }

    private fun dataSet() {
        /**
         * Adding bottom dots
         * */
        addBottomDots(0)

        onBoardSlider.apply {
            adapter = viewPagerAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun interactions() {
        nextBtn.setOnClickListener {
            /**
             *  Checking for last page, if last page
             *  login screen will be launched
             * */
            val current = getCurrentScreen(+1)
            if (current < listArray.size) {
                /**
                 * Move to next screen
                 * */
                onBoardSlider.currentItem = current
            } else {
                // Launch login screen
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        SharedPreferences(this).setFirstTimeLaunch(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(listArray.size)

        dotsLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.gray))
            dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.blue_tosca))
        }
    }

    private fun getCurrentScreen(i: Int): Int = onBoardSlider.currentItem.plus(i)

}
