package com.overlaydemo

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.overlay_control.CustomOverlayControl
import com.overlay_control.models.OverlayData
import com.overlay_control.showcaseViews.AkOverlayView
import com.overlay_control.showcaseViews.AkOverlaySequence
import com.overlay_control.showcaseViews.ShowcaseConfig
import com.overlaydemo.databinding.ActivityMainBinding
import com.overlaydemo.databinding.CustomDialogWithViewpagerBinding
import com.overlaydemo.utils.adapters.DialogViewPagerAdapter
import com.overlaydemo.utils.screens.AnimateDummyActivity
import com.overlaydemo.utils.screens.CalenderViewActivity
import com.overlaydemo.utils.screens.NewSlideHeaderAnimationActivity
import com.overlaydemo.utils.screens.VerticalViewPagerActivity
import com.overlaydemo.utils.screens.WebviewFontActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val jsonData =
        "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"title\": \"Welcome to Our App!\",\n" +
                "      \"description\": \"Get started by exploring the amazing features and functionalities of our app.\",\n" +
                "      \"nextText\": \"Next\",\n" +
                "      \"skipText\": \"Skip 1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Discover New Content\",\n" +
                "      \"description\": \"Find interesting articles, videos, and updates tailored just for you.\",\n" +
                "      \"nextText\": \"Next\",\n" +
                "      \"skipText\": \"Skip 2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Personalize Your Experience\",\n" +
                "      \"description\": \"Customize your profile, preferences, and settings to make the app your own.\",\n" +
                "      \"nextText\": \"Continue\",\n" +
                "      \"skipText\": \"Skip 3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Connect with Others\",\n" +
                "      \"description\": \"Interact with friends, colleagues, and fellow users through messaging, comments, and more.\",\n" +
                "      \"nextText\": \"Explore\",\n" +
                "      \"skipText\": \"Skip 4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Stay Informed\",\n" +
                "      \"description\": \"Receive updates, notifications, and alerts to stay informed about important news and events.\",\n" +
                "      \"nextText\": \"Proceed\",\n" +
                "      \"skipText\": \"Skip 5\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Get Help and Support\",\n" +
                "      \"description\": \"Access our support resources and get assistance whenever you need it.\",\n" +
                "      \"nextText\": \"Finish\",\n" +
                "      \"skipText\": \"Skip 6\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        val views = arrayOf<View>(
            binding.btWebviewFonts,
            binding.btnHeaderAnimation,
            binding.btnViewPagerAnim,
            binding.btViewPagerDialog,
            binding.btVerticalViewPager,
            binding.btnCalenderView
        )

        val overlayData = Gson().fromJson(jsonData, OverlayData::class.java)

        val config = ShowcaseConfig().apply {
            setDelay(300)
        }

        val sequence = AkOverlaySequence(this, (0..100).random().toString()).apply {
            setConfig(config)
        }

        val size = overlayData.getData().size

        for (index in 0 until size) {

            val showcaseView = AkOverlayView.Builder(this)
                .setTarget(views[index])
                .setNumberText("${index + 1} of $size")
                .setNextButtonText(overlayData.getData()[index].nextText)
                .setTitleText(overlayData.getData()[index].title)
                .setContentText(overlayData.getData()[index].description)
                .withRectangleShape()
                .build()

            sequence.addSequenceItem(showcaseView)

        }

        sequence.start()

//        CustomOverlayControl.showOverlayViews(
//            this,
//            jsonData,
//            views,
//            config
//        )

        binding.btWebviewFonts.setOnClickListener {
            startActivity(Intent(this, WebviewFontActivity::class.java))
        }

        binding.btnViewPagerAnim.setOnClickListener {
            startActivity(Intent(this, AnimateDummyActivity::class.java))
        }

        binding.btnHeaderAnimation.setOnClickListener {
            startActivity(Intent(this, NewSlideHeaderAnimationActivity::class.java))
        }

        binding.btVerticalViewPager.setOnClickListener {
            startActivity(Intent(this, VerticalViewPagerActivity::class.java))
        }

        binding.btnCalenderView.setOnClickListener {
            startActivity(Intent(this, CalenderViewActivity::class.java))
        }

        binding.btViewPagerDialog.setOnClickListener {

            Log.e("hhhhhhhhh", "Hello btViewPagerDialog")

            val list = ArrayList<String>()
            list.add("Hello")
            list.add("Hello")
            list.add("Hello")
            list.add("Hello")
            list.add("Hello")

            openCustomDialogViewPager(list, isBannerVisible = false, isAllPromoVisible = true)

        }

    }

    private fun openCustomDialogViewPager(
        list: ArrayList<String>,
        isBannerVisible: Boolean,
        isAllPromoVisible: Boolean
    ) {

        val dialog = Dialog(this@MainActivity, WindowManager.LayoutParams.MATCH_PARENT)
        val dialogBinding =
            CustomDialogWithViewpagerBinding.inflate(LayoutInflater.from(this@MainActivity))
        dialog.setContentView(dialogBinding.root)

        val adapter = DialogViewPagerAdapter(isBannerVisible, isAllPromoVisible)
        adapter.setData(list)
        dialogBinding.vpDialogViewPager.adapter = adapter

        dialogBinding.vpDialogViewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }

        // Setup Indicators
        dialogBinding.llIndicators.removeAllViews()
        if (list.size > 1) {
            val itemCount = adapter.itemCount
            for (i in 0 until itemCount) {
                val indicatorView = View(this)
                val layoutParams = LinearLayout.LayoutParams(
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp)
                )
                layoutParams.setMargins(
                    10,
                    0,
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
                    0
                )
                indicatorView.layoutParams = layoutParams
                indicatorView.setBackgroundResource(R.drawable.inactive_dialog_viewpager_back)
                dialogBinding.llIndicators.addView(indicatorView)
            }
            updateIndicators(0, dialogBinding)
        } else {
            dialogBinding.vpDialogViewPager.isUserInputEnabled = false
        }

        dialogBinding.vpDialogViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position, dialogBinding)
            }
        })

        dialogBinding.vpDialogViewPager.setPageTransformer(pageTransformer)

        dialog.window?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black80
                )
            )
        )

        dialog.show()

    }

    private fun updateIndicators(
        currentPosition: Int,
        dialogBinding: CustomDialogWithViewpagerBinding
    ) {
        for (i in 0 until dialogBinding.llIndicators.childCount) {
            val indicatorView = dialogBinding.llIndicators.getChildAt(i)
            if (i == currentPosition) {
                val indicatorParam = LinearLayout.LayoutParams(
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._20sdp),
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp)
                )
                indicatorParam.setMargins(
                    10,
                    0,
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
                    0
                )
                indicatorView.layoutParams = indicatorParam
                indicatorView.setBackgroundResource(R.drawable.active_indicator_back)
            } else {
                val indicatorParam = LinearLayout.LayoutParams(
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp),
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp)
                )
                indicatorParam.setMargins(
                    10,
                    0,
                    this.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._2sdp),
                    0
                )
                indicatorView.layoutParams = indicatorParam
                indicatorView.setBackgroundResource(R.drawable.inactive_dialog_viewpager_back)
            }
        }
    }


}
