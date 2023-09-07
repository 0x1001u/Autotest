package com.android.autotest

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.Thread.sleep

class AutoTestService: AccessibilityService() {
    val TAG = javaClass.simpleName
    companion object {
//        const val SEARCH_VIEW_ID = "com.ss.android.ugc.aweme.splash.SplashActivity"
        const val MAIN_VIEW_ID = "com.ss.android.ugc.aweme.main.MainActivity"
        const val SEARCH_VIEW_ID = "com.ss.android.ugc.aweme:id/g7_"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
//        Log.d(TAG, "onAccessibilityEvent: $event")
        if (event.eventType == TYPE_WINDOW_STATE_CHANGED ) {
            when (event.className.toString()) {
                MAIN_VIEW_ID -> {
                    event.source?.let { source ->
                        Log.d(TAG, "Search View ID: $source")
                        source.getNodeById(SEARCH_VIEW_ID).click()
                    }
                }
                else -> {
                    Log.d(TAG, "Search Null: $event")
                }
            }
        }
    }

    override fun onInterrupt() {
       Log.d(TAG, "onInterrupt")
    }

    fun AccessibilityNodeInfo?.click() {
        if (this == null) return
        if (this.isClickable) {
            Log.d(TAG, "Clickable: $this")
            this.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            return
        } else {
            Log.d(TAG, "Don't Clickable: $this")
            this.parent.click()
        }
    }

    fun AccessibilityNodeInfo.getNodeById(id: String): AccessibilityNodeInfo? {
        var count = 0
        while (count < 10) {
            findAccessibilityNodeInfosByViewId(id).let {
                if (!it.isNullOrEmpty()) return it[0]
            }
            sleep(100)
            count++
        }
        return null
    }
}