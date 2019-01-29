package com.android.puy.puymvpjava.mqttv3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puy on 2018/12/5 14:17
 */
public class MqttConfig {
    //mqtt ClientId
    public static String mqttClientId = "GID_test_1@@@TEST00006";
    //mqtt用户名
    public static String mqttUserName = "LTAIMmKqTLMAbDS9";
    //mqtt用户密码
    public static String mqttUserPw = "MAgvXVGYyTBSm0DuDx7a9VHWtYjg3M";
    //topic
    public static List<String> topics = new ArrayList<String>() {
        {
            add("qy_mqtt_normal/#");
        }
    };
}
