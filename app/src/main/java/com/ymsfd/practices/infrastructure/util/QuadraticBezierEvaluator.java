package com.ymsfd.practices.infrastructure.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2018/4/12
 * <p>
 * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
 * t  曲线长度比例
 * p0 起始点
 * p1 控制点
 * p2 终止点
 * B(t) t对应的点
 */
public class QuadraticBezierEvaluator implements TypeEvaluator<PointF> {
    private PointF mResultPointF = new PointF();
    private PointF mControlPointF;

    public QuadraticBezierEvaluator(PointF pointF) {
        this.mControlPointF = pointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float oneMinusT = 1 - fraction;
        mResultPointF.x = oneMinusT * oneMinusT * startValue.x + 2 * fraction * oneMinusT *
                mControlPointF.x + fraction * fraction * endValue.x;
        mResultPointF.y = oneMinusT * oneMinusT * startValue.y + 2 * fraction * oneMinusT *
                mControlPointF.y + fraction * fraction * endValue.y;
        return mResultPointF;
    }
}
