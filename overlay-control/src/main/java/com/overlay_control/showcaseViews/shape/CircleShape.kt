package com.overlay_control.showcaseViews.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.overlay_control.showcaseViews.target.Target

/**
 * Circular shape for target.
 */
class CircleShape(private var radius: Int = 200) : Shape {
    private var adjustToTarget = true
    private var padding: Int = 0

    constructor(bounds: Rect?) : this(getPreferredRadius(bounds!!))
    constructor(target: Target?) : this(target?.bounds)

    fun setAdjustToTarget(adjustToTarget: Boolean) {
        this.adjustToTarget = adjustToTarget
    }

    fun isAdjustToTarget(): Boolean {
        return adjustToTarget
    }

    fun getRadius(): Int {
        return radius
    }

    fun setRadius(radius: Int) {
        this.radius = radius
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        if (radius > 0) {
            canvas.drawCircle(x.toFloat(), y.toFloat(), (radius + padding).toFloat(), paint)
        }
    }

    override fun updateTarget(target: Target) {
        if (adjustToTarget) {
            radius = getPreferredRadius(target.bounds)
        }
    }

    override fun getTotalRadius(): Int {
        return radius + padding
    }

    override fun setPadding(padding: Int) {
        this.padding = padding
    }

    override fun getWidth(): Int {
        return radius * 2
    }

    override fun getHeight(): Int {
        return radius * 2
    }

    companion object {
        fun getPreferredRadius(bounds: Rect): Int {
            return bounds.width().coerceAtLeast(bounds.height()) / 2
        }
    }
}