package com.kuma.kangaroof.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * currently 3 childViews
 */
class MyViewPagerCraft : ViewGroup {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    private val TAG: String = "MyViewPagerCraft"

    private var container0ne: FrameLayout = FrameLayout(context)
    private var containerTwo: FrameLayout = FrameLayout(context)
    private var containerThree: FrameLayout = FrameLayout(context)

    init {
        container0ne.setBackgroundColor(Color.parseColor("#ff7f00"))
        containerTwo.setBackgroundColor(Color.parseColor("#007fff"))
        containerThree.setBackgroundColor(Color.parseColor("#ff0000"))
        addView(container0ne)
        addView(containerTwo)
        addView(containerThree)
        setBackgroundColor(Color.parseColor("#ff00ff"))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getDefaultSize(suggestedMinimumHeight, widthMeasureSpec) * 3,
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        )
        measureChildren(
            MeasureSpec.makeMeasureSpec(
                getDefaultSize(suggestedMinimumHeight, widthMeasureSpec),
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                getDefaultSize(suggestedMinimumHeight, heightMeasureSpec),
                MeasureSpec.EXACTLY
            )
        )
    }


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        x -= measuredWidth / 3

        container0ne.layout(0, 0, measuredWidth / 3, measuredHeight)
        containerTwo.layout(measuredWidth / 3, 0, measuredWidth / 3 * 2, measuredHeight)
        containerThree.layout(measuredWidth / 3 * 2, 0, measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {

    }


    var startH = 0
    var startV = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startH = event.x.toInt()
                startV = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                (parent as View)//md 这个到底是什么
                    .scrollBy((startH/**/ - event.x).toInt(), 0)
                Log.d(TAG,"startH=$startH,event.x=${event.x}")
            }
        }
        return true
    }

    override fun computeScroll() {


    }
}