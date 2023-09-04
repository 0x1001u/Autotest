package com.android.autotest

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AutoTestService: AccessibilityService() {
    val TAG = javaClass.simpleName

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d(TAG, "onAccessibilityEvent: $event")
    }

    override fun onInterrupt() {
       Log.d(TAG, "onInterrupt")
    }
}