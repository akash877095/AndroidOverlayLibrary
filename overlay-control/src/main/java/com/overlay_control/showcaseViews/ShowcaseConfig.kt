package com.overlay_control.showcaseViews

import android.graphics.Color
import android.graphics.Typeface
import com.overlay_control.showcaseViews.shape.Shape

class ShowcaseConfig {

    companion object {

        const val DEFAULT_MASK_COLOUR = "#D9000000"

    }

    var mDelay: Long = -1
    private var mMaskColour: Int = Color.parseColor(DEFAULT_MASK_COLOUR)
    private var mDismissTextStyle: Typeface? = null
    private var mContentTextColor: Int = Color.parseColor("#ffffff")
    private var mDismissTextColor: Int = Color.parseColor("#ffffff")
    private var mFadeDuration: Long = -1
    private var mShape: Shape? = null
    private var mShapePadding: Int = -1
    private var renderOverNav: Boolean? = null

    fun getDelay(): Long {
        return mDelay
    }

    fun setDelay(delay: Long) {
        mDelay = delay
    }

    fun getMaskColor(): Int {
        return mMaskColour
    }

    fun setMaskColor(maskColor: Int) {
        mMaskColour = maskColor
    }

    fun getContentTextColor(): Int {
        return mContentTextColor
    }

    fun setContentTextColor(contentTextColor: Int) {
        mContentTextColor = contentTextColor
    }

    fun getDismissTextColor(): Int {
        return mDismissTextColor
    }

    fun setDismissTextColor(dismissTextColor: Int) {
        mDismissTextColor = dismissTextColor
    }

    fun getDismissTextStyle(): Typeface? {
        return mDismissTextStyle
    }

    fun setDismissTextStyle(dismissTextStyle: Typeface) {
        mDismissTextStyle = dismissTextStyle
    }

    fun getFadeDuration(): Long {
        return mFadeDuration
    }

    fun setFadeDuration(fadeDuration: Long) {
        mFadeDuration = fadeDuration
    }

    fun getShape(): Shape? {
        return mShape
    }

    fun setShape(shape: Shape) {
        mShape = shape
    }

    fun setShapePadding(padding: Int) {
        mShapePadding = padding
    }

    fun getShapePadding(): Int {
        return mShapePadding
    }

    fun getRenderOverNavigationBar(): Boolean? {
        return renderOverNav
    }

    fun setRenderOverNavigationBar(renderOverNav: Boolean) {
        this.renderOverNav = renderOverNav
    }
}
