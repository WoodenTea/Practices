package com.ymsfd.practices.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ymsfd.practices.R;

public class AnimatorActivity extends BaseActivity implements View.OnClickListener {
    private ObjectAnimator scaleXAnimator, translateXAnimator, alphaAnimator,
            translateAnimator;
    private AnimatorSet animatorScaleSet, animationSet;
    private ValueAnimator mValueAnimator;
    private ValueAnimator bezierValueAnimator;
    private DisplayMetrics displayMetrics;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_animator);
        displayMetrics = getResources().getDisplayMetrics();
        final Button bezier = (Button) findViewById(R.id.bezier);
        bezier.setOnClickListener(this);

        bezierValueAnimator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(0, 0), new PointF(displayMetrics.widthPixels, displayMetrics.heightPixels));
        bezierValueAnimator.setDuration(2000);
        bezierValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                bezier.setX(pointF.x);
                bezier.setY(pointF.y);
            }
        });
        bezierValueAnimator.setTarget(bezier);
        bezierValueAnimator.setRepeatCount(1);
        bezierValueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        setupAnimator();
        return true;
    }

    private void setupAnimator() {
        Button button = (Button) findViewById(R.id.scaleX);
        button.setOnClickListener(this);
        scaleXAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.scalex);
        scaleXAnimator.setTarget(button);

        button = (Button) findViewById(R.id.scale);
        button.setOnClickListener(this);
        animatorScaleSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.scale);
        animatorScaleSet.setTarget(button);

        button = (Button) findViewById(R.id.translateX);
        button.setOnClickListener(this);
        translateXAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(
                this, R.animator.translatex);
        translateXAnimator.setTarget(button);

        button = (Button) findViewById(R.id.alpha);
        button.setOnClickListener(this);
        alphaAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.alpha);
        alphaAnimator.setTarget(button);

        Button buttonProValHolder;
        buttonProValHolder = (Button) findViewById(R.id.propertyValHolder);
        buttonProValHolder.setOnClickListener(this);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(
                "translationX", 0f, 300f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(
                "translationY", 0f, 300f);
        translateAnimator = ObjectAnimator.ofPropertyValuesHolder(
                buttonProValHolder, pvhX, pvhY);
        translateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        translateAnimator.setRepeatCount(1);
        translateAnimator.setDuration(2000);

        button = (Button) findViewById(R.id.propertyAnimator);
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.set);
        button.setOnClickListener(this);
        animationSet = new AnimatorSet();
        animationSet
                .playTogether(ObjectAnimator.ofFloat(button, "alpha", 1, 0,
                        1), ObjectAnimator.ofFloat(button, "translationX",
                        0f, 400f, 0f), ObjectAnimator.ofFloat(button,
                        "rotation", 0, 180, 360));
        animationSet.setDuration(1000);
        animationSet.setTarget(button);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
                D("onAnimationStart");
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                D("onAnimationRepeat");
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                D("onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                D("onAnimationCancel");
            }
        });

        final Button buttonValueAnimator = (Button) findViewById(R.id.animator);
        buttonValueAnimator.setOnClickListener(this);

        mValueAnimator = ValueAnimator.ofInt(1, 100);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator va) {
                float factor = va.getAnimatedFraction();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) buttonValueAnimator
                        .getLayoutParams();
                marginLayoutParams.leftMargin = (int) (factor * 100);
                buttonValueAnimator.setLayoutParams(marginLayoutParams);
            }
        });
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(1);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setTarget(buttonValueAnimator);
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
                v.animate().translationX(100f).alpha(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                final Button button = (Button) findViewById(R.id.propertyAnimator);
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

    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            D("Fraction: " + fraction);
            float oneMinusT = 1.0f - fraction;
            PointF point = new PointF();

            PointF point1 = new PointF();
            point1.set(displayMetrics.widthPixels, 0);

            PointF point2 = new PointF();
            point2.set(0, displayMetrics.heightPixels);

            point.x = oneMinusT * oneMinusT * oneMinusT * (startValue.x) + 3
                    * oneMinusT * oneMinusT * fraction * (point1.x) + 3 * oneMinusT
                    * fraction * fraction * (point2.x) + fraction * fraction * fraction * (endValue.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (startValue.y) + 3
                    * oneMinusT * oneMinusT * fraction * (point1.y) + 3 * oneMinusT
                    * fraction * fraction * (point2.y) + fraction * fraction * fraction * (endValue.y);
            return point;
        }
    }
}