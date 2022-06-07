//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.net;

import com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base.DecoderException;

class Utils {
    private static final int RADIX = 16;

    Utils() {
    }

    static int digit16(byte b) throws DecoderException {
        int i = Character.digit((char)b, 16);
        if (i == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + b);
        } else {
            return i;
        }
    }

    static char hexDigit(int b) {
        return Character.toUpperCase(Character.forDigit(b & 15, 16));
    }
}
