package com.android.puy.puymvpjava.customs.pageindicatorview.animation.data.type;


import cn.droidlover.xdroidmvp.customs.pageindicatorview.animation.data.Value;

public class SlideAnimationValue implements Value {

    private int coordinate;

    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }
}
