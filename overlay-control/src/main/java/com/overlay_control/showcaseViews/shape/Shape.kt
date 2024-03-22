package com.overlay_control.showcaseViews.shape

import android.graphics.Canvas
import android.graphics.Paint
import com.overlay_control.showcaseViews.target.Target

interface Shape {

    fun draw(canvas: Canvas, paint: Paint, x: Int, y: Int)

    fun getWidth(): Int

    fun getHeight(): Int

    fun updateTarget(target: Target)

    fun getTotalRadius(): Int

    fun setPadding(padding: Int)

}