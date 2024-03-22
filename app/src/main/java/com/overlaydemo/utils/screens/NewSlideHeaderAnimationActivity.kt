package com.overlaydemo.utils.screens

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.overlaydemo.R
import com.overlaydemo.databinding.ActivityNewSlideHeaderAnimationBinding

class NewSlideHeaderAnimationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewSlideHeaderAnimationBinding

    private var previousScrollY = 0

    private var isAtBottom = false

    private var isScrollUp = false

    private var currentScrollY = 0

    private var isHideAnimationCompleted = false

    private var isScrollingDown = false

    private var isScrollingStopped = false

    private var isHeaderAnimationRunning = false

    private var headerAnimation: Animator? = null

    private var loginAnimation: Animator? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewSlideHeaderAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {

            currentScrollY = binding.scrollView.scrollY

            val isScrollingUp = currentScrollY < previousScrollY
            previousScrollY = currentScrollY

            val isScrollAtBottom = binding.llScrollContent.measuredHeight <=
                    binding.scrollView.scrollY + binding.scrollView.height

            val layoutParam = binding.cvExploreView.layoutParams as MarginLayoutParams
            Log.e("lkkklgghgghhg", "Current Scroll Value = $currentScrollY")
            Log.e("lkkklgghgghhg", "Current Scroll Value = ${layoutParam.topMargin}")
            if (currentScrollY <= 100) {
                Log.e("srsrsrssrsrrs", "Scrolling Topest")
                // Top of the scroll
                if (!isHideAnimationCompleted || layoutParam.topMargin > 200) {
                    Log.e("srsrsrssrsrrs", "Only Once")
                    hideHeaderViewWithAnimation()
                    hideLoginViewWithAnimation()
                }
                isScrollingDown = false
                isHideAnimationCompleted = true
            } else if (isScrollingUp && !isAtBottom) {
                // Scrolling up

                if (!isScrollUp) {

                    if(!isHideAnimationCompleted || layoutParam.topMargin > 0) {
                        Log.e("srsrsrssrsrrs", "Scrolling UP")
                        hideHeaderViewWithAnimation()
                        hideLoginViewWithAnimation()
                    }
                    isScrollUp = true
                }
                isHideAnimationCompleted = true
                isAtBottom = false
                isScrollingDown = false
            } else if (!isScrollingDown) {

                // Scroll Down
                Log.e("srsrsrssrsrrs", "Scrolling Down")

                val layoutParam =
                    binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams
                if (layoutParam.topMargin == 0) showHeaderWithAnimation()

                isHideAnimationCompleted = false
                isScrollingDown = true
                isAtBottom = false
                isScrollUp = false
            }

            isScrollingStopped = false

            if (isScrollAtBottom && !isAtBottom) {

                Log.e("abcdef", "Scrolling Bottom")
                val layoutParam = binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams

                if (layoutParam.topMargin == 0) {
                    showLoginViewWithAnimation()
                }
                isHideAnimationCompleted = false
                isAtBottom = true
                isScrollingDown = false
                isScrollUp = false
            }

        }

        val handler = Handler(Looper.getMainLooper())

        val runnable = Runnable { executeConditionWhenScrollStops() }

        binding.scrollView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    handler.postDelayed(runnable, 400)
                }

                else -> {
                    // Cancel the runnable if scrolling is still happening
                    handler.removeCallbacks(runnable)
                    isScrollingStopped = false
                    // executeConditionWhenScrollStops()
                }
            }

            false

        }

    }

    private fun executeConditionWhenScrollStops() {
        Log.e("abcdefg", "Runnable Running Working")
        Log.e("abcdefg", "isScrollingStopped = $isScrollingStopped")
        Log.e("abcdefg", "isScrollingDown = $isScrollingDown")
        Log.e("abcdefg", "isAtBottom = $isAtBottom")

        val layoutParam = binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams
        val layoutParam1 = binding.cvTopHeaderCardView.layoutParams as ViewGroup.MarginLayoutParams

        Log.e("abcdefg", "TopMargin = ${layoutParam.topMargin}")
        Log.e("dddddddddfffff", "TopMargin CV Header = ${layoutParam1.topMargin}")
        if (isScrollingDown && (!isScrollingStopped || layoutParam.topMargin == 0)) {
            Log.e("abcdefg", "layoutParam = " + layoutParam.topMargin)

            Log.e("abcdefg", "Runnable Executed!!!")

            if (layoutParam.topMargin == 0) {
                Log.e("abcdefg", "Executed This condition")
                showLoginViewWithAnimation()
            }

        } else {

            Log.e(
                "isViewVisibleInScroll",
                "visibility = ${isViewVisibleInScrollView(binding.scrollView, binding.ivProfile)}"
            )
            Log.e("isViewVisibleInScroll", "layoutParam.topMargin = " + layoutParam.topMargin)

            //    if (!isViewVisibleInScrollView(binding.scrollView, binding.ivProfile)) {
            if (currentScrollY > 50) {

                isHideAnimationCompleted = false
                if (isAtBottom) {
                    isScrollingDown = false
                    if (layoutParam.topMargin == 0) {
                        showHeaderWithAnimation()
                        showLoginViewWithAnimation()
                    }
                    Log.e("isViewVisibleInScroll", "isAtBottom")

                } else {
                    showHeaderWithAnimation()
                    showLoginViewWithAnimation()
                    Log.e("isViewVisibleInScroll", "Not on Bottom")
                }
            } else {
                isHideAnimationCompleted = false
            }

            //    }

        }

    }

    private fun isViewVisibleInScrollView(scrollView: ScrollView, view: View): Boolean {
        val scrollBounds = Rect()
        scrollView.getHitRect(scrollBounds)
        return view.getLocalVisibleRect(scrollBounds)
    }

    private fun showLoginViewWithAnimation() {

        Log.e(
            "dsdsdsdsdsdsdsd",
            "showLoginViewWithAnimation ++++ = ${binding.imageView.visibility == View.GONE}"
        )

        binding.clLoginView.elevation = 10f

        // if (binding.imageView.visibility == View.GONE) {
        binding.cvExploreView.setBackgroundResource(android.R.color.transparent)

        loginAnimation?.cancel()

        val layoutParams =
            binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams
        val initialMarginTop = layoutParams.topMargin

        Log.e("whenitsrun", "showLoginViewWithAnimation ++++ = $initialMarginTop")

        val targetMarginTop = 70.dpToPx()

        val marginAnimator = ValueAnimator.ofInt(initialMarginTop, targetMarginTop)
        marginAnimator.duration = 1500

        marginAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            layoutParams.topMargin = animatedValue
            binding.cvExploreView.layoutParams = layoutParams
        }

        loginAnimation = marginAnimator

        marginAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                isScrollUp = false
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {}

        })

        val views = listOf(
            binding.imageView,
            binding.greetingTextView,
            binding.loginRegisterTextView,
            binding.searchIcon,
            binding.notificationIcon,
            binding.dotIcon
        )

        val animatorSet = AnimatorSet()
        val animators = mutableListOf<Animator>()

        for (view in views) {

            view.alpha = 0f
            view.visibility = View.VISIBLE

            val fadeInAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
            fadeInAnimator.duration = 1200

            fadeInAnimator.addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Float
                view.alpha = animatedValue
            }

            animators.add(fadeInAnimator)

        }

        animatorSet.playTogether(animators)
        animatorSet.start()

        marginAnimator.start()

        //}

    }

    private fun hideHeaderViewWithAnimation() {

        if (isHeaderAnimationRunning) {
            headerAnimation?.cancel()
            // loginAnimation?.cancel()
        }

        binding.clLoginView.elevation = 0f

        hideLoginViewWithAnimation()

        val layoutParams = binding.cvTopHeaderCardView.layoutParams as ViewGroup.MarginLayoutParams

        Log.e(
            "hghghghghghghg",
            "binding.cvExploreView.layoutParams = " + (binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams).topMargin
        )

        val initialMarginTop = layoutParams.topMargin
        val targetMarginTop = (-75).dpToPx()

        Log.e("whenitsrun", "hideHeaderViewWithAnimation")

        val marginAnimator = ValueAnimator.ofInt(initialMarginTop, targetMarginTop)
        // marginAnimator.duration = 50
        marginAnimator.duration = 300

        marginAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            layoutParams.topMargin = animatedValue
            binding.cvTopHeaderCardView.layoutParams = layoutParams
        }

        marginAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isHeaderAnimationRunning = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isHeaderAnimationRunning = false
            }

            override fun onAnimationCancel(animation: Animator) {
                isHeaderAnimationRunning = false
            }

            override fun onAnimationRepeat(animation: Animator) {}

        })

        marginAnimator.start()

        headerAnimation = marginAnimator

    }

    private fun hideLoginViewWithAnimation() {

        loginAnimation?.cancel()

        val layoutParams = binding.cvExploreView.layoutParams as ViewGroup.MarginLayoutParams
        val initialMarginTop = layoutParams.topMargin
        val targetMarginTop = 0.dpToPx()

        Log.e("gfgfgfgfgfgfgfg", "hideLoginViewWithAnimation")

        val marginAnimator = ValueAnimator.ofInt(initialMarginTop, targetMarginTop)
        marginAnimator.duration = 500

        marginAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            layoutParams.topMargin = animatedValue
            binding.cvExploreView.layoutParams = layoutParams
        }

        marginAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                isHideAnimationCompleted = false
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {}

        })

        val views = listOf(
            binding.imageView,
            binding.greetingTextView,
            binding.loginRegisterTextView,
            binding.searchIcon,
            binding.notificationIcon,
            binding.dotIcon
        )

        val animatorSet = AnimatorSet()
        val animators = mutableListOf<Animator>()

        for (view in views) {

            view.alpha = 0f
            // view.visibility = View.VISIBLE

            val fadeOutAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 300

            fadeOutAnimator.addUpdateListener { animator ->
                val animatedValue = animator.animatedValue as Float
                view.alpha = animatedValue
            }

            animators.add(fadeOutAnimator)

        }

        animatorSet.playTogether(animators)
        animatorSet.start()

        loginAnimation = marginAnimator

        marginAnimator.start()

    }

    private fun showHeaderWithAnimation() {

        if (isHeaderAnimationRunning) {
            headerAnimation?.cancel()
        }

        binding.imageView.visibility = View.GONE
        binding.greetingTextView.visibility = View.GONE
        binding.loginRegisterTextView.visibility = View.GONE
        binding.searchIcon.visibility = View.GONE
        binding.notificationIcon.visibility = View.GONE
        binding.dotIcon.visibility = View.GONE

        Log.e("showHeaderWithAnimation", "showHeaderWithAnimation 123")

        val layoutParams = binding.cvTopHeaderCardView.layoutParams as ViewGroup.MarginLayoutParams
        val initialMarginTop = layoutParams.topMargin
        val targetMarginTop = 0.dpToPx()

        val marginAnimator = ValueAnimator.ofInt(initialMarginTop, targetMarginTop)
        marginAnimator.duration = 1200

        marginAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int
            layoutParams.topMargin = animatedValue
            binding.cvTopHeaderCardView.layoutParams = layoutParams
            binding.cvExploreView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }

        marginAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isHeaderAnimationRunning = true
            }

            override fun onAnimationEnd(animation: Animator) {
                isHeaderAnimationRunning = false
            }

            override fun onAnimationCancel(animation: Animator) {
                isHeaderAnimationRunning = false
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })

        marginAnimator.start()

        headerAnimation = marginAnimator
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

}