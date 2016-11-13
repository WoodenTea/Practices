package com.ymsfd.practices.interfaces;

import com.ymsfd.practices.domain.SearchResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by WoodenTea.
 * Date: 10/11/2016
 * Time: 21:13
 */
public interface SearchInterface {
    @GET("sug")
    Observable<SearchResult> searchProduct(@Query("code") String code,
                                           @Query("q") String keyword);
}
