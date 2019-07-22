package com.loner.android.sdk.utils

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

import java.util.Timer
import java.util.TimerTask


// TODO: Auto-generated Javadoc

/**
 * The Class handle sliderUnlockWidget event like onSliderUnlock and onSliderTouched
 */
class SliderUnlockWidget : android.support.v7.widget.AppCompatSeekBar, OnSeekBarChangeListener {
    private var originalProgress: Int = 0
    private var redComponentView: View? = null

    /** The slider unlock widget listener.  */
    /**
     * Gets the on slider unlock listener.
     *
     * @return the on slider unlock listener
     */
    private var onSliderUnlockListener: SliderUnlockWidgetListener? = null

    /** The ctx.  */
    private var ctx: Context? = null

    private var sliderCounter: Int = 0

    private var timer: Timer? = null

    private var myTimerTask: MyTimerTask? = null

    /**
     * The listener interface for receiving sliderUnlockWidget events.
     * The class that is interested in processing a sliderUnlockWidget
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's `addSliderUnlockWidgetListener` method. When
     * the sliderUnlockWidget event occurs, that object's appropriate
     * method is invoked.
     *
    `` */
    interface SliderUnlockWidgetListener {

        /**
         * On slider unlock.
         *
         * @param sliderUnlockWidget the slider unlock widget
         */
        fun onSliderUnlock(sliderUnlockWidget: SliderUnlockWidget)

        fun onSliderTouched()
    }

    /**
     * Instantiates a new slider unlock widget.
     *
     * @param context the context
     * @param attrs the attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        ctx = context
        init()
    }


    fun setRedComponentView(redComponentView: View) {
        this.redComponentView = redComponentView
    }

    /**
     * Instantiates a new slider unlock widget.
     *
     * @param context the context
     */
    constructor(context: Context) : super(context) {
        ctx = context
        init()
    }


    internal inner class MyTimerTask : TimerTask() {
        override fun run() {
            if (sliderCounter == 0) {
                stopTimer()
            } else {
                sliderCounter -= 1
            }
        }
    }

    /**
     * Init the.
     */
    private fun init() {
        //		setProgressDrawable(ctx.getResources().getDrawable(R.drawable.progress));
        setOnSeekBarChangeListener(this)
        isSliderTouchedUp = false
        timer = Timer()

        if (timer != null) {
            timer!!.cancel()
        }
        timer = Timer()
        myTimerTask = MyTimerTask()
        sliderCounter = 2
    }

    fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
        onSliderUnlockListener!!.onSliderTouched()
    }

    /**
     * Reset.
     */
    fun reset() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val progress = progress

                if (progress > 3) {
                    handler.postDelayed(this, 1)
                    setProgress(progress - 3)
                } else {
                    setProgress(0)
                    handler.removeCallbacks(this)
                }
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // TODO Auto-generated method stub

        if (event.action == MotionEvent.ACTION_DOWN) {// pressed
            isSliderTouchedUp = false
            timer = Timer()
            myTimerTask = MyTimerTask()
            timer!!.schedule(myTimerTask, 0, 1000)
        } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            isSliderTouchedUp = true
            sliderCounter = 2
            if (timer != null) {
                timer!!.cancel()
                timer = null
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * Sets the slider unlock listener.
     *
     * @param onSliderUnlockListener the new slider unlock listener
     */
    fun setSliderUnlockListener(
            onSliderUnlockListener: SliderUnlockWidgetListener) {
        this.onSliderUnlockListener = onSliderUnlockListener
    }


    /* (non-Javadoc)
	 * @see android.widget.SeekBar.OnSeekBarChangeListener#onProgressChanged(android.widget.SeekBar, int, boolean)
	 */
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            // only allow changes by 1 up or down
            if (progress > originalProgress + 10 || progress < originalProgress - 10) {
                seekBar.progress = originalProgress

            } else {
                originalProgress = progress
                updateTheRedComponent(progress)

            }
        }

        if (getProgress() == max && getProgress() != 0) {
            originalProgress = 0
            onSliderUnlockListener!!.onSliderUnlock(this)
        }


    }

    fun updateTheRedComponent(seekBarprogress: Int) {
        val params = redComponentView!!.layoutParams
        val dpValue = getTheDPValueBasedSeekBar(seekBarprogress).toInt()
        val newOne = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(), resources.displayMetrics).toInt()
        params.width = newOne
        redComponentView!!.layoutParams = params
    }

    private fun getTheDPValueBasedSeekBar(progress: Int): Double {
        val progressValue = progress * 0.0105
        val completeDpValue = 350f
        return completeDpValue * progressValue
    }

    /* (non-Javadoc)
	 * @see android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(android.widget.SeekBar)
	 */
    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    /* (non-Javadoc)
	 * @see android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android.widget.SeekBar)
	 */
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        originalProgress = seekBar.progress
        if (seekBar.progress < 96) {
            originalProgress = 0
            progress = 0
            updateTheRedComponent(5)
        } else {
            progress = 100
            updateTheRedComponent(5)
        }
    }

    companion object {

        var isSliderTouchedUp: Boolean = false
    }
}

