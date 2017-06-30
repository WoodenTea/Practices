package com.ymsfd.practices.spring

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.MotionEvent
import android.view.ViewTreeObserver
import com.ymsfd.practices.R
import com.ymsfd.practices.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_position.*

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/19
 */
class PositionActivity : BaseActivity() {
    private companion object Params {
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var xAnimation: SpringAnimation
    lateinit var yAnimation: SpringAnimation

    override fun _onCreate(savedInstanceState: Bundle?): Boolean {
        if (!super._onCreate(savedInstanceState)) {
            return false
        }

        setContentView(R.layout.activity_position)
        enableToolbarUp(true)

        movingView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                xAnimation = createSpringAnimation(movingView, SpringAnimation.X, movingView.x, STIFFNESS, DAMPING_RATIO)
                yAnimation = createSpringAnimation(movingView, SpringAnimation.Y, movingView.y, STIFFNESS, DAMPING_RATIO)
                movingView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        var dX = 0f
        var dY = 0f
        movingView.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY

                    xAnimation.cancel()
                    yAnimation.cancel()
                }
                MotionEvent.ACTION_MOVE -> {
                    movingView.animate()
                            .x(event.rawX + dX)
                            .y(event.rawY + dY)
                            .setDuration(0)
                            .start()
                }
                MotionEvent.ACTION_UP -> {
                    xAnimation.start()
                    yAnimation.start()
                }
            }

            true
        }

        return true
    }
}