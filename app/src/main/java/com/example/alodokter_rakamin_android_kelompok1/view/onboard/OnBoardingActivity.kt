package com.example.alodokter_rakamin_android_kelompok1.view.onboard

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
import com.example.alodokter_rakamin_android_kelompok1.view.MainActivity
import kotlinx.android.synthetic.main.activity_onboard.*

class OnBoardingActivity : AppCompatActivity(), OnBoardPager.OnBoardListener {
    private val onBoardPages = listOf(
        OnBoardData( R.string.title_onboardfirst_1, R.string.title_onboardfirst_2, R.drawable.ic_onboard_1),
        OnBoardData(R.string.title_onboardsecond_1, R.string.title_onboardsecond_2, R.drawable.ic_onboard_2),
        OnBoardData(R.string.title_onboardthird_1, R.string.title_onboardthird_1, R.drawable.ic_onboard_3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        onBoardPager.setUpOnBoard(this, onBoardPages)
            .setUpPageTransformer(AnimatePageTransformer())
            .setUpOnBoardListener(this)
    }

    override fun onBoardFinished(skipped: Boolean) {

    }
}
