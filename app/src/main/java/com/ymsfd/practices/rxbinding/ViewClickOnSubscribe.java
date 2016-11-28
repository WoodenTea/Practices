package com.ymsfd.practices.rxbinding;

import android.view.View;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.MainThreadDisposable;

import static io.reactivex.android.MainThreadDisposable.verifyMainThread;

final class ViewClickOnSubscribe implements ObservableOnSubscribe<Void> {
    private final View view;

    ViewClickOnSubscribe(View view) {
        this.view = view;
    }

    @Override
    public void subscribe(final ObservableEmitter<Void> e) throws Exception {
        verifyMainThread();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e.isDisposed()) {
                    e.onNext(null);
                }
            }
        };

        e.setDisposable(new MainThreadDisposable() {
            @Override
            protected void onDispose() {
                view.setOnClickListener(null);
            }
        });

        view.setOnClickListener(listener);
    }
}
