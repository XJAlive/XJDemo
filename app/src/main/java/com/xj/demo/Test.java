package com.xj.demo;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiej on 2021/3/2
 */
class Test {

    public static void main(String[] args) {

        String TAG = "xj";

//        Observable.timer(5, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.from(Executors.newScheduledThreadPool(1, new ThreadFactory() {
//                @Override
//                public Thread newThread(Runnable r) {
//                    return new Thread(r);
//                }
//            }))).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long t) throws Exception {
//                 System.out.println(t);
//            }
//        });


//        Observable.timer(500, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
//            @Override
//            public void accept(Long aLong) throws Exception {
//                System.out.println(aLong);
//            }
//        });
//


    }

}
