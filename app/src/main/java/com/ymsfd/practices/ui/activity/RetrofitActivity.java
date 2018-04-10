package com.ymsfd.practices.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.ymsfd.practices.R;
import com.ymsfd.practices.domain.SearchResult;
import com.ymsfd.practices.infrastructure.util.WTLogger;
import com.ymsfd.practices.interfaces.SearchInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ParameterInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WoodenTea.
 * Date: 2015/12/24
 * Time: 16:38
 */
public class RetrofitActivity extends RxBaseActivity {
    private TextView txtResult;

    @Override
    protected boolean startup(Bundle savedInstanceState) {
        if (!super.startup(savedInstanceState)) {
            return false;
        }

        setContentView(R.layout.retrofit_activity);
        enableToolbarUp(true);
        txtResult = findViewById(R.id.tv_result);

        EditText et_keyword = findViewById(R.id.et_keyword);
        Disposable subscribe = RxTextView.textChanges(et_keyword)
                .debounce(600, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) {
                        txtResult.setText("");
                        return !TextUtils.isEmpty(charSequence);
                    }
                }).compose(this.<CharSequence>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@NonNull CharSequence charSequence) {
                        onSearch(charSequence);
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        Observable<String> observable = Observable.just(list).subscribeOn(Schedulers.io())
                .flatMap(new Function<List<String>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull List<String> strings) {
                        return Observable.fromIterable(strings);
                    }
                });

        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            l.add(i);
        }
        Observable<String> stringObservable = Observable.just(l).subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull List<Integer> integers) throws
                            Exception {
                        return Observable.fromIterable(integers);
                    }
                }).map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                });
        Observable.concat(observable, stringObservable).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                WTLogger.d(RetrofitActivity.this.getClass().getSimpleName(), s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                WTLogger.d(RetrofitActivity.this.getClass().getSimpleName(), "Complete");
            }
        });
        return true;
    }

    private void onSearch(CharSequence charSequence) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "utf-8");
        map.put("q", charSequence.toString());
        map.put("a", "");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Interceptor i = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                String string = response.body().string();
                string = "{\"result\":[[\"凉鞋\",\"-0.13910571580227457\"],[\"板鞋女童\"," +
                        "\"-0.1333657167730834\"]],\"shop\":\"abc\"}";
                MediaType contentType = response.body().contentType();
                ResponseBody body = ResponseBody.create(contentType, string);
                return response.newBuilder().body(body).build();
            }
        };
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ParameterInterceptor())
                .addInterceptor(interceptor)
                .addInterceptor(i)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://suggest.taobao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        final SearchInterface search = retrofit.create(SearchInterface.class);
        search.searchProduct(map).map(new Function<SearchResult, List<List<String>>>() {
            @Override
            public List<List<String>> apply(SearchResult searchResult) {
                return searchResult.result;
            }
        }).flatMap(new Function<List<List<String>>, Observable<List<String>>>() {
            @Override
            public Observable<List<String>> apply(List<List<String>> lists) {
                return Observable.fromIterable(lists);
            }
        }).filter(new Predicate<List<String>>() {
            @Override
            public boolean test(List<String> lists) {
                return lists.size() > 1;
            }
        }).map(new Function<List<String>, String>() {
            @Override
            public String apply(List<String> strings) {
                return "[商品名称:" + strings.get(0) + ", ID:" + strings.get(1) + "]\n";
            }
        }).doOnDispose(new Action() {
            @Override
            public void run() {
                D("Dispose");
            }
        }).subscribeOn(Schedulers.io())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        txtResult.append(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        D("" + (txtResult == null));
                        D("Completed");
                    }
                });
    }
}
