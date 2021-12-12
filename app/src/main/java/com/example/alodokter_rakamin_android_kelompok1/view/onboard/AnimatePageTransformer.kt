package com.example.alodokter_rakamin_android_kelompok1.view.onboard

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

class AnimatePageTransformer: ViewPager2.PageTransformer {
    companion object{
        private const val MIN_SCALE_ANIMATE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        val pageHeight = page.height

        when{
            position < -1 -> page.alpha = 0f
            position <= 1 -> {
                val scaleFactor = MIN_SCALE_ANIMATE.coerceAtLeast(1 - abs(position))
                val verticalMargin = pageHeight * (1 - scaleFactor) / 2
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    page.translationX = horizontalMargin - verticalMargin / 2
                } else {
                    page.translationX = -horizontalMargin + verticalMargin / 2
                }

                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE_ANIMATE) / (1 - MIN_SCALE_ANIMATE) * (1 - MIN_ALPHA)
            }
            else -> page.alpha = 0f
        }
    }
}