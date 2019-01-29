package com.android.puy.puymvpjava.mqttv3;

/**
 * Created by puy on 2018/12/4 15:31
 */

import org.eclipse.paho.client.mqttv3.IMqttActionListener;

public interface IPublishActionListener extends IMqttActionListener {

    /**
     * 链接丢失
     */
    void onConnectionLost();

}