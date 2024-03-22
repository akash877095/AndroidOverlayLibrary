package com.overlaydemo.verticalviewpagercontrol

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.overlaydemo.R
import com.overlaydemo.databinding.CustomViewVerticalViewPagerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomVerticalViewPager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var binding: CustomViewVerticalViewPagerBinding

    private lateinit var pagerAdapter: VerticalPagerAdapter

    private lateinit var pagerAdapterSecond: VerticalPagerAdapterSecond

    private var indicatorCount: Int = 0

    private var dataList: List<String>? = null

    private val AUTO_SCROLL_DELAY: Long = 2000

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var runnable: Runnable

    init {

        initView(context)

    }

    private fun initView(context: Context) {
        binding =
            CustomViewVerticalViewPagerBinding.inflate(LayoutInflater.from(context), this, true)
        Log.e("hfhfhfhfhfhfh", "initView")
        setupViewPager()
        setupIndicator()
    }

    private fun setupViewPager() {

        // 1st viewpager code
        pagerAdapter = VerticalPagerAdapter().apply {

            setOnClickListener(this@CustomVerticalViewPager)

        }

        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.offscreenPageLimit = 1

        startAutoScroll()

        ////////////////////////////////////////////////////////////////////////////////////////////

        // 2nd viewpager code
        Log.e("hfhfhfhfhfhfh", "setupViewPager")

        pagerAdapterSecond = VerticalPagerAdapterSecond()

        binding.viewPagerSecond.adapter = pagerAdapterSecond

        binding.viewPagerSecond.offscreenPageLimit = 1

        val recyclerView = binding.viewPagerSecond.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.isItemPrefetchEnabled = true
        layoutManager.initialPrefetchItemCount = 1

        binding.viewPagerSecond.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {

                updateIndicators(position)

            }

        })

    }

    private fun setupIndicator() {

        binding.indicatorLayout.removeAllViews()
        indicatorCount = pagerAdapter.itemCount
        for (i in 0 until indicatorCount) {
            val indicatorView = createIndicatorView()
            binding.indicatorLayout.addView(indicatorView)
        }
        Log.e("hfhfhfhfhfhfh", "setupIndicator")

        updateIndicators(0)

    }

    private fun createIndicatorView(): View {
        val indicatorView = View(context)
        val layoutParams = LinearLayout.LayoutParams(
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp),
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)
        )
        layoutParams.setMargins(
            0,
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
            0,
            0
        )
        indicatorView.layoutParams = layoutParams
        indicatorView.background =
            ContextCompat.getDrawable(context, R.drawable.inactive_indicator_back)
        return indicatorView
    }

    private fun updateIndicators(currentPosition: Int) {
        Log.e("hfhfhfhfhfhfh", "updateIndicators")
        for (i in 0 until binding.indicatorLayout.childCount) {
            val indicatorView = binding.indicatorLayout.getChildAt(i)
            if (i == currentPosition) {
                indicatorView.background =
                    ContextCompat.getDrawable(context, R.drawable.active_indicator_back)
            } else {
                indicatorView.background =
                    ContextCompat.getDrawable(context, R.drawable.inactive_indicator_back)
            }
        }
    }

    fun setDataList(dataList: List<String>) {

        Log.e("hfhfhfhfhfhfh", "setItemCount")

        if (dataList.isNotEmpty()) {

            // set Data in Second ViewPager
            pagerAdapter.setData(dataList)

            // set Data in Second ViewPager
            pagerAdapterSecond.setData(dataList)

            this.dataList = dataList

            setupIndicator()

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.firstViewRoot -> {

                stopAutoScroll()

                // Fade-out animation
                val fadeOut = AlphaAnimation(1.0f, 0.0f)
                fadeOut.duration = 1000

                binding.viewPager.startAnimation(fadeOut)

                // If Item is more you can increase the delay time in Handler
                Handler(Looper.getMainLooper()).postDelayed({

                    // Log.e("hjhhjhjshdjkfhjksd","view.tag as Int = " + (view.tag as Int))

                    binding.viewPagerSecond.currentItem = (view.tag as Int)

                },200)

                showSecondViewPagerWithAnimation()

                fadeOut.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation?) {}

                    override fun onAnimationRepeat(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {

                        binding.viewPager.visibility = INVISIBLE

                        // show second view pager

                    }

                })

            }

        }

    }

    private fun showSecondViewPagerWithAnimation() {

        // Expanded Animation Code
        val valueAnimator =
            ValueAnimator.ofInt(0, resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._65sdp))

        val animationDuration = 300L
        valueAnimator.duration = animationDuration

        valueAnimator.start()

        valueAnimator.addUpdateListener { animator ->
            val newHeight = animator.animatedValue as Int
            val layoutParams = binding.secondViewPagerLayout.layoutParams
            layoutParams.height = newHeight
            binding.secondViewPagerLayout.layoutParams = layoutParams
        }

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                binding.viewPager.visibility = View.INVISIBLE
            }
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })

        // Fade-in animation start
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 3500


        binding.secondViewPagerLayout.startAnimation(fadeIn)

        // Expanded Animation Code

    }

    private fun startAutoScroll() {

        runnable = object : Runnable {

            override fun run() {

                val currentItem = binding.viewPager.currentItem

                val itemCount = pagerAdapter.itemCount

                val pageHeight = binding.viewPager.layoutParams.height

                if (currentItem < itemCount - 1) {

                    binding.viewPager.beginFakeDrag()
                    binding.viewPager.fakeDragBy((-pageHeight).toFloat())
                    binding.viewPager.endFakeDrag()

                    binding.viewPager.setCurrentItem(currentItem + 1, true)

                } else {

                    binding.viewPager.beginFakeDrag()
                    binding.viewPager.fakeDragBy(((-pageHeight + (100 * itemCount))).toFloat())
                    binding.viewPager.endFakeDrag()

                    binding.viewPager.setCurrentItem(0, true)

                }

                handler.postDelayed(this, AUTO_SCROLL_DELAY)

            }

        }

        handler.postDelayed(runnable, AUTO_SCROLL_DELAY)

    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

}