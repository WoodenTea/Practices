package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.ymsfd.practices.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ymsfdDev.
 * User: ymsfd
 * Date: 4/30/15
 * Time: 10:32
 */
public class TestActivity extends BaseActivity {

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.test_activity);
        enableToolbarHomeButton(true);

        EditText editText = (EditText) findViewById(R.id.edit);
        final TextView txtView = (TextView) findViewById(R.id.txt);
        RxTextView.textChanges(editText)
//                .debounce(600, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        txtView.setText("");
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<CharSequence, Observable<CharSequence>>() {
                    @Override
                    public Observable<CharSequence> call(CharSequence charSequence) {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
//                        txtView.setText(charSequence);
                    }
                });
        return true;
    }
}