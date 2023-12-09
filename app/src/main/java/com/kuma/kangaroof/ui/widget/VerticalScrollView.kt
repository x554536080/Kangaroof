package com.kuma.kangaroof.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.ScrollView
import kotlin.math.abs

class VerticalScrollView : ScrollView {
    private  var mGestureDetector: GestureDetector

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mGestureDetector = GestureDetector(context,YScrollDetector())
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev)
    }

    inner class YScrollDetector:SimpleOnGestureListener(){
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return (abs(distanceY) > abs(distanceX))
        }
    }
}