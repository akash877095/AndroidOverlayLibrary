package com.overlay_control.showcaseViews

interface IDetachedListener {

   fun onShowcaseDetached(
       showcaseView: AkOverlayView?,
       wasDismissed: Boolean,
       wasSkipped: Boolean
    )

}