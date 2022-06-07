//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.binary;

import java.io.InputStream;

public class Base32InputStream extends BaseNCodecInputStream {
    public Base32InputStream(InputStream in) {
        this(in, false);
    }

    public Base32InputStream(InputStream in, boolean doEncode) {
        super(in, new Base32(false), doEncode);
    }

    public Base32InputStream(InputStream in, boolean doEncode, int lineLength, byte[] lineSeparator) {
        super(in, new Base32(lineLength, lineSeparator), doEncode);
    }
}
