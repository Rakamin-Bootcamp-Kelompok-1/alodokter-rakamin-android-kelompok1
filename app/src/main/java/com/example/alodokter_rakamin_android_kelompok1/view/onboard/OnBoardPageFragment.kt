package com.example.alodokter_rakamin_android_kelompok1.view.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.alodokter_rakamin_android_kelompok1.R
import kotlinx.android.synthetic.main.fragment_onboard_page.*

class OnBoardPageFragment(private val page: OnBoardData) : Fragment(R.layout.fragment_onboard_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvOnBoard1.setText(page.title)
        tvOnBoard2.setText(page.text)
        imageOnBoardPage.setImageResource(page.image)
    }

}