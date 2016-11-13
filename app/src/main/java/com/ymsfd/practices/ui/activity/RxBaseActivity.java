package com.ymsfd.practices.ui.activity;

import android.os.Bundle;

import com.ymsfd.practices.rxlife.ActivityEvent;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class RxBaseActivity extends BaseActivity {
    protected final BehaviorSubject<Integer> lifecycleSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
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
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    public <T> Observable.Transformer<T, T> bindUntilEvent(final Integer bindEvent) {
        final Observable<Integer> observable = lifecycleSubject.takeFirst(new Func1<Integer,
                Boolean>() {
            @Override
            public Boolean call(Integer event) {
                return event.equals(bindEvent);
            }
        });

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceOb) {
                return sourceOb.takeUntil(observable);
            }
        };
    }
}
