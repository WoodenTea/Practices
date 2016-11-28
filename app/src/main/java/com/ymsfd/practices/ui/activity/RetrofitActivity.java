package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.SearchResult;
import com.ymsfd.practices.interfaces.SearchInterface;
import com.ymsfd.practices.rxbinding.RxTextView;
import com.ymsfd.practices.rxlife.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        final SearchInterface search = retrofit.create(SearchInterface.class);
        RxTextView.textChanges(et_keyword)
                .debounce(600, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        tv_result.setText("");
                        return !TextUtils.isEmpty(charSequence);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<CharSequence, Observable<SearchResult>>() {
                    @Override
                    public Observable<SearchResult> apply(CharSequence charSequence) {
                        return search.searchProduct("utf-8", charSequence.toString());
                    }
                })
                .map(new Function<SearchResult, List<List<String>>>() {
                    @Override
                    public List<List<String>> apply(SearchResult searchResult) throws Exception {
                        return searchResult.result;
                    }
                })
                .flatMap(new Function<List<List<String>>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> apply(List<List<String>> lists) {
                        return Observable.fromIterable(lists);
                    }
                })
                .filter(new Predicate<List<String>>() {
                    @Override
                    public boolean test(List<String> lists) {
                        return lists.size() > 1;
                    }
                })
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) {
                        return "[商品名称:" + strings.get(0) + ", ID:" + strings.get(1) + "]\n";
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        D("Dispose");
                    }
                })
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        tv_result.append(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        D("Completed");
                    }
                });

        return true;
    }
}
