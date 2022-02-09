package com.kuma.kangaroof.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.kuma.kangaroof.R

/**
 * name: MyView
 * author: 熊熊熊
 * date: 2022/1/6 17:03
 * description:
 */
class MyView : View {

    lateinit var ta: TypedArray

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        ta = context.obtainStyledAttributes(attrs, R.styleable.MyView)
        ta.getBoolean(R.styleable.MyView_attr1,true)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}