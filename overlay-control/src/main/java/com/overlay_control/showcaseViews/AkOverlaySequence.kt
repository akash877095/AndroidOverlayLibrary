package com.overlay_control.showcaseViews

import android.app.Activity
import android.view.View
import java.util.LinkedList
import java.util.Queue

class AkOverlaySequence(private val mActivity: Activity) : IDetachedListener {
    private var mPrefsManager: PrefsManager? = null
    private val mShowcaseQueue: Queue<AkOverlayView> = LinkedList()
    private var mSingleUse = false
    private var mConfig: ShowcaseConfig? = null
    private var mSequencePosition = 0
    private var mOnItemShownListener: OnSequenceItemShownListener? = null
    private var mOnItemDismissedListener: OnSequenceItemDismissedListener? = null

    constructor(activity: Activity, sequenceID: String) : this(activity) {
        singleUse(sequenceID)
    }

    fun addSequenceItem(
        targetView: View,
        content: String,
        dismissText: String,
        numberText: String
    ): AkOverlaySequence {
        addSequenceItem(targetView, "", content, dismissText, numberText)
        return this
    }

    private fun addSequenceItem(
        targetView: View,
        title: String,
        content: String,
        dismissText: String,
        numberText: String
    ): AkOverlaySequence {
        val sequenceItem = AkOverlayView.Builder(mActivity)
            .setTarget(targetView)
            .setNumberText(numberText)
            .setTitleText(title)
            .setDismissText(dismissText)
            .setContentText(content)
            .setSequence(true)
            .build()
        mConfig?.let { sequenceItem.setConfig(it) }
        mShowcaseQueue.add(sequenceItem)
        return this
    }

    fun addSequenceItem(sequenceItem: AkOverlayView): AkOverlaySequence {
        mConfig?.let { sequenceItem.setConfig(it) }
        mShowcaseQueue.add(sequenceItem)
        return this
    }

    fun singleUse(sequenceID: String): AkOverlaySequence {
        mSingleUse = true
        mPrefsManager = PrefsManager(mActivity, sequenceID)
        return this
    }

    fun setOnItemShownListener(listener: OnSequenceItemShownListener) {
        mOnItemShownListener = listener
    }

    fun setOnItemDismissedListener(listener: OnSequenceItemDismissedListener) {
        mOnItemDismissedListener = listener
    }

    private fun hasFired(): Boolean {
        return mPrefsManager?.sequenceStatus == PrefsManager.SEQUENCE_FINISHED
    }

    fun start() {
        if (mSingleUse && hasFired()) {
            return
        }

        mSequencePosition = mPrefsManager?.sequenceStatus ?: 0
        if (mSequencePosition > 0) {
            repeat(mSequencePosition) {
                mShowcaseQueue.poll()
            }
        }

        if (mShowcaseQueue.size > 0) {
            showNextItem()
        } else {
            if (mSingleUse) {
                mPrefsManager?.setFired()
            }
        }
    }

    private fun showNextItem() {
        if (mShowcaseQueue.isNotEmpty() && !mActivity.isFinishing) {
            val sequenceItem = mShowcaseQueue.remove()
            sequenceItem.setDetachedListener(this)
            sequenceItem.show(mActivity)
            mOnItemShownListener?.onShow(sequenceItem, mSequencePosition)
        } else {
            if (mSingleUse) {
                mPrefsManager?.setFired()
            }
        }
    }

    private fun skipTutorial() {
        mShowcaseQueue.clear()
        if (mShowcaseQueue.isNotEmpty() && !mActivity.isFinishing) {
            val sequenceItem = mShowcaseQueue.remove()
            sequenceItem.setDetachedListener(this)
            sequenceItem.show(mActivity)
            mOnItemShownListener?.onShow(sequenceItem, mSequencePosition)
        } else {
            if (mSingleUse) {
                mPrefsManager?.setFired()
            }
        }
    }

    fun setConfig(config: ShowcaseConfig) {
        mConfig = config
    }

    interface OnSequenceItemShownListener {
        fun onShow(itemView: AkOverlayView, position: Int)
    }

    interface OnSequenceItemDismissedListener {
        fun onDismiss(itemView: AkOverlayView, position: Int)
    }

    override fun onShowcaseDetached(
        showcaseView: AkOverlayView?,
        wasDismissed: Boolean,
        wasSkipped: Boolean
    ) {
        showcaseView?.setDetachedListener(null)

        if (wasDismissed) {
            showcaseView?.let { mOnItemDismissedListener?.onDismiss(it, mSequencePosition) }
            mPrefsManager?.let {
                mSequencePosition++
                it.sequenceStatus = mSequencePosition
            }
            showNextItem()
        }

        if (wasSkipped) {
            showcaseView?.let { mOnItemDismissedListener?.onDismiss(it, mSequencePosition) }
            mPrefsManager?.let {
                mSequencePosition++
                it.sequenceStatus = mSequencePosition
            }
            skipTutorial()
        }
    }

}
