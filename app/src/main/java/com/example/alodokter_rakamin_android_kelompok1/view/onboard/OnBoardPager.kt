package com.example.alodokter_rakamin_android_kelompok1.view.onboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.alodokter_rakamin_android_kelompok1.R
import com.example.alodokter_rakamin_android_kelompok1.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.onboard_page.view.*

class OnBoardPager(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    constructor(context: Context, attributeSet: AttributeSet? = null) : this(
        context,
        attributeSet,
        0
    )

    constructor(context: Context) : this(context, null)

    private val zoomInAnim = AnimationUtils.loadAnimation(context, R.anim.animate)
    private val onBoardPages = mutableListOf<OnBoardData>()
    private var onBoardListener: OnBoardListener? = null

    init {
        inflateView()
        setUpListeners()
    }

    private fun inflateView() {
        View.inflate(context, R.layout.onboard_page, this)
    }

    private fun setUpListeners() {
        btnGetStarted.setOnClickListener {
            onBoardListener?.onBoardFinished(false)
        }

        nextBtnOnBoard.setOnClickListener {
            onBoardListener?.onBoardFinished(true)
        }
    }

    fun setUpOnBoard(
        fragmentActivity: FragmentActivity,
        onBoardPages: List<OnBoardData>
    ): OnBoardPager {
        val viewPagerAdapter = ViewPagerAdapter(fragmentActivity)
        viewPagerAdapter.setPages(onBoardPages)

        viewPager.adapter = viewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == onBoardPages.size - 1) {
                    btnGetStarted.visibility = View.VISIBLE
                    btnGetStarted.startAnimation(zoomInAnim)
                    loginBtnOnBoard.visibility = View.VISIBLE
                    loginBtnOnBoard.startAnimation(zoomInAnim)
                    nextBtnOnBoard.visibility = View.GONE
                } else {
                    btnGetStarted.visibility = View.GONE
                    loginBtnOnBoard.visibility = View.GONE
                    nextBtnOnBoard.visibility = View.VISIBLE
                    nextBtnOnBoard.startAnimation(zoomInAnim)
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        this.onBoardPages.addAll(onBoardPages)
        return this
    }

    fun setUpPageTransformer(pageTransformer: ViewPager2.PageTransformer): OnBoardPager {
        viewPager.setPageTransformer(pageTransformer)
        return this
    }

    fun setUpOnBoardListener(onBoardListener: OnBoardListener): OnBoardPager {
        this.onBoardListener = onBoardListener
        return this
    }

    interface OnBoardListener {
        fun onBoardFinished(skipped: Boolean = false)
    }
}

