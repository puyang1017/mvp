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
        mSubscriptionMap.put(object.getClass().getName(), new ArrayList<>());
        System.out.println("rxBus register put " + object.getClass().getName() + " " + mSubscriptionMap.size());
    }

    @Override
    public void unregister(Object object) {
        try {
            System.out.println("rxBus unregister " + mSubscriptionMap.size());
            for (Subscription subscription : mSubscriptionMap.get(object.getClass().getName())) {
                System.out.println("rxBus unregister " + object.getClass().getName());
                if (subscription != null) subscription.cancel();
            }
            mSubscriptionMap.remove(object.getClass().getName());
            System.out.println("rxBus unregister remove" + object.getClass().getName() + " " + mSubscriptionMap.size());
        } catch (Exception e) {
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
    public <T extends IEvent> Flowable<T> toFlowable(Object object, Class<T> eventType) {
        Flowable<T> flowable = bus.ofType(eventType).onBackpressureBuffer();
        return flowable.doOnSubscribe(subscription -> {
            try {
                System.out.println("rxBus toFlowable " + object.getClass().getName());
                List<Subscription> subscriptions = mSubscriptionMap.get(object.getClass().getName());
                if (subscriptions != null) {
                    subscriptions.add(subscription);
                    System.out.println("rxBus toFlowable " + object.getClass().getName() + " " + mSubscriptionMap.size());
                    mSubscriptionMap.get(object.getClass().getName()).add(subscription);
                } else {
                    System.out.println("rxBus toFlowable mSubscriptionMap no find " + object.getClass().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static class Holder {
        private static final RxBusImpl instance = new RxBusImpl();
    }
}
