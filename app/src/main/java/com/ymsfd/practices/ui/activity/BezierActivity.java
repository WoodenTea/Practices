package com.ymsfd.practices.ui.activity;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Preconditions;
import com.ymsfd.practices.infrastructure.util.Utils;
import com.ymsfd.practices.infrastructure.util.WTLogger;

/**
 * Created by WoodenTea.
 * Date: 2016/7/6
 * Time: 10:33
 */
public class BezierActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.ripple_view_activity);
        setUpActionBar(true);

        imageView = (ImageView) findViewById(R.id.image_view);

        View view = findViewById(R.id.button);
        view.setOnClickListener(this);

        view = findViewById(R.id.button2);
        view.setOnClickListener(this);

        view = findViewById(R.id.button3);
        view.setOnClickListener(this);

        return true;
    }

    @Override
    public void onClick(View v) {
        final Point point = Utils.displaySize(this);
        Rect rect = new Rect();
        imageView.getGlobalVisibleRect(rect);
        ValueAnimator valueAnimator;

        if (v.getId() == R.id.button) {
            Path path = new Path();
            path.moveTo(0, 0);
            path.cubicTo(point.x / 3.f, -500, point.x * 2 / 3, 500, point.x, 0);

            final PathMeasure pathMeasure = new PathMeasure(path, false);
            final float[] pointF = new float[2];
            valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.setDuration(3000);
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    pathMeasure.getPosTan(value, pointF, null);
                    imageView.setTranslationX(pointF[0]);
                    imageView.setTranslationY(pointF[1]);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(valueAnimator);
            animatorSet.start();
        } else if (v.getId() == R.id.button2) {
            PointF pointF = new PointF(point.x / 3f, rect.top - 500);
            PointF pointF1 = new PointF(point.x * 2 / 3.f, rect.top + 500);
            PointF pointF2 = new PointF(0, rect.top);
            PointF pointF3 = new PointF(point.x, rect.top);
            valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(pointF, pointF1),
                    pointF2, pointF3);
            valueAnimator.setDuration(3000);
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                    Preconditions.checkNotNull(pointF);
                    imageView.setX(pointF.x);
                    imageView.setY(pointF.y);
                }
            });
            valueAnimator.start();
        } else if (v.getId() == R.id.button3) {
            PointF pointF = new PointF(0, 0);
            PointF pointF2 = new PointF(point.x - imageView.getWidth(), 0);
            valueAnimator = ValueAnimator.ofObject(new SineEvaluator(), pointF, pointF2);
            valueAnimator.setDuration(3000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                    Preconditions.checkNotNull(pointF);
                    imageView.setTranslationX(pointF.x);
                    imageView.setTranslationY(pointF.y);
                }
            });
            valueAnimator.start();
        }
    }

    class BezierEvaluator implements TypeEvaluator<PointF> {
        private PointF ctrlPointF;
        private PointF ctrlPointF2;
        private PointF point = new PointF();

        public BezierEvaluator(PointF ctrlPointF, PointF ctrlPointF2) {
            this.ctrlPointF = ctrlPointF;
            this.ctrlPointF2 = ctrlPointF2;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float oneMinusT = 1.0f - fraction;

            point.x = startValue.x * (float) Math.pow(oneMinusT, 3.f) +
                    3 * ctrlPointF.x * fraction * (float) Math.pow(oneMinusT, 2.f) +
                    3 * ctrlPointF2.x * (float) Math.pow(fraction, 2.f) * oneMinusT +
                    endValue.x * (float) Math.pow(fraction, 3.f);

            point.y = startValue.y * (float) Math.pow(oneMinusT, 3.f) +
                    3 * fraction * ctrlPointF.y * (float) Math.pow(oneMinusT, 2.f) +
                    3 * ctrlPointF2.y * (float) Math.pow(fraction, 2.f) * oneMinusT +
                    endValue.y * (float) Math.pow(fraction, 3.f);

            return point;
        }
    }

    /**
     * Sine
     * y = A * sin(ωx+φ) + k
     * A——振幅, 当物体作轨迹符合正弦曲线的直线往复运动时; 其值为行程的1/2。
     * (ωx+φ)——相位, 反映变量y所处的状态。
     * φ ——初相, x=0时的相位; 反映在坐标系上则为图像的左右移动。
     * k——偏距, 反映在坐标系上则为图像的上移或下移。
     * ω——角速度, 控制正弦周期(单位角度内震动的次数)。
     * T——振动周期, 2π / ω
     * f——振动频率, 1 / T
     */
    class SineEvaluator implements TypeEvaluator<PointF> {
        private PointF pointF = new PointF();

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = fraction * endValue.x;
            pointF.x = x;
            pointF.y = (float) (200.f * Math.sin(-0.03 * x));
            WTLogger.d(getClass().getSimpleName(), "Fraction->" + fraction +
                    " " + pointF.toString());
            return pointF;
        }
    }
}
