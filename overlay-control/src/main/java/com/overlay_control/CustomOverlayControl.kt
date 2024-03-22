package com.overlay_control

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.overlay_control.models.OverlayData
import com.overlay_control.showcaseViews.AkOverlaySequence
import com.overlay_control.showcaseViews.AkOverlayView
import com.overlay_control.showcaseViews.ShowcaseConfig
import com.google.gson.Gson

class CustomOverlayControl {
    companion object {
        val randomNumber = (0..100).random().toString()

        fun showOverlayViews(
            activity: Activity,
            jsonData: String,
            views: Array<View>,
            config: ShowcaseConfig
        ) {

            val overlayData = Gson().fromJson(jsonData, OverlayData::class.java)

            val sequence = AkOverlaySequence(activity, randomNumber).apply {
                setConfig(config)
            }

            val size = overlayData.getData().size
            for (index in 0 until size) {
                val numberText = "${index + 1} of ${overlayData.getData().size}"
                val nextButtonText = overlayData.getData()[index].nextText
                val dummyView = View(activity).apply {
                    visibility = View.INVISIBLE
                }
                if (index + 1 == overlayData.getData().size) {
                    val showcaseView = AkOverlayView.Builder(activity)
                        .setTarget(dummyView)
                        .setNumberText(numberText)
                        .setNextButtonText(nextButtonText)
                        .setSkipText("")
                        .setTitleText(overlayData.getData()[index].title)
                        .setContentText(overlayData.getData()[index].description)
                        .setOverlayLayoutPadding(
                            ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION,
                            0,
                            50,
                            50
                        )
                        .withRectangleShape()
                        .build()
                    sequence.addSequenceItem(showcaseView)
                } else {
                    val showcaseView = AkOverlayView.Builder(activity)
                        .setTarget(views[index])
                        .setNumberText(numberText)
                        .setNextButtonText(nextButtonText)
                        .setTitleText(overlayData.getData()[index].title)
                        .setContentText(overlayData.getData()[index].description)
                        .withRectangleShape()
                        .build()
                    sequence.addSequenceItem(showcaseView)
                }
            }
            sequence.start()
        }
    }
}
