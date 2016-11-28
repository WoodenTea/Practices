package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.rxlife.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

public class RxBaseActivity extends BaseActivity {
    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

    public <T> ObservableTransformer<T, T> bindUntilEvent(final Integer bindEvent) {
        final Observable<Integer> observable = lifecycleSubject.filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer event) {
                return event.equals(bindEvent);
            }
        });

        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> sourceOb) {
                return sourceOb.takeUntil(observable);
            }
        };
    }
}
