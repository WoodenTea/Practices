package com.ymsfd.practices.rxbinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.MainThreadDisposable;

import static io.reactivex.android.MainThreadDisposable.verifyMainThread;

final class TextViewTextOnSubscribe implements ObservableOnSubscribe<CharSequence> {
    final private TextView view;

    TextViewTextOnSubscribe(TextView view) {
        this.view = view;
    }

    @Override
    public void subscribe(final ObservableEmitter<CharSequence> e) throws Exception {
        verifyMainThread();

        final TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!e.isDisposed()) {
                    e.onNext(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        e.setDisposable(new MainThreadDisposable() {
            @Override
            protected void onDispose() {
                view.removeTextChangedListener(watcher);
            }
        });

        view.addTextChangedListener(watcher);

        // Emit initial value.
        e.onNext(view.getText());
    }
}
