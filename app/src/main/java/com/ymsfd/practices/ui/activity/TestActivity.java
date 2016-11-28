package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ymsfd.practices.R;
import com.ymsfd.practices.rxbinding.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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
                .debounce(600, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        txtView.setText("");
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<CharSequence, Observable<CharSequence>>() {
                    @Override
                    public Observable<CharSequence> apply(CharSequence charSequence) {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return true;
    }
}