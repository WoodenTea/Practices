package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * User: ymsfd
 * Date: 5/25/15
 * Time: 10:27
 */
public class SceneActivity extends BaseActivity {
    private Scene scene1;
    private Scene scene2;
    private TransitionManager mTransitionManager;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_scene);
        ViewGroup rootContainer =
                (ViewGroup) findViewById(R.id.rootContainer);

        TransitionInflater transitionInflater = TransitionInflater.from(this);
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transition,
                rootContainer);

        scene1 = Scene.getSceneForLayout(rootContainer,
                R.layout.scene_layout_1, this);
        scene2 = Scene.getSceneForLayout(rootContainer, R.layout.scene_layout_2, this);
        scene1.enter();

        return true;
    }

    public void goToScene2(View view) {
        mTransitionManager.transitionTo(scene2);
    }

    public void goToScene1(View view) {
        mTransitionManager.transitionTo(scene1);
    }
}
