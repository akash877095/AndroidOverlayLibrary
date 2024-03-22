package com.overlay_control.showcaseViews

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

class CircularRevealAnimationFactory : IAnimationFactory {

    companion object {
        private const val ALPHA = "alpha"
        private const val INVISIBLE = 0f
        private const val VISIBLE = 1f
    }

    private val interpolator: AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun animateInView(target: View, point: Point, duration: Long, listener: IAnimationFactory.AnimationStartListener) {
        val animator = ViewAnimationUtils.createCircularReveal(target, point.x, point.y, 0f,
            if (target.width > target.height) target.width.toFloat() else target.height.toFloat()
        )
        animator.duration = duration
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                listener.onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator) {}

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun animateOutView(target: View, point: Point, duration: Long, listener: IAnimationFactory.AnimationEndListener) {
        val animator = ViewAnimationUtils.createCircularReveal(target, point.x, point.y,
            if (target.width > target.height) target.width.toFloat() else target.height.toFloat(), 0f
        )
        animator.duration = duration
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                listener.onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun animateTargetToPoint(showcaseView: AkOverlayView, point: Point) {
        val set = AnimatorSet()
        val xAnimator = ObjectAnimator.ofInt(showcaseView, "showcaseX", point.x)
        val yAnimator = ObjectAnimator.ofInt(showcaseView, "showcaseY", point.y)
        set.playTogether(xAnimator, yAnimator)
        set.interpolator = interpolator
        set.start()
    }
}
