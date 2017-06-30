package com.ymsfd.practices.interfaces;

import com.ymsfd.practices.domain.SearchResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by WoodenTea.
 * Date: 10/11/2016
 * Time: 21:13
 */
public interface SearchInterface {
    @GET("sug")
    Observable<SearchResult> searchProduct(@QueryMap Map<String, Object> map);
}
