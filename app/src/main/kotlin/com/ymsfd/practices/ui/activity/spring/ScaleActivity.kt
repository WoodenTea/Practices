package com.ymsfd.practices.ui.activity.spring

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.ymsfd.practices.R
import com.ymsfd.practices.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_scale.*

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/19
 */
class ScaleActivity : BaseActivity() {
    private companion object Param {
        val INITIAL_SCALE = 1f
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATION = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var scaleXAnimation: SpringAnimation
    lateinit var scaleYAnimation: SpringAnimation
    lateinit var scaleGestureDetector: ScaleGestureDetector

    override fun startup(savedInstanceState: Bundle?): Boolean {
        if (!super.startup(savedInstanceState)) {
            return false
        }

        setContentView(R.layout.activity_scale)
        enableToolbarUp(true)

        updateScaleText()

        scaleXAnimation = createSpringAnimation(scalingView, SpringAnimation.SCALE_X, INITIAL_SCALE, STIFFNESS, DAMPING_RATION)
        scaleYAnimation = createSpringAnimation(scalingView, SpringAnimation.SCALE_Y, INITIAL_SCALE, STIFFNESS, DAMPING_RATION)
        scaleXAnimation.addUpdateListener { _, _, _ -> updateScaleText() }

        setupPinchToZoom()

        scalingView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                scaleXAnimation.start()
                scaleYAnimation.start()
            } else {
                scaleXAnimation.cancel()
                scaleYAnimation.cancel()

                scaleGestureDetector.onTouchEvent(event)
            }

            true
        }

        return true
    }

    private fun setupPinchToZoom() {
        var scaleFactor = 1f
        scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        scaleFactor *= detector.scaleFactor
                        scalingView.scaleX *= scaleFactor
                        scalingView.scaleY *= scaleFactor
                        updateScaleText()
                        return true
                    }
                })
    }

    private fun updateScaleText() {
        scaleTextView.text = String.format("%.3f", scalingView.scaleX)
    }
}