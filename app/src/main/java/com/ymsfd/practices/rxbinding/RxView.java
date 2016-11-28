package com.ymsfd.practices.rxbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;

import static com.ymsfd.practices.infrastructure.util.Preconditions.checkNotNull;

public class RxView {
    @CheckResult
    @NonNull
    public static Observable<Void> clicks(@NonNull View view) {
        checkNotNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }
}
