package com.overlay_control.showcaseViews.target

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.View

class ViewTarget(private val view: View) : Target {

    constructor(viewId: Int, activity: Activity) : this(activity.findViewById(viewId))

    override val point: Point
        get() {
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val x = location[0] + view.width / 2
            val y = location[1] + view.height / 2
            return Point(x, y)
        }

    override val bounds: Rect
        get() {
            val location = IntArray(2)
            view.getLocationInWindow(location)
            return Rect(
                location[0],
                location[1],
                location[0] + view.measuredWidth,
                location[1] + view.measuredHeight
            )
        }

    fun getView(): View {
        return view
    }
}
