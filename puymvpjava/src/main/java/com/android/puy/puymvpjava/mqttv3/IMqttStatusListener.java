package com.android.puy.puymvpjava.mqttv3;

import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * Created by puy on 2018/12/4 15:30
 */
public interface IMqttStatusListener {

    /**
     * 链接成功
     *
     * @param asyncActionToken
     */
    void connectSuccess(IMqttToken asyncActionToken);

    /**
     * 链接失败
     *
     * @param asyncActionToken
     * @param exception
     */
    void connectFail(IMqttToken asyncActionToken, Throwable exception);

    /**
     * 订阅成功
     *
     * @param iMqttToken
     */
    void subscribeSuccess(IMqttToken iMqttToken);

    /**
     * 订阅失败
     *
     * @param iMqttToken
     * @param throwable
     */
    void subscribeFail(IMqttToken iMqttToken, Throwable throwable);

    /**
     * 链接完成
     *
     * @param reconnect 链接完成后的链接状态 可能会失去链接
     * @param serverURI 链接服务器地址
     */
    void connectComplete(boolean reconnect, String serverURI);

    /**
     * 链接丢失
     *
     * @param cause 丢失原因
     */
    void connectLost(Throwable cause);
}
