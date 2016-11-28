package com.ymsfd.practices.rxlife;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by WoodenTea.
 * Date: 10/11/2016
 * Time: 20:59
 */
public class RxFragment extends Fragment {
    protected final BehaviorSubject<Integer> lifeSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifeSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifeSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifeSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifeSubject.onNext(FragmentEvent.DESTROY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifeSubject.onNext(FragmentEvent.DESTROY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifeSubject.onNext(FragmentEvent.DETACH);
    }

    public <T> ObservableTransformer<T, T> bindUntilEvent(final Integer bindEvent) {
        final Observable<Integer> observable = lifeSubject.filter(new Predicate<Integer>() {
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
