package com.doterra.app


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

class WelcomeActivity : AppCompatActivity() {

    private lateinit var bgScrollView: HorizontalScrollView
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_welcome)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        applySystemWindowInsetsPadding()

        bgScrollView = findViewById(R.id.bg_scroll_view)
        viewPager = findViewById(R.id.viewPager)

        val adapter = WelcomePagerAdapter(this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                applyParallaxEffect(position, positionOffset)
            }
        })
    }

    private fun applySystemWindowInsetsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun applyParallaxEffect(position: Int, positionOffset: Float) {
        val bgForest = findViewById<View>(R.id.bg_forest)
        val totalScrollWidth = bgForest.width - bgScrollView.width
        val scrollMultiplier = 1f
        val scrollPosition = ((position + positionOffset) / (viewPager.adapter?.itemCount
            ?: 1)) * totalScrollWidth * scrollMultiplier
        bgScrollView.scrollTo(scrollPosition.toInt(), 0)
        Log.d(
            "ParallaxEffect",
            "Position: $position, Offset: $positionOffset, ScrollPosition: $scrollPosition"
        )
    }
}
