package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.ymsfd.practices.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by WoodenTea.
 * Date: 2015/12/24
 * Time: 16:38
 */
public class RetrofitActivity extends BaseActivity {
    private TextView tv_result;
    private EditText et_keyword;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.actvt_retrofit);
        et_keyword = (EditText) findViewById(R.id.et_keyword);
        tv_result = (TextView) findViewById(R.id.tv_result);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://suggest.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        final SearchService searchService = retrofit.create(SearchService.class);
        BehaviorSubject<ActivityEvent> lifecycle = BehaviorSubject.create();
        RxTextView.textChanges(et_keyword)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        tv_result.setText("");
                        return charSequence.length() > 0;
                    }
                })
                .observeOn(Schedulers.io())
                .switchMap(new Func1<CharSequence, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(CharSequence charSequence) {
                        return searchService.searchProduct("utf-8",
                                charSequence.toString());
                    }
                })
                .filter(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data != null;
                    }
                })
                .map(new Func1<Data, List<List<String>>>() {
                    @Override
                    public List<List<String>> call(Data data) {
                        return data.result;
                    }
                })
                .flatMap(new Func1<List<List<String>>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(List<List<String>> lists) {
                        return Observable.from(lists);
                    }
                })
                .filter(new Func1<List<String>, Boolean>() {
                    @Override
                    public Boolean call(List<String> strings) {
                        return strings.size() > 1;
                    }
                })
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        return "[商品名称:" + strings.get(0) + ", ID:" + strings.get(1) + "]\n";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        D("Unsubscribe");
                    }
                })
                .compose(RxLifecycle.<String>bindUntilActivityEvent(lifecycle, ActivityEvent
                        .DESTROY))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        D("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        E(e);
                    }

                    @Override
                    public void onNext(String s) {
                        tv_result.append(s);
                    }
                })
        ;

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        D("Destroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        D("Stop");
    }

    interface SearchService {
        @GET("sug")
        Observable<Data> searchProduct(@Query("code") String code, @Query("q") String keyword);
    }

    class Data {
        public List<List<String>> result;
    }
}
