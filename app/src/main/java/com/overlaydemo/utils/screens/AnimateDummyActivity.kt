package com.overlaydemo.utils.screens

import DynamicPagerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.overlaydemo.R
import com.overlaydemo.databinding.ActivityAnimateDummyBinding
import java.lang.Math.abs

class AnimateDummyActivity : AppCompatActivity() {

    private var isInitialState = true

    private lateinit var binding: ActivityAnimateDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimateDummyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {

        binding.viewPager.adapter = DynamicPagerAdapter(layoutCount = 3)

        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.clipToPadding = false
        binding.viewPager.overScrollMode = View.OVER_SCROLL_NEVER

        binding.mainCardView.post {

            val mainCardViewWidth = binding.mainCardView.width

//            val marginLayoutParams = binding.viewPager.layoutParams as ViewGroup.MarginLayoutParams
//            marginLayoutParams.setMargins(200, 0, 0, 0)
//            binding.viewPager.layoutParams = marginLayoutParams

            // binding.viewPager.setPadding(40, 0, 20, 0)

            Log.e("hellofirstitem", "mainCardViewWidth = $mainCardViewWidth")

        }

        binding.viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)

        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)

        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }

        binding.viewPager.setPageTransformer(pageTransformer)

        val itemDecoration =
            HorizontalMarginItemDecoration(this, R.dimen.viewpager_current_item_horizontal_margin)

        binding.viewPager.addItemDecoration(itemDecoration)

        binding.mainCardView.setOnClickListener {

            Toast.makeText(this, "Main CardView Clicked", Toast.LENGTH_SHORT).show()

        }

        binding.ivProfileImg.setOnClickListener {

            Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show()

        }

        binding.ivPlusIcon.setOnClickListener {

            Toast.makeText(this, "Plus Icon Clicked", Toast.LENGTH_SHORT).show()

        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val currentItem = position.toFloat() + positionOffset
                val currentItemWidth = binding.viewPager.getChildAt(0)?.width ?: 0
                val scrollX = currentItem * currentItemWidth

                Log.e("scrollXscrollX", "scrollX = $scrollX")

                if (scrollX >= 499.0f) binding.mainCardView.visibility = View.VISIBLE

                if (currentItem == 0f) {
                    // Zoom in animation for the first item
                    binding.mainCardView.elevation = 5f
                    binding.viewPager.elevation = 0f
                    binding.mainCardView.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(0)
                        .start()
                } else if (position >= 1) {
                    binding.mainCardView.elevation = 0f
                    binding.viewPager.elevation = 5f
                    binding.mainCardView.visibility = View.GONE
                } else {
                    binding.mainCardView.elevation = 5f
                    binding.viewPager.elevation = 10f
                    // Zoom out animation for other items
                    val visibilityPercentage = 1 - (scrollX / currentItemWidth)
                    binding.mainCardView.animate()
                        .scaleX(visibilityPercentage)
                        .scaleY(visibilityPercentage)
                        .setDuration(0)
                        .start()
                }
            }
        })

    }

    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }

}