package com.ymsfd.practices.ui.activity.spring

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.MotionEvent
import com.ymsfd.practices.R
import com.ymsfd.practices.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_rotation.*

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/19
 */
class RotationActivity : BaseActivity() {
    private companion object Param {
        val INITIAL_ROTATION = 0f
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATION = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var rotationAnimation: SpringAnimation

    override fun startup(savedInstanceState: Bundle?): Boolean {
        if (!super.startup(savedInstanceState)) {
            return false
        }

        setContentView(R.layout.activity_rotation)
        enableToolbarUp(true)

        updateRotationText()
        rotationAnimation = createSpringAnimation(rotatingView, SpringAnimation.ROTATION,
                INITIAL_ROTATION, STIFFNESS, DAMPING_RATION)
        rotationAnimation.addUpdateListener { _, _, _ -> updateRotationText() }

        var previousRotation: Float
        var currentRotation = 0f

        rotatingView.setOnTouchListener { view, event ->
            val currentX = view.width / 2.0
            val currentY = view.height / 2.0
            val x = event.x
            val y = event.y

            fun updateCurrentRotation() {
                currentRotation = view.rotation + Math.toDegrees(Math.atan2(x - currentX, currentY - y)).toFloat()
            }

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    rotationAnimation.cancel()
                    updateCurrentRotation()
                }
                MotionEvent.ACTION_MOVE -> {
                    previousRotation = currentRotation
                    updateCurrentRotation()

                    val angel = currentRotation - previousRotation
                    view.rotation += angel

                    updateRotationText()
                }
                MotionEvent.ACTION_UP -> {
                    rotationAnimation.start()
                }
            }
            true
        }

        return true
    }

    private fun updateRotationText() {
        rotationTextView.text = String.format("%.3f", rotatingView.rotation)
    }
}