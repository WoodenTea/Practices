package com.ymsfd.practices.ui.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.ymsfd.practices.R;
import com.ymsfd.practices.databinding.DataBindingActivityBinding;
import com.ymsfd.practices.domain.UserEntity;
import com.ymsfd.practices.ui.activity.BaseTranslucentActivity;

/**
 * Created by WoodenTea.
 * Date: 2016/6/30
 * Time: 15:17
 */
public class DataBindingActivity extends BaseTranslucentActivity {
    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        DataBindingActivityBinding binding = DataBindingUtil.setContentView(this, R.layout
                .data_binding_activity);

        setUpActionBar(true);
        UserEntity user = new UserEntity("First", "Last");
        binding.setUser(user);

        ClickHandler handlers = new ClickHandler();
        binding.setHandlers(handlers);

        Presenter presenter = new Presenter();
        binding.setPresenter(presenter);

        Task task = new Task();
        binding.setTask(task);

        return true;
    }
}
