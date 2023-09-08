package com.android.autotest

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import android.view.accessibility.AccessibilityNodeInfo
import java.lang.Thread.sleep
import kotlin.random.Random

class AutoTestService: AccessibilityService() {
    val TAG = javaClass.simpleName

    companion object {
        const val MAIN_ACTIVITY = "com.ss.android.ugc.aweme.main.MainActivity"
        const val SEARCH_ACTIVITY = "com.ss.android.ugc.aweme.search.activity.SearchResultActivity"
        const val USERPROFILE_ACTIVITY = "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity"
        const val CHATROOM_ACTIVITY = "com.ss.android.ugc.aweme.im.sdk.chat.ChatRoomActivity"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == TYPE_WINDOW_STATE_CHANGED) {
            when (event.className.toString()) {
                MAIN_ACTIVITY -> {
                    event.source?.let { source ->
                        gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/jvt")?.parent)
                    }
                }
                SEARCH_ACTIVITY -> {
                    event.source?.let { source ->
                        source.getNodeById("com.ss.android.ugc.aweme:id/et_search_kw")?.input("防城港")
                        sleep(5000)
//                        source.fullPrintNode("Search")
                        gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/w73"))
                        sleep(5000)
                        gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/ibn"))
                    }
                }
                USERPROFILE_ACTIVITY -> {
                    event.source?.let { source ->
                        sleep(5000)
                        source.fullPrintNode("Search")
                        val fansTotal = source.getNodeById("com.ss.android.ugc.aweme:id/x5r").text()
                        if (fansTotal.endsWith("万")) {
                            gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/back_btn"))
                        } else {
                            val followed = source.getNodeById("com.ss.android.ugc.aweme:id/p==").text()
                            if (followed.equals("已关注")) {
                                gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/back_btn"))
                            } else {
                                gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/he5"))
                            }
                            sleep(2000)
                            gestureClick(source.getNodeById("com.ss.android.ugc.aweme:id/soa"))
                        }
                    }
                }
                CHATROOM_ACTIVITY -> {
                    event.source?.let { source ->
                        sleep(2000)
//                        source.getNodeById("com.ss.android.ugc.aweme:id/")?.input("很棒，加油。")
//                        source.fullPrintNode("Chatroom")
                    }
                }
            }
        }
//        val nodeInfo: AccessibilityNodeInfo = when {
//            rootInActiveWindow != null -> rootInActiveWindow
//            event?.source != null -> event.source!!
//            else -> return
//        }
//        val searchNode = nodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.ugc.aweme:id/jvt")
//        if (searchNode.size>0) {
//            val searchTemp = searchNode[0].parent
//            Log.d(TAG, "Search Node: $searchTemp")
//            gestureClick(searchNode[0].parent)
//        }
//        when (event?.eventType) {
//            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
//                Log.d(TAG,"Notification Change: $event")
//            }
//            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
//                Log.d(TAG, "Window Change: $event")
//            }
//            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
//                Log.d(TAG, "Content Change: $event")
//            }
//        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
    }


    /**
     * 根据id查找单个节点
     * @param id 控件id
     * @return 对应id的节点
     * */
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

    /**
     * 根据id查找多个节点
     * @param id 控件id
     * @return 对应id的节点列表
     * */
    fun AccessibilityNodeInfo.getNodesById(id: String): List<AccessibilityNodeInfo>? {
        var count = 0
        while (count < 10) {
            findAccessibilityNodeInfosByViewId(id).let {
                if (!it.isNullOrEmpty()) return it
            }
            sleep(100)
            count++
        }
        return null
    }

    /**
     * 根据文本查找单个节点
     * @param text 匹配文本
     * @param allMatch 是否全匹配，默认false，contains()方式的匹配
     * @return 匹配文本的节点
     * */
    fun AccessibilityNodeInfo.getNodeByText(
        text: String,
        allMatch: Boolean = false
    ): AccessibilityNodeInfo? {
        var count = 0
        while (count < 10) {
            findAccessibilityNodeInfosByText(text).let {
                if (!it.isNullOrEmpty()) {
                    if (allMatch) {
                        it.forEach { node -> if (node.text == text) return node }
                    } else {
                        return it[0]
                    }
                }
                sleep(100)
                count++
            }
        }
        return null
    }

    /**
     * 根据文本查找多个节点
     * @param text 匹配文本
     * @param allMatch 是否全匹配，默认false，contains()方式的匹配
     * @return 匹配文本的节点列表
     * */
    fun AccessibilityNodeInfo.getNodesByText(
        text: String,
        allMatch: Boolean = false
    ): List<AccessibilityNodeInfo>? {
        var count = 0
        while (count < 10) {
            findAccessibilityNodeInfosByText(text).let {
                if (!it.isNullOrEmpty()) {
                    return if (allMatch) {
                        val tempList = arrayListOf<AccessibilityNodeInfo>()
                        it.forEach { node -> if (node.text == text) tempList.add(node) }
                        if (tempList.isEmpty()) null else tempList
                    } else {
                        it
                    }
                }
                sleep(100)
                count++
            }
        }
        return null
    }

    /**
     * 获取结点的文本
     * */
    fun AccessibilityNodeInfo?.text(): String {
        return this?.text?.toString() ?: ""
    }

    /**
     * 遍历打印结点
     * */
    fun AccessibilityNodeInfo?.fullPrintNode(
        tag: String,
        spaceCount: Int = 0
    ) {
        if (this == null) return
        val spaceSb = StringBuilder().apply { repeat(spaceCount) { append("  ") } }
        Log.d("$tag", "$spaceSb$text | $viewIdResourceName | $className | Clickable: $isClickable | Editable: $isEditable")
        if (childCount == 0) return
        for (i in 0 until childCount) getChild(i).fullPrintNode(tag, spaceCount + 1)
    }

    /**
     * 利用手势模拟点击
     * @param node: 需要点击的节点
     * */
    fun AccessibilityService.gestureClick(node: AccessibilityNodeInfo?) {
        if (node == null) return
        val tempRect = Rect()
        node.getBoundsInScreen(tempRect)
        val x = ((tempRect.left + tempRect.right) / 2).toFloat()
        val y = ((tempRect.top + tempRect.bottom) / 2).toFloat()
        dispatchGesture(
            GestureDescription.Builder().apply {
                addStroke(GestureDescription.StrokeDescription(Path().apply { moveTo(x, y) }, 0L, 50L))
            }.build(),
            object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                }
            },
            null
        )
    }
    fun gestureSwipe(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        duration: Long = 50L,
        randomPosition: Int = 0,
        randomTime: Long = 0
    ) {
        val tempStartX = startX + Random.nextInt(0 - randomPosition, randomPosition + 1)
        val tempStartY = startY + Random.nextInt(0 - randomPosition, randomPosition + 1)
        val tempEndX = endX + Random.nextInt(0 - randomPosition, randomPosition + 1)
        val tempEndY = endY + Random.nextInt(0 - randomPosition, randomPosition + 1)
        val tempDuration = duration + Random.nextLong(0 - randomTime, randomTime + 1)
        val swipePath = Path()
        swipePath.moveTo(
            if (tempStartX < 0) startX.toFloat() else tempStartX.toFloat(),
            if (tempStartY < 0) startY.toFloat() else tempStartY.toFloat()
        )
        swipePath.lineTo(
            if (tempEndX < 0) endX.toFloat() else tempEndX.toFloat(),
            if (tempEndY < 0) endY.toFloat() else tempEndY.toFloat()
        )
        dispatchGesture(
            GestureDescription.Builder().apply {
                addStroke(GestureDescription.StrokeDescription(swipePath, 0L, tempDuration))
            }.build(),
            object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                }
            },
            null
        )
    }

    fun AccessibilityNodeInfo.input(content: String) = performAction(
        AccessibilityNodeInfo.ACTION_SET_TEXT, Bundle().apply {
            putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, content)
        }
    )

}