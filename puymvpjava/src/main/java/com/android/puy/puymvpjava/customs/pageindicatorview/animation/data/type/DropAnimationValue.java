package com.android.puy.puymvpjava.customs.pageindicatorview.animation.data.type;


import cn.droidlover.xdroidmvp.customs.pageindicatorview.animation.data.Value;

public class DropAnimationValue implements Value {

    private int width;
    private int height;
    private int radius;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
