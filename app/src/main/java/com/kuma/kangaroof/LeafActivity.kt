package com.kuma.kangaroof

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kuma.base.util.AnimationUtils.initRotateAnimation
import com.kuma.kangaroof.ui.LeafLoadingView
import java.lang.ref.WeakReference
import java.util.*

class LeafActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private class MyHandler(activity: LeafActivity?) : Handler() {
        //持有弱引用HandlerActivity，GC回收时会被回收掉
        private val mActivity: WeakReference<LeafActivity> = WeakReference<LeafActivity>(activity)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val activity: LeafActivity? = mActivity.get()
            if (activity != null) {
                when (msg.what) {
                    REFRESH_PROGRESS -> {
                        mProgress = if (mProgress > 100 || mProgress > 100) {
                            0
                        } else mProgress
                        mProgress += 1
                        //随机800ms以内刷新一次
                        activity.mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                Random().nextInt(360).toLong())
                        mLeafLoadingView.setProgress(mProgress)
                    }
                }
            }
        }
    }

    companion object {
        private const val REFRESH_PROGRESS = 0x10
        private var mProgress = 0
        lateinit var mLeafLoadingView: LeafLoadingView
    }

    private var mFanView: View? = null

    private var mAmplitudeSeekBar: SeekBar? = null
    private var mAmplitudeText: TextView? = null
    private var mDisparitySeekBar: SeekBar? = null
    private var mDisparityText: TextView? = null
    private var mFloatTimeSeekBar: SeekBar? = null
    private var mFloatTimeText: TextView? = null
    private var mRotateTimeSeekBar: SeekBar? = null
    private var mRotateTimeText: TextView? = null


    private val mHandler: MyHandler = MyHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaf)

        initViews()
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
    }

    override fun onDestroy() {
        super.onDestroy()
        mProgress = 0
    }

    private fun initViews() {
        mLeafLoadingView = findViewById<View>(R.id.leaf_loading) as LeafLoadingView

        mFanView = findViewById(R.id.fan_pic)
        val rotateAnimation: RotateAnimation = initRotateAnimation(false, 1500, true,
                Animation.INFINITE)
        mFanView?.startAnimation(rotateAnimation)


        mAmplitudeText = findViewById<View>(R.id.text_ampair) as TextView
        mAmplitudeText?.text = getString(R.string.current_mplitude,
                mLeafLoadingView.getMiddleAmplitude())
        mAmplitudeSeekBar = findViewById<View>(R.id.seekBar_ampair) as SeekBar
        mAmplitudeSeekBar?.setOnSeekBarChangeListener(this)
        mAmplitudeSeekBar?.progress = mLeafLoadingView.getMiddleAmplitude()
        mAmplitudeSeekBar?.max = 50

        mDisparityText = findViewById<View>(R.id.text_disparity) as TextView
        mDisparityText!!.text = getString(R.string.current_Disparity,
                mLeafLoadingView.getMplitudeDisparity())
        mDisparitySeekBar = findViewById<View>(R.id.seekBar_distance) as SeekBar
        mDisparitySeekBar?.setOnSeekBarChangeListener(this)
        mDisparitySeekBar?.progress = mLeafLoadingView.getMplitudeDisparity()
        mDisparitySeekBar?.max = 20

        mFloatTimeText = findViewById<View>(R.id.text_float_time) as TextView
        mFloatTimeSeekBar = findViewById<View>(R.id.seekBar_float_time) as SeekBar
        mFloatTimeSeekBar!!.setOnSeekBarChangeListener(this)
        mFloatTimeSeekBar!!.max = 5000
        mFloatTimeSeekBar!!.progress = mLeafLoadingView.getLeafFloatTime().toInt()
        mFloatTimeText!!.text = resources.getString(R.string.current_float_time,
                mLeafLoadingView.getLeafFloatTime())

        mRotateTimeText = findViewById<View>(R.id.text_rotate_time) as TextView
        mRotateTimeSeekBar = findViewById<View>(R.id.seekBar_rotate_time) as SeekBar
        mRotateTimeSeekBar!!.setOnSeekBarChangeListener(this)
        mRotateTimeSeekBar!!.max = 5000
        mRotateTimeSeekBar!!.progress = mLeafLoadingView.getLeafRotateTime().toInt()
        mRotateTimeText!!.text = resources.getString(R.string.current_float_time,
                mLeafLoadingView.getLeafRotateTime())
    }

    public fun increaseProgress(view: View) {
        mProgress += 5
        mProgress = if (mProgress > 100 || mProgress > 100) {
            0
        } else mProgress
        mLeafLoadingView.setProgress(mProgress)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when {
            seekBar === mAmplitudeSeekBar -> {
                mLeafLoadingView.setAmplitude(progress)
                mAmplitudeText?.text = getString(R.string.current_mplitude,
                        progress)
            }
            seekBar === mDisparitySeekBar -> {
                mLeafLoadingView.setDisparity(progress)
                mDisparityText?.text = getString(R.string.current_Disparity,
                        progress)
            }
            seekBar === mFloatTimeSeekBar -> {
                mLeafLoadingView.setFloatTime(progress.toLong())
                mFloatTimeText?.text = resources.getString(R.string.current_float_time,
                        progress)
            }
            seekBar === mRotateTimeSeekBar -> {
                mLeafLoadingView.setRotateTime(progress.toLong())
                mRotateTimeText?.text = resources.getString(R.string.current_rotate_time,
                        progress)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}