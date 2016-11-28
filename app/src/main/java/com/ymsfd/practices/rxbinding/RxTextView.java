package com.ymsfd.practices.rxbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import io.reactivex.Observable;

import static com.ymsfd.practices.infrastructure.util.Preconditions.checkNotNull;

public class RxTextView {
    @CheckResult
    @NonNull
    public static Observable<CharSequence> textChanges(@NonNull TextView view) {
        checkNotNull(view, "view == null");
        return Observable.create(new TextViewTextOnSubscribe(view));
    }
}
