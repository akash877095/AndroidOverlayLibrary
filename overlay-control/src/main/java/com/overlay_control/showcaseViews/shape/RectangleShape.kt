package com.overlay_control.showcaseViews.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.overlay_control.showcaseViews.target.Target

class RectangleShape(private var width: Int, private var height: Int) : Shape {

    private var adjustToTarget = true
    private var cornerRadius = 10.0f
    private var fullWidth = false
    private var padding = 0
    private var paddingBottom = 10
    private var paddingLeft = 10
    private var paddingRight = 10
    private var paddingTop = 10
    private var rect: Rect? = null

    constructor(bounds: Rect) : this(bounds, false) {
        rect = bounds
    }

    constructor(bounds: Rect, fullWidth: Boolean) : this(0, 0) {
        this.fullWidth = fullWidth
        height = bounds.height()
        width = if (fullWidth) Integer.MAX_VALUE else bounds.width()
        init()
    }

    fun isAdjustToTarget(): Boolean {
        return adjustToTarget
    }

    fun setAdjustToTarget(adjustToTarget: Boolean) {
        this.adjustToTarget = adjustToTarget
    }

    private fun init() {
        rect = Rect(-width / 2, -height / 2, width / 2, height / 2)
    }

    override fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int) {
        rect?.let { rect ->
            val rectF = RectF(
                (rect.left + x - paddingLeft).toFloat(),
                (rect.top + y - paddingTop).toFloat(),
                (rect.right + x + paddingRight).toFloat(),
                (rect.bottom + y + paddingBottom).toFloat()
            )
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        }
    }

    override fun updateTarget(target: Target) {
        if (adjustToTarget) {
            val bounds = target.bounds
            height = bounds.height()
            width = if (fullWidth) Integer.MAX_VALUE else bounds.width()
            init()
        }
    }

    override fun getTotalRadius(): Int {
        return height / 2 + padding
    }

    override fun setPadding(padding: Int) {
        this.padding = padding
    }

    override fun getWidth(): Int {
        return width
    }

    override fun getHeight(): Int {
        return height
    }
}
