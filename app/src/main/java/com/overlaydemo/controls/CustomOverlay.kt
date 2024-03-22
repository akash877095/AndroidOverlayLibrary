package com.overlaydemo.controls

import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.overlaydemo.R
import com.overlaydemo.databinding.OverlayLayoutBinding

class CustomOverlay @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(ctx, attrs) {
    private var mContext: Context = ctx
    private var overlayCounter: Int = 0
    private var overlayView: View? = null

    init {
        init(ctx, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        mContext = context
        attributeSet?.let {
            val a: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.CustomOverlayView, 0, 0)
            val overlayViewLayout = a.getResourceId(R.styleable.CustomOverlayView_layout_overlay, 0)
            if (overlayViewLayout != 0) {
                overlayView = LayoutInflater.from(context).inflate(overlayViewLayout, null)
            }
            orientation = VERTICAL
            a.recycle()
        }
    }

    fun setJsonData(overlayData: OverlayData?) {
        val title = overlayData?.getData()?.getOrNull(0)?.title ?: ""
        val stringBuilder = StringBuilder()
        stringBuilder.append("overlayData = ").append(title)
        val logMessage = stringBuilder.toString()
        val tag = "overlayDataoverlayData"
        Log.e(tag, logMessage)
        showOverlay(overlayData)
    }

    private fun showOverlay(overlayData: OverlayData?) {
        val dialog = Dialog(context, -1)
        val binding = OverlayLayoutBinding.inflate(LayoutInflater.from(context))
        val rootView = binding.root as View
        dialog.setContentView(rootView)

        val dataSize = overlayData?.getData()?.size ?: 0
        val dataModel = overlayData?.getData()?.getOrNull(overlayCounter)
        binding.numberText.text = dataModel?.title ?: ""
        binding.tvTitle.text = dataModel?.title ?: ""
        binding.descriptionText.text = dataModel?.description ?: ""
        binding.btnNextButton.text = dataModel?.nextText ?: ""
        binding.btnSkipButton.text = dataModel?.skipText ?: ""

        val window = dialog.window
       // window?.setBackgroundDrawableResource(R.drawable.overlay_layout)

        binding.btnNextButton.setOnClickListener {
            val nextIndex = overlayCounter + 1
            showOverlay(nextIndex, dialog, binding, overlayData)
        }

        binding.btnSkipButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showOverlay(nextIndex: Int, dialog: Dialog, binding: OverlayLayoutBinding, overlayData: OverlayData?) {
        overlayCounter = nextIndex
        dialog.dismiss()
        showOverlay(overlayData)
    }
}
