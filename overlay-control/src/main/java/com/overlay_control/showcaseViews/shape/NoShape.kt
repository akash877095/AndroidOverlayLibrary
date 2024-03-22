package com.overlay_control.showcaseViews.shape

import android.graphics.Canvas
import android.graphics.Paint
import com.overlay_control.showcaseViews.target.Target

/**
 * A Shape implementation that draws nothing.
 */
class NoShape : Shape {
    override fun updateTarget(target: Target) {
        // do nothing
    }

    override fun getTotalRadius(): Int {
        return 0
    }

    override fun setPadding(padding: Int) {
        // do nothing
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        // do nothing
    }

    override fun getWidth(): Int {
        return 0
    }

    override fun getHeight(): Int {
        return 0
    }
}