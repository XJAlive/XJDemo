package com.xj.demo.coroutine;


import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

public class CallCoroutine {

    public static void main(String... args) {
        Object value = CoroutineUnitTestKt.hello(new Continuation<Integer>() {

            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }


            public void resumeWith(Object o) { // ①
                if (o instanceof Integer) {
                    handleResult(o);
                } else {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                }
            }
        });

        if (value == IntrinsicsKt.getCOROUTINE_SUSPENDED()) { // ②
            CoroutineUnitTestKt.log("Suspended.");
        } else {
            handleResult(value);
        }
    }

    public static void handleResult(Object o) {
        CoroutineUnitTestKt.log("The result is " + o);
    }
}