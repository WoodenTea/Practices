package com.ymsfd.practices.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.infrastructure.util.Utils;

public class AnimatorActivity extends BaseActivity implements View.OnClickListener {
    private ObjectAnimator scaleXAnimator, translateXAnimator, alphaAnimator, translateAnimator;
    private AnimatorSet animatorScaleSet, animationSet;
    private ValueAnimator mValueAnimator;
    private ValueAnimator bezierValueAnimator;
    private Point metricsPoint;

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.animator_activity);
        enableToolbarUp(true);

        metricsPoint = Utils.displaySize(this);
        final Button bezier = findViewById(R.id.bezier);
        bezier.setOnClickListener(this);

        bezierValueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), new PointF(0, 0),
                new PointF(metricsPoint.x, metricsPoint.y));
        bezierValueAnimator.setDuration(2000);
        bezierValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                bezier.setX(pointF.x);
                bezier.setY(pointF.y);
            }
        });
        bezierValueAnimator.setTarget(bezier);
        bezierValueAnimator.setRepeatCount(1);
        bezierValueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ImageView cartoonImage = findViewById(R.id.cartoon_image);

        View container = findViewById(R.id.container);
        ObjectAnimator animator = ObjectAnimator.ofInt(container, "backgroundColor", 0xFFFF0000,
                0xFFFF00FF);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(cartoonImage, "translationX",
                0.0f, 300.0f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(cartoonImage, "scaleX", 1.0f, 2.0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(cartoonImage, "rotationX", 0.0f, 90.0f,
                0.0F);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(cartoonImage, "alpha", 1.0f, 0.2f, 1.0F);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(cartoonImage, "scaleX", 2.0f, 1.0f);
        ObjectAnimator animator6 = ObjectAnimator.ofInt(container, "backgroundColor", 0xFFFFFFFF);
        AnimatorSet animatorSet = new AnimatorSet();
        ((animatorSet.play(animator).with(animator1).before(animator2)).before(animator3)).after
                (animator4);
        animatorSet.play(animator5).after(animator3);
        animatorSet.play(animator6).after(animator5);
        animatorSet.setDuration(5000);
        animatorSet.start();

        setupAnimator();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha:
                alphaAnimator.start();
                break;
            case R.id.scale:
                animatorScaleSet.start();
                break;
            case R.id.scaleX:
                scaleXAnimator.start();
                break;
            case R.id.translateX:
                translateXAnimator.start();
                break;
            case R.id.propertyValHolder:
                translateAnimator.start();
                break;
            case R.id.set:
                animationSet.start();
                break;
            case R.id.animator:
                mValueAnimator.start();
                break;
            case R.id.propertyAnimator:
                v.animate().translationX(100f).alpha(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        final Button button = findViewById(R.id.propertyAnimator);
                        button.animate().alpha(1).translationX(0f).start();
                    }
                }).start();
                break;
            case R.id.bezier:
                bezierValueAnimator.start();
                break;
            default:
                break;

        }
    }

    private void setupAnimator() {
        Button button = findViewById(R.id.scaleX);
        button.setOnClickListener(this);
        scaleXAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.scalex);
        scaleXAnimator.setTarget(button);

        button = findViewById(R.id.scale);
        button.setOnClickListener(this);
        animatorScaleSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.scale);
        animatorScaleSet.setTarget(button);

        button = findViewById(R.id.translateX);
        button.setOnClickListener(this);
        translateXAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.translatex);
        translateXAnimator.setTarget(button);

        button = findViewById(R.id.alpha);
        button.setOnClickListener(this);
        alphaAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.alpha);
        alphaAnimator.setTarget(button);

        Button buttonProValHolder;
        buttonProValHolder = findViewById(R.id.propertyValHolder);
        buttonProValHolder.setOnClickListener(this);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", 0f, 300f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 0f, 300f);
        translateAnimator = ObjectAnimator.ofPropertyValuesHolder(buttonProValHolder, pvhX, pvhY);
        translateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        translateAnimator.setRepeatCount(1);
        translateAnimator.setDuration(2000);

        button = findViewById(R.id.propertyAnimator);
        button.setOnClickListener(this);

        button = findViewById(R.id.set);
        button.setOnClickListener(this);
        animationSet = new AnimatorSet();
        animationSet.playTogether(ObjectAnimator.ofFloat(button, "alpha", 1, 0, 1),
                ObjectAnimator.ofFloat(button, "translationX", 0f, 400f, 0f), ObjectAnimator
                        .ofFloat(button, "rotation", 0, 180, 360));
        animationSet.setDuration(1000);
        animationSet.setTarget(button);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
                D("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                D("onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                D("onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                D("onAnimationRepeat");
            }
        });

        final Button buttonValueAnimator = findViewById(R.id.animator);
        buttonValueAnimator.setOnClickListener(this);

        mValueAnimator = ValueAnimator.ofInt(1, 100);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float factor = valueAnimator.getAnimatedFraction();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)
                        buttonValueAnimator.getLayoutParams();
                marginLayoutParams.leftMargin = (int) (factor * 100);
                buttonValueAnimator.setLayoutParams(marginLayoutParams);
            }
        });
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(1);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setTarget(buttonValueAnimator);
    }

    /**
     * 三阶贝塞尔曲线
     * B(t) = P0(1-t)^3 + 3P1t(1-t)^2 + 3P2t^2(1-t) + P3t^3, t∈[0,1]
     */
    class BezierEvaluator implements TypeEvaluator<PointF> {

        private PointF point = new PointF();
        private PointF point1 = new PointF(metricsPoint.x, 0);
        private PointF point2 = new PointF(0, metricsPoint.y);

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            D("Fraction: " + fraction + " Start->" + startValue + " End->" + endValue);
            float oneMinusT = 1.0f - fraction;

            point.x = startValue.x * (float) Math.pow(oneMinusT, 3.f) +
                    3 * point1.x * fraction * (float) Math.pow(oneMinusT, 2.f) +
                    3 * point2.x * (float) Math.pow(fraction, 2.f) * oneMinusT +
                    endValue.x * (float) Math.pow(fraction, 3.f);

            point.y = startValue.y * (float) Math.pow(oneMinusT, 3.f) +
                    3 * fraction * point1.y * (float) Math.pow(oneMinusT, 2.f) +
                    3 * point2.y * (float) Math.pow(fraction, 2.f) * oneMinusT +
                    endValue.y * (float) Math.pow(fraction, 3.f);

            return point;
        }
    }
}