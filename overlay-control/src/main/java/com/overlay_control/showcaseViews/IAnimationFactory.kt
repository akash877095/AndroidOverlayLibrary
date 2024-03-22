package com.overlay_control.showcaseViews

import android.graphics.Point
import android.view.View

interface IAnimationFactory {

    fun animateInView(
        target: View,
        point: Point,
        duration: Long,
        listener: AnimationStartListener
    )

    fun animateOutView(
        target: View,
        point: Point,
        duration: Long,
        listener: AnimationEndListener
    )

    fun animateTargetToPoint(showcaseView: AkOverlayView, point: Point)

    interface AnimationStartListener {
        fun onAnimationStart()
    }

    interface AnimationEndListener {
        fun onAnimationEnd()
    }

}