package com.androidlab.rxpack.RxBindingTest;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Haodong on 2017/3/12.
 */

public class RxBus {
    private final Subject<Object, Object> _bus;
    private final Map<Class<?>, Object> mStickyEventMap;

    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());

        mStickyEventMap = new ConcurrentHashMap<>();
    }

    private static class RxBusHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance() {
        return RxBusHolder.INSTANCE;
    }


    public void post(Object o) {
        _bus.onNext(o);

    }



    /**
     *
     * @param eventType
     * @param <T>根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }


    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }
    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = _bus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                //如果之前发生了postSticky事件，则会在订阅事件发生后，发送这个事件
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                   //这里是一个冷热结合的Observable
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));

                    }
                }));
            } else {
                //如果之前并没有post事件的发生,和普通toObservable一样
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }
    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }


}
