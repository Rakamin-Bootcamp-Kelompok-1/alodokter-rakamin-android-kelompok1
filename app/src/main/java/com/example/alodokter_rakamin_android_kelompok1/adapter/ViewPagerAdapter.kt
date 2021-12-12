package com.example.alodokter_rakamin_android_kelompok1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alodokter_rakamin_android_kelompok1.view.onboard.OnBoardData
import com.example.alodokter_rakamin_android_kelompok1.view.onboard.OnBoardPageFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var pages = mutableListOf<OnBoardData>()

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = OnBoardPageFragment(pages[position])

    fun setPages(pages: List<OnBoardData>) {
        this.pages.addAll(pages)
    }
}