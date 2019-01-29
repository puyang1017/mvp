package com.android.puy.puymvpjava.event;


public class BusProvider {

    private static RxBusImpl bus;

    public static RxBusImpl getBus() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                if (bus == null) {
                    bus = RxBusImpl.get();
                }
            }
        }
        return bus;
    }

}
