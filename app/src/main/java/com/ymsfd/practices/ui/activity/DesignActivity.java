package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ymsfd.practices.R;

/**
 * Created by WoodenTea.
 * User: ymsfd
 * Date: 6/10/15
 * Time: 09:46
 */
public class DesignActivity extends BaseActivity {
    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.design_activity);
        enableToolbarUp(true);
        final TextInputLayout textInputLayout = findViewById(R.id.til_pwd);
        EditText editText = textInputLayout.getEditText();
        textInputLayout.setHint("Password");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 4) {
                    textInputLayout.setError("Password error");
                    textInputLayout.setErrorEnabled(true);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Snackbar.make(findViewById(R.id.til_pwd), "Snackbar comes out", Snackbar.LENGTH_LONG)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(
                                DesignActivity.this,
                                "Toast comes out",
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();

        return true;
    }
}
