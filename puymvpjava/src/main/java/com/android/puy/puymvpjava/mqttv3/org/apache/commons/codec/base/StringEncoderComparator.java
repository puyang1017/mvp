//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.android.puy.puymvpjava.mqttv3.org.apache.commons.codec.base;

import java.util.Comparator;

public class StringEncoderComparator implements Comparator {
    private final StringEncoder stringEncoder;

    /** @deprecated */
    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public int compare(Object o1, Object o2) {
        boolean var3 = false;

        int compareCode;
        try {
            Comparable<Comparable<?>> s1 = (Comparable)this.stringEncoder.encode(o1);
            Comparable<?> s2 = (Comparable)this.stringEncoder.encode(o2);
            compareCode = s1.compareTo(s2);
        } catch (EncoderException var6) {
            compareCode = 0;
        }

        return compareCode;
    }
}
