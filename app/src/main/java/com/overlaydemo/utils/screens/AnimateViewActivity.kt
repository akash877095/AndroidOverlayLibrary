package com.overlaydemo.utils.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.overlaydemo.databinding.ActivityAnimateViewBinding
import com.overlaydemo.databinding.ItemsLayoutBinding

class AnimateViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimateViewBinding
    private var isInitialState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimateViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        addDynamicDataLayouts(3)
        binding.mainCardView.post {
            initViewsHelper()
        }
        binding.horizontalScrollView.elevation = 0.0f
        binding.mainCardView.setOnClickListener {
            Toast.makeText(this@AnimateViewActivity, "Card View Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.ivProfileImg.setOnClickListener {
            Toast.makeText(this@AnimateViewActivity, "Profile Image Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.ivPlusIcon.setOnClickListener {
            Toast.makeText(this@AnimateViewActivity, "Plus Icon Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.horizontalScrollView.viewTreeObserver.addOnScrollChangedListener {
            initViewsHelper()
        }
    }

    private fun initViewsHelper() {
        binding.mainCardView.width.let { mainCardViewWidth ->
            binding.dynamicViewsLinearLayout.setPadding(mainCardViewWidth + 40, 0, 20, 0)
            binding.horizontalScrollView.clipToPadding = false
            binding.horizontalScrollView.overScrollMode = View.OVER_SCROLL_NEVER
            Log.e("hellofirstitem", "mainCardViewWidth = $mainCardViewWidth")
        }

        val firstView = binding.dynamicViewsLinearLayout.getChildAt(0)
        val firstViewWidthForMainCardView = firstView.width
        val firstViewWidthForAnimation = binding.horizontalScrollView.getChildAt(0).width
        val scrollX = binding.horizontalScrollView.scrollX
        Log.e("scrollXscrollXscrollX", "scrollX = $scrollX")
        val visibleWidth = (binding.horizontalScrollView.width - firstView.left) + scrollX
        val visibilityPercentage = 1f - (scrollX.toFloat() / firstViewWidthForAnimation.toFloat())
        val scaleXAnimator = ObjectAnimator.ofFloat(binding.mainCardView, "scaleX", visibilityPercentage)
        val scaleYAnimator = ObjectAnimator.ofFloat(binding.mainCardView, "scaleY", visibilityPercentage)
        scaleXAnimator.duration = 0L
        scaleYAnimator.duration = 0L
        scaleXAnimator.start()
        scaleYAnimator.start()
        if (visibleWidth >= firstViewWidthForMainCardView) {
            binding.mainCardView.visibility = View.GONE
        } else {
            binding.mainCardView.visibility = View.VISIBLE
        }
        if (scrollX == 0) {
            isInitialState = true
            binding.mainCardView.elevation = 10.0f
            binding.horizontalScrollView.elevation = 0.0f
        } else if (isInitialState) {
            binding.horizontalScrollView.elevation = 20.0f
            binding.mainCardView.elevation = 10.0f
            isInitialState = false
        }
    }

    private fun addDynamicDataLayouts(layoutCount: Int) {
        var i = 1
        if (1 <= layoutCount) {
            while (true) {
                val dynamicViewBinding = ItemsLayoutBinding.inflate(LayoutInflater.from(this))
                binding.dynamicViewsLinearLayout.addView(dynamicViewBinding.root)
               // dynamicViewBinding.tvTitle.text = "Title $i"
               // dynamicViewBinding.tvDescription.text = "Description $i"
               // dynamicViewBinding.tvDate.text = "Date $i"
                i++
                if (i > layoutCount) {
                    break
                }
            }
        }
    }
}