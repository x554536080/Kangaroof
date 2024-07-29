package com.kuma.kangaroof.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.kuma.kangaroof.R
import java.util.*
import kotlin.math.acos
import kotlin.math.sin

/**
 * # ******************************************************************
 * # ClassName:      LeafLoadingView
 * # Description:    主要看onDraw就可以了
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/27 16:42
 * # ******************************************************************
 */
class LeafLoadingView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {


    private val WHITE_COLOR = -0x21c67
    private val ORANGE_COLOR = Color.parseColor("#F5A418")

    // 总进度
    private val TOTAL_PROGRESS = 100

    // 中等振幅大小
    private val MIDDLE_AMPLITUDE = 13

    // 不同类型之间的振幅差距
    private val AMPLITUDE_DISPARITY = 5

    // 叶子飘动一个周期所花的时间
    val LEAF_FLOAT_TIME: Long = 3000

    // 叶子旋转一周需要的时间
    val LEAF_ROTATE_TIME: Long = 2000

    // 进度条距左／上／下，右的距离
    private val MARGIN_L = 9f
    private val MARGIN_RIGHT = 25f

    /*comment
    对于控制绘制的变量有三种，
    一种是常量、默认值
    二是动态&确定的控件size
    三是业务逻辑状态控制变量*/
    // 中等振幅大小
    private var mMiddleAmplitude: Int = MIDDLE_AMPLITUDE

    // 振幅差
    private var mAmplitudeDisparity: Int = AMPLITUDE_DISPARITY

    // 叶子飘动一个周期所花的时间
    private var mLeafFloatTime: Long = LEAF_FLOAT_TIME

    // 叶子旋转一周需要的时间
    private var mLeafRotateTime: Long = LEAF_ROTATE_TIME

    // 总宽高
    private var mTotalWidth: Int = 0
    private var mTotalHeight: Int = 0
    private val mLMargin = SizeUtils.dp2px(MARGIN_L)
    private var mRightMargin: Int = SizeUtils.dp2px(MARGIN_RIGHT)

    // 进度条宽度
    private var mProgressWidth: Int = 0

    // 圆弧半径、圆弧右侧横坐标
    private var mArcRadius: Int = 0
    private var mArcRightLocation = 0


    // 当前进度
    private var mProgress = 0

    // 当前进度条的绘制位置
    private var mCurrentProgressPosition = 0

    // 初始化的叶子信息，只包含起始状态
    private var mLeafs: List<Leaf>? = null


    lateinit var mWhiteRectF: RectF
    lateinit var mOrangeRectF: RectF
    lateinit var mArcRectF: RectF

    private var mWhitePaint: Paint = Paint()
    private var mBitmapPaint: Paint = Paint()
    private var mOrangePaint: Paint = Paint()

    private var mLeafBitmap: Bitmap? = null
    private var mLeafWidth = 0
    private var mLeafHeight: Int = 0

    private var mOuterBitmap: Bitmap? = null
    private var mOuterSrcRect: Rect? = null
    private var mOuterDestRect: Rect? = null
    private var mOuterWidth = 0
    private var mOuterHeight: Int = 0

    private var mResources: Resources? = null

    init {
        mResources = resources

        mBitmapPaint = Paint()
        mBitmapPaint.isAntiAlias = true
        //防止抖动
        mBitmapPaint.isDither = true
        //滤波处理防锯齿
        mBitmapPaint.isFilterBitmap = true

        mWhitePaint = Paint()
        mWhitePaint.isAntiAlias = true
        mWhitePaint.color = WHITE_COLOR//开心 健康 哭 高傲 缠绵...

        mOrangePaint = Paint()
        mOrangePaint.isAntiAlias = true
        mOrangePaint.color = ORANGE_COLOR

        mLeafBitmap = (mResources?.getDrawable(R.drawable.leaf) as BitmapDrawable).bitmap
        mLeafWidth = mLeafBitmap?.width ?: 0
        mLeafHeight = mLeafBitmap?.height ?: 0

        mOuterBitmap = (mResources?.getDrawable(R.drawable.leaf_kuang) as BitmapDrawable).bitmap
        mOuterWidth = mOuterBitmap?.width ?: 0
        mOuterHeight = mOuterBitmap?.height ?: 0

        mLeafs = Leaf().generateLeafs()
    }


    //
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTotalWidth = w
        mTotalHeight = h
        mProgressWidth = mTotalWidth - mLMargin - mRightMargin
        mArcRadius = (mTotalHeight - 2 * mLMargin) / 2
        mArcRightLocation = mArcRadius + mLMargin

        //bitmap选择范围
        mOuterSrcRect = Rect(0, 0, mOuterWidth, mOuterHeight)
        //屏幕绘制范围
        mOuterDestRect = Rect(0, 0, mTotalWidth, mTotalHeight)

        mArcRectF = RectF(mLMargin.toFloat(), mLMargin.toFloat(),
                (mLMargin + 2 * mArcRadius).toFloat(), (mTotalHeight - mLMargin).toFloat())
        mWhiteRectF = RectF(mLMargin + mCurrentProgressPosition.toFloat(), mLMargin.toFloat(),
                mTotalWidth - mRightMargin.toFloat(), mTotalHeight - mLMargin.toFloat())
        mOrangeRectF = RectF(mArcRadius.toFloat(), mLMargin.toFloat(),
                mCurrentProgressPosition.toFloat(), (mTotalHeight - mLMargin).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawProgress(canvas)
        drawLeafs(canvas)
        canvas!!.drawBitmap(mOuterBitmap!!, mOuterSrcRect, mOuterDestRect!!, mBitmapPaint)
        postInvalidate()
    }

    private fun drawLeafs(canvas: Canvas?) {
        mLeafRotateTime = if (mLeafRotateTime <= 0) {
            LEAF_ROTATE_TIME
        } else {
            mLeafRotateTime
        }
        val currentTime = System.currentTimeMillis()

        for (leaf in mLeafs!!) {
            if (currentTime > leaf.startTime && leaf.startTime != 0L) {
                // 根据时间计算位置
                getLeafLocation(leaf, currentTime)
                // 根据时间计算旋转角度
                canvas!!.save()
                // 通过Matrix控制叶子旋转
                val matrix = Matrix()
                val transX: Float = mLMargin + leaf.x
                val transY: Float = mLMargin + leaf.y
                matrix.postTranslate(transX, transY)
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                val rotateFraction = ((currentTime - leaf.startTime) % mLeafRotateTime
                        / mLeafRotateTime.toFloat())
                val angle = (rotateFraction * 360).toInt()
                // 根据叶子旋转方向确定叶子旋转角度
                val rotate = if (leaf.rotateDirection == 0) angle + leaf.rotateAngle
                else -angle + leaf.rotateAngle
                matrix.postRotate(rotate.toFloat(), transX
                        + mLeafWidth / 2, transY + mLeafHeight / 2)
                matrix.postScale(1.25f,1.25f)
                canvas.drawBitmap(Bitmap.createBitmap(mLeafBitmap!!
                        ,0,0, mLeafBitmap!!.width,mLeafBitmap!!.height,matrix,true)
                !!, matrix, mBitmapPaint)
                canvas.restore()
            }
        }
    }

    private fun getLeafLocation(leaf: Leaf, currentTime: Long) {
        mLeafFloatTime = if (mLeafFloatTime <= 0) LEAF_FLOAT_TIME else mLeafFloatTime
        val intervalTime = currentTime - leaf.startTime
        if (intervalTime < 0) return //还未到就不出现呗
        else if (intervalTime > mLeafFloatTime) {
            leaf.startTime = System.currentTimeMillis() + Random().nextInt(mLeafFloatTime.toInt())
            //到了就设个间隔等待重新出现呗
        }
        //？？？？？？？？
        val fraction = intervalTime.toFloat() / mLeafFloatTime
        leaf.x = (mProgressWidth - mProgressWidth * fraction)
        leaf.y = getLocationY(leaf)
    }

    private fun getLocationY(leaf: Leaf): Float {
        // y = A(wx+Q)+h
        val w = Math.PI * 2 / mProgressWidth
        var a: Float = when (leaf.type) {
            StartType.LITTLE -> (mMiddleAmplitude - mAmplitudeDisparity).toFloat()
            StartType.MIDDLE -> mMiddleAmplitude.toFloat()
            StartType.BIG -> (mMiddleAmplitude + mAmplitudeDisparity).toFloat()

        }
        return (a * sin(w * leaf.x) + mArcRadius * 3 / 5).toFloat()
    }

    private fun drawProgress(canvas: Canvas?) {
        //保持兼容
        if (mProgress > TOTAL_PROGRESS) mProgress = 0

        //半圆底色
        canvas!!.drawArc(mArcRectF, 90f, 180f, false, mWhitePaint)
        //矩形底色
        mWhiteRectF.left = mArcRightLocation.toFloat()//去除半圆部分的矩形底色
        canvas.drawRect(mWhiteRectF, mWhitePaint)
        //进度
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS
        if (mCurrentProgressPosition < mArcRadius) {
            //进度半圆
            val angleHalf = Math.toDegrees(acos(((mArcRadius - mCurrentProgressPosition)
                    / mArcRadius.toFloat()).toDouble())).toInt()
            val startAngle = 180 - angleHalf
            val sweepAngle = 2 * angleHalf
            canvas.drawArc(mArcRectF, startAngle.toFloat(), sweepAngle.toFloat(), false, mOrangePaint)
        } else {
            //矩形半圆
            canvas.drawArc(mArcRectF, 90f, 180f, false, mOrangePaint)
            //矩形进度
            mOrangeRectF.left = mArcRightLocation.toFloat()
            Log.d("xdsss", mOrangeRectF.left.toString())
            mOrangeRectF.right = mCurrentProgressPosition.toFloat()
            Log.d("xdsss", mOrangeRectF.right.toString())
            canvas.drawRect(mOrangeRectF, mOrangePaint)
        }
    }


    private inner class Leaf {
        val random = Random()
        private val MAX_LEAFS = 6
        private var mAddTime = 0

        //static
        var type: StartType = StartType.LITTLE//振幅类型 大中小
        var startTime: Long = 0//
        var rotateDirection = 0//旋转方向 正反

        // dynamic
        var x: Float = 0.0f
        var y: Float = 0.0f
        var rotateAngle = 0

        // 生成一个叶子信息
        fun generateLeaf(): Leaf {
            val leaf = Leaf()
            val randomType = random.nextInt(3)
            // 随时类型－ 随机振幅
            var type = StartType.MIDDLE
            when (randomType) {
                0 -> {
                }
                1 -> type = StartType.LITTLE
                2 -> type = StartType.BIG
                else -> {
                }
            }
            leaf.type = type
            // 随机起始的旋转角度
            leaf.rotateAngle = random.nextInt(360)
            // 随机旋转方向（顺时针或逆时针）
            leaf.rotateDirection = random.nextInt(2)
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            mAddTime += random.nextInt((LEAF_FLOAT_TIME * 1.5).toInt())
            leaf.startTime = System.currentTimeMillis() + mAddTime
            return leaf
        }

        // 根据最大叶子数产生叶子信息
        fun generateLeafs(): List<Leaf> {
            return generateLeafs(MAX_LEAFS)
        }

        // 根据传入的叶子数量产生叶子信息
        fun generateLeafs(leafSize: Int): List<Leaf> {
            val leafs: MutableList<Leaf> = LinkedList()
            for (i in 0 until leafSize) {
                leafs.add(generateLeaf())
            }
            return leafs
        }

    }

    private enum class StartType {
        LITTLE, MIDDLE, BIG
    }


    /**
     * 设置中等振幅
     *
     * @param amplitude
     */
    fun setAmplitude(amplitude: Int) {
        this.mMiddleAmplitude = amplitude
    }

    /**
     * 设置振幅差
     *
     * @param disparity
     */
    fun setDisparity(disparity: Int) {
        this.mAmplitudeDisparity = disparity
    }

    /**
     * 获取中等振幅
     *
     */
    fun getMiddleAmplitude(): Int {
        return mMiddleAmplitude
    }

    /**
     * 获取振幅差
     *
     */
    fun getMplitudeDisparity(): Int {
        return mAmplitudeDisparity
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    fun setProgress(progress: Int) {
        mProgress = progress
        postInvalidate()
    }

    /**
     * 设置叶子飘完一个周期所花的时间
     *
     * @param time
     */
    fun setFloatTime(time: Long) {
        mLeafFloatTime = time
    }

    /**
     * 设置叶子旋转一周所花的时间
     *
     * @param time
     */
    fun setRotateTime(time: Long) {
        mLeafRotateTime = time
    }

    /**
     * 获取叶子飘完一个周期所花的时间
     */
    fun getLeafFloatTime(): Long {
        mLeafFloatTime = if (mLeafFloatTime == 0L) LEAF_FLOAT_TIME else mLeafFloatTime
        return mLeafFloatTime
    }

    /**
     * 获取叶子旋转一周所花的时间
     */
    fun getLeafRotateTime(): Long {
        mLeafRotateTime = if (mLeafRotateTime == 0L) LEAF_ROTATE_TIME else mLeafRotateTime
        return mLeafRotateTime
    }
}