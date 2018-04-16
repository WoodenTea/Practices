package com.ymsfd.practices.ui.widget.util

import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.drawable.ShapeDrawable

/**
 * Description:
 * Author: WoodenTea
 * Date: 2018/4/12
 */
class ShapeHolder(drawable: ShapeDrawable) {
    private var x = 0f
    private var y = 0f
    private var color = 0
    private var alpha = 1f
    private var paint: Paint? = null
    private var gradient: RadialGradient? = null
    private var shapeDrawable: ShapeDrawable = drawable

    fun getPaint(): Paint? {
        return paint
    }

    fun setPaint(paint: Paint) {
        this.paint = paint
    }

    fun setX(x: Float) {
        this.x = x
    }

    fun getX(): Float {
        return x
    }

    fun setY(y: Float) {
        this.y = y
    }

    fun getY(): Float {
        return y
    }

    fun setColor(color: Int) {
        this.color = color
    }

    fun getColor(): Int {
        return color
    }

    fun setAlpha(alpha: Float) {
        this.alpha = alpha
    }

    fun getAlpha(): Float {
        return alpha
    }

    fun setWidth(width: Float) {
        val shape = shapeDrawable.shape
        shape.resize(width, shape.height)
    }

    fun getWidth(): Float {
        return shapeDrawable.shape.width
    }

    fun setHeight(height: Float) {
        val shape = shapeDrawable.shape
        shape.resize(shape.width, height)
    }

    fun getHeight(): Float {
        return shapeDrawable.shape.height
    }

    fun setShape(drawable: ShapeDrawable) {
        this.shapeDrawable = drawable
    }

    fun getShape(): ShapeDrawable {
        return shapeDrawable
    }

    fun setGradient(gradient: RadialGradient) {
        this.gradient = gradient
    }

    fun getGradient(): RadialGradient? {
        return gradient
    }
}