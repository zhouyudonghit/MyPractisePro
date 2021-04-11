package com.example.localuser.retrofittest;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by localuser on 2018/4/9.
 */

public interface GetRequestRxjava_Interface {
    @GET("http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation> getCall();

    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start")int start,@Query("count")int count);
}
