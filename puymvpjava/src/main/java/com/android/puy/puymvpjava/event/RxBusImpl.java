package com.android.puy.puymvpjava.event;


import android.annotation.SuppressLint;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


public class RxBusImpl implements IBus {

    private FlowableProcessor<Object> bus = null;

    private HashMap<String, List<Subscription>> mSubscriptionMap = new HashMap<>();

    private RxBusImpl() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBusImpl get() {
        return Holder.instance;
    }

    @Override
    public void register(Object object) {
        mSubscriptionMap.put(object.getClass().getName(),new ArrayList<>());
    }

    @Override
    public void unregister(Object object) {
        try {
            for(Subscription subscription :mSubscriptionMap.get(object.getClass().getName())){
                if(subscription!=null) subscription.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void post(IEvent event) {
        bus.onNext(event);
    }

    @Override
    public void postSticky(IEvent event) {

    }

    @SuppressLint("CheckResult")
    public <T extends IEvent> Flowable<T> toFlowable(Object object , Class<T> eventType) {
        Flowable<T> flowable = bus.ofType(eventType).onBackpressureBuffer();
        return flowable.doOnSubscribe(subscription -> {
            try {
                mSubscriptionMap.get(object.getClass().getName()).add(subscription);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private static class Holder {
        private static final RxBusImpl instance = new RxBusImpl();
    }
}
