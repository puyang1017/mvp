//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.language;

import com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base.EncoderException;
import com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base.StringEncoder;

/** @deprecated */
@Deprecated
public class Caverphone implements StringEncoder {
    private final Caverphone2 encoder = new Caverphone2();

    public Caverphone() {
    }

    public String caverphone(String source) {
        return this.encoder.encode(source);
    }

    public Object encode(Object obj) throws EncoderException {
        if (!(obj instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        } else {
            return this.caverphone((String)obj);
        }
    }

    public String encode(String str) {
        return this.caverphone(str);
    }

    public boolean isCaverphoneEqual(String str1, String str2) {
        return this.caverphone(str1).equals(this.caverphone(str2));
    }
}
