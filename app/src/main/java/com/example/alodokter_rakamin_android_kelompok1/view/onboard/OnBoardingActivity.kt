package com.example.alodokter_rakamin_android_kelompok1.view.onboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alodokter_rakamin_android_kelompok1.R
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
