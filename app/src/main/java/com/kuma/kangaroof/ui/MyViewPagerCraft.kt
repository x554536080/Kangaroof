package com.kuma.kangaroof.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.Scroller

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

    private val scroller = Scroller(context, AccelerateDecelerateInterpolator())

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

    override fun onDraw(canvas: Canvas) {

    }



    var startH = 0
    var currentPage = 2
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startH = event.x.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                scrollBy(-(event.x - startH).toInt(), 0)
                startH = event.x.toInt()
            }
            MotionEvent.ACTION_UP -> {
                if (currentPage == 1) {
                    if (scrollX > -measuredWidth / 6) {
                        scroller.startScroll(scrollX, scrollY, -scrollX, 0, 300)
                        invalidate()
                        currentPage = 2
                        return true
                    } else {
                        scroller.startScroll(scrollX, scrollY, -scrollX - measuredWidth / 3, 0, 300)
                        invalidate()
                        return true
                    }
                }
                if (currentPage == 2) {
                    if (scrollX > measuredWidth / 6) {
                        scroller.startScroll(scrollX, scrollY, measuredWidth / 3 - scrollX, 0, 300)
                        invalidate()
                        currentPage = 3
                        return true
                    } else if (scrollX < -measuredWidth / 6) {
                        scroller.startScroll(
                            scrollX,
                            scrollY,
                            -measuredWidth / 3 - scrollX,
                            0,
                            300
                        )
                        invalidate()
                        currentPage = 1
                        return true
                    } else {
                        scroller.startScroll(scrollX, scrollY, -scrollX, 0, 300)
                        invalidate()
                        return true
                    }
                }
                if (currentPage == 3) {
                    if (scrollX < measuredWidth / 6) {
                        scroller.startScroll(scrollX, scrollY, -scrollX, 0, 300)
                        invalidate()
                        currentPage = 2
                        return true
                    } else {
                        scroller.startScroll(scrollX, scrollY, -scrollX + measuredWidth / 3, 0, 300)
                        invalidate()
                        return true
                    }

                }

//                if (addOn < -measuredWidth / 6)
            }
        }
        return true
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            invalidate()
        }
    }
}