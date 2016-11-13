package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.SearchResult;
import com.ymsfd.practices.interfaces.SearchInterface;
import com.ymsfd.practices.rxlife.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by WoodenTea.
 * Date: 2015/12/24
 * Time: 16:38
 */
public class RetrofitActivity extends RxBaseActivity {
    private TextView tv_result;

    @Override
    protected boolean _onCreate(Bundle savedInstanceState) {
        if (!super._onCreate(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.retrofit_activity);
        enableToolbarHomeButton(true);
        EditText et_keyword = (EditText) findViewById(R.id.et_keyword);
        tv_result = (TextView) findViewById(R.id.tv_result);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://suggest.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        final SearchInterface search = retrofit.create(SearchInterface.class);
        RxTextView.textChanges(et_keyword)
                .debounce(600, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        tv_result.setText("");
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<CharSequence, Observable<SearchResult>>() {
                    @Override
                    public Observable<SearchResult> call(CharSequence charSequence) {
                        return search.searchProduct("utf-8", charSequence.toString());
                    }
                })
                .map(new Func1<SearchResult, List<List<String>>>() {
                    @Override
                    public List<List<String>> call(SearchResult searchResult) {
                        return searchResult.result;
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
                    public Boolean call(List<String> lists) {
                        return lists.size() > 1;
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
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        D("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String string) {
                        tv_result.append(string);
                    }
                });

        return true;
    }
}
