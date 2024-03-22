package com.overlay_control.showcaseViews

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class FadeAnimationFactory : IAnimationFactory {

    companion object {
        private const val ALPHA = "alpha"
        private const val INVISIBLE = 0f
        private const val VISIBLE = 1f
    }

    private val interpolator: AccelerateDecelerateInterpolator

    init {
        interpolator = AccelerateDecelerateInterpolator()
    }

    override fun animateInView(target: View, point: Point, duration: Long, listener: IAnimationFactory.AnimationStartListener) {
        val oa = ObjectAnimator.ofFloat(target, ALPHA, INVISIBLE, VISIBLE)
        oa.duration = duration
        oa.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                listener.onAnimationStart()
            }

            override fun onAnimationEnd(animator: Animator) {}

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
        oa.start()
    }

    override fun animateOutView(target: View, point: Point, duration: Long, listener: IAnimationFactory.AnimationEndListener) {
        val oa = ObjectAnimator.ofFloat(target, ALPHA, INVISIBLE)
        oa.duration = duration
        oa.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {
                listener.onAnimationEnd()
            }

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })
        oa.start()
    }

    override fun animateTargetToPoint(showcaseView: AkOverlayView, point: Point) {
        val set = AnimatorSet()
        @SuppressLint("ObjectAnimatorBinding")
        val xAnimator = ObjectAnimator.ofInt(showcaseView, "showcaseX", point.x)
        @SuppressLint("ObjectAnimatorBinding")
        val yAnimator = ObjectAnimator.ofInt(showcaseView, "showcaseY", point.y)
        set.playTogether(xAnimator, yAnimator)
        set.interpolator = interpolator
        set.start()
    }
}
