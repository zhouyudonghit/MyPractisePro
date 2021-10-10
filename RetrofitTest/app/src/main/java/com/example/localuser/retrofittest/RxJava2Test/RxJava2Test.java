package com.example.localuser.retrofittest.RxJava2Test;

import android.util.Log;

import com.example.localuser.retrofittest.Configs.LogConfigs;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Test {
    private String TAG = LogConfigs.TAG_PREFIX_RXJAVA_TEST + getClass().getSimpleName();
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }

        @Override
        public void onComplete() {

        }
    };

    //Subscriber类貌似和Flowable相关
    Subscriber<String> subsriber = new Subscriber<String>() {
        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(String s) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            Log.d(TAG,"subscribe");
            e.onNext("Hello");
            e.onNext("Hi");
            e.onNext("Aloha");
            e.onComplete();
        }
    }).subscribeOn(AndroidSchedulers.mainThread());

    public void test()
    {
//        observable.subscribe(new Consumer() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Log.d(TAG,"o = " + o);
//                observer.onNext((String)o);
//            }
//        });
        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                Log.d(TAG,"defer call");
                return observable;
            }
        }).subscribe(observer);
//        Observable.just("just test").subscribe(observer);
//        double d1 = 0.0252;
//        double d2 = 0.019600000000000003;
//        Log.d(TAG,"d = "+Double.valueOf("0.044800000000000003"));
    }

    public void test1()
    {
        //被观察者在主线程中，每1ms发送一个事件
        Observable.interval(1, TimeUnit.MILLISECONDS)
                //.subscribeOn(Schedulers.newThread())
                //将观察者的工作放在新线程环境中
                .observeOn(Schedulers.newThread())
                //观察者处理每1000ms才处理一个事件
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.w("TAG","---->"+aLong);
                    }
                });
    }
}
