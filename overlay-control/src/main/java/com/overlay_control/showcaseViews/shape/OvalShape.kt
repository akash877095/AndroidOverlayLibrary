package com.overlay_control.showcaseViews.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.overlay_control.showcaseViews.target.Target

class OvalShape(private var radius: Int = 200) : Shape {
    private var adjustToTarget: Boolean = true
    private var padding: Int = 0

    constructor(bounds: Rect?) : this(getPreferredRadius(bounds!!))

    constructor(target: Target?) : this(target?.bounds)

    init {
        this.radius = radius
        this.adjustToTarget = true
    }

    fun isAdjustToTarget(): Boolean {
        return adjustToTarget
    }

    fun setAdjustToTarget(adjustToTarget: Boolean) {
        this.adjustToTarget = adjustToTarget
    }

    fun getRadius(): Int {
        return radius
    }

    fun setRadius(radius: Int) {
        this.radius = radius
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        if (radius > 0) {
            val rad = (radius + padding).toFloat()
            val rectF = RectF(x - rad, y - rad / 2, x + rad, y + rad / 2)
            canvas.drawOval(rectF, paint)
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
        return radius
    }

    companion object {
        fun getPreferredRadius(bounds: Rect): Int {
            return Math.max(bounds.width(), bounds.height()) / 2
        }
    }
}
