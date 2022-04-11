//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.language;

import java.util.Locale;
import com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base.EncoderException;
import com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base.StringEncoder;

final class SoundexUtils {
    SoundexUtils() {
    }

    static String clean(String str) {
        if (str != null && str.length() != 0) {
            int len = str.length();
            char[] chars = new char[len];
            int count = 0;

            for(int i = 0; i < len; ++i) {
                if (Character.isLetter(str.charAt(i))) {
                    chars[count++] = str.charAt(i);
                }
            }

            if (count == len) {
                return str.toUpperCase(Locale.ENGLISH);
            } else {
                return (new String(chars, 0, count)).toUpperCase(Locale.ENGLISH);
            }
        } else {
            return str;
        }
    }

    static int difference(StringEncoder encoder, String s1, String s2) throws EncoderException {
        return differenceEncoded(encoder.encode(s1), encoder.encode(s2));
    }

    static int differenceEncoded(String es1, String es2) {
        if (es1 != null && es2 != null) {
            int lengthToMatch = Math.min(es1.length(), es2.length());
            int diff = 0;

            for(int i = 0; i < lengthToMatch; ++i) {
                if (es1.charAt(i) == es2.charAt(i)) {
                    ++diff;
                }
            }

            return diff;
        } else {
            return 0;
        }
    }
}
