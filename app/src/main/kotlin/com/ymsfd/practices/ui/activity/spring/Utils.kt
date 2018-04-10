package com.ymsfd.practices.ui.activity.spring

import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.annotation.FloatRange
import android.view.View

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/19
 */
fun createSpringAnimation(view: View,
                          property: DynamicAnimation.ViewProperty,
                          finalPosition: Float,
                          @FloatRange(from = 0.0) stiffness: Float,
                          @FloatRange(from = 0.0) dampingRation: Float): SpringAnimation {
    val animation = SpringAnimation(view, property)
    val spring = SpringForce(finalPosition)
    spring.stiffness = stiffness
    spring.dampingRatio = dampingRation
    animation.spring = spring

    return animation
}