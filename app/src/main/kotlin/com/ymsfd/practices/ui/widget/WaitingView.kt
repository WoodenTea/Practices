package com.ymsfd.practices.ui.widget

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.ymsfd.practices.R
import com.ymsfd.practices.infrastructure.util.QuadraticBezierEvaluator
import com.ymsfd.practices.ui.widget.util.ShapeHolder
import kotlin.math.abs

/**
 * Description:
 * Author: WoodenTea
 * Date: 2018/4/12
 */
class WaitingView : View, View.OnClickListener {
    private val circles = ArrayList<ShapeHolder>()
    private var radius = 0f
    private val paint = Paint()
    private val center = PointF(110f, 110f)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val res = context.resources
        val defaultColor = ContextCompat.getColor(context, R.color.default_waiting_color)
        val defaultRadius = res.getDimension(R.dimen.default_waiting_radius)

        val array = context.obtainStyledAttributes(attrs, R.styleable.WaitingView,
                defStyleAttr, 0)
        radius = array.getDimension(R.styleable.WaitingView_radius, defaultRadius)
        var color = array.getColor(R.styleable.WaitingView_color, defaultColor)
        array.recycle()

        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val height: Int
        val width: Int

        width = if (widthMode == View.MeasureSpec.EXACTLY) {
            widthSize
        } else {
            (2 * radius * 2 + paddingLeft + paddingRight).toInt()
        }

        height = if (heightMode == View.MeasureSpec.EXACTLY) {
            heightSize
        } else {
            (2 * radius * 2 + paddingTop + paddingBottom).toInt()
        }

        radius = 100f

        setMeasuredDimension(220, 220)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(center.x, center.y, radius, paint)
        for (index in circles.indices) {
            val shapeHolder = circles[index]
            canvas.save()
            canvas.translate(shapeHolder.getX(), shapeHolder.getY())
            shapeHolder.getShape().draw(canvas)
            canvas.restore()
        }
    }

    override fun onClick(view: View) {
        val evaluator = QuadraticBezierEvaluator(PointF(radius + radius / 2, radius - (radius / 2) * Math.sqrt(3.0).toFloat()))
        val animator = ValueAnimator.ofObject(evaluator,
                PointF(200f, 100f),
                PointF(radius - radius / 2, radius - (radius / 2) * Math.sqrt(3.0).toFloat()))
        animator.duration = 600
        animator.addUpdateListener { valueAnimator ->
            val pointF: PointF = valueAnimator.animatedValue as PointF
            circles[0].setX(pointF.x)
            circles[0].setY(pointF.y)

            invalidate()
        }
        animator.interpolator = AccelerateDecelerateInterpolator()

        val evaluator2 = QuadraticBezierEvaluator(PointF(0f, 100f))
        val animator2 = ValueAnimator.ofObject(evaluator2,
                PointF(radius - radius / 2, radius - (radius / 2) * Math.sqrt(3.0).toFloat()),
                PointF(radius - radius / 2, radius + (radius / 2) * Math.sqrt(3.0).toFloat())
        )
        animator2.duration = 600
        animator2.addUpdateListener { valueAnimator ->
            val pointF: PointF = valueAnimator.animatedValue as PointF
            circles[0].setX(pointF.x)
            circles[0].setY(pointF.y)

            invalidate()
        }
        animator2.interpolator = AccelerateDecelerateInterpolator()

        val evaluator3 = QuadraticBezierEvaluator(PointF(radius + radius / 2, radius + (radius / 2) * Math.sqrt(3.0).toFloat()))
        val animator3 = ValueAnimator.ofObject(evaluator3,
                PointF(radius - radius / 2, radius + (radius / 2) * Math.sqrt(3.0).toFloat()),
                PointF(200f, 100f)
        )
        animator3.duration = 600
        animator3.addUpdateListener { valueAnimator ->
            val pointF: PointF = valueAnimator.animatedValue as PointF
            circles[0].setX(pointF.x)
            circles[0].setY(pointF.y)

            invalidate()
        }
        animator3.interpolator = AccelerateDecelerateInterpolator()

        val set = AnimatorSet()
//        set.playSequentially(animator, animator2, animator3)
        (set.play(animator2).before(animator3)).after(animator)

        set.start()
    }

    private fun init() {
        setOnClickListener(this)
        paint.isAntiAlias = true
        this.paint.style = Paint.Style.STROKE
        addCircle(center.x, 0f)
        addCircle(5f, center.y - 10)
        addCircle(195f, center.y - 10)
    }

    private fun addCircle(x: Float, y: Float): ShapeHolder {
        val circle = OvalShape()
        circle.resize(radius, radius)
        val drawable = ShapeDrawable(circle)
        val holder = ShapeHolder(drawable)
        holder.setX(x)
        holder.setY(y)

        val red = (100 + Math.random() * 155).toInt()
        val green = (100 + Math.random() * 155).toInt()
        val blue = (100 + Math.random() * 155).toInt()
        val color = -0x1000000 or (red shl 16) or (green shl 8) or blue
        val paint = drawable.paint
        val darkColor = -0x1000000 or (red / 4 shl 16) or (green / 4 shl 8) or blue / 4
        val gradient = RadialGradient(12.5f, 6.5f, 20f, color, darkColor, Shader.TileMode.CLAMP)
        paint.shader = gradient
        holder.setPaint(paint)
        circles.add(holder)

        return holder
    }
}