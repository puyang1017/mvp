package com.android.puy.puymvpjava.customs.pageindicatorview;

import android.support.annotation.Nullable;
import com.android.puy.puymvpjava.customs.pageindicatorview.animation.AnimationManager;
import com.android.puy.puymvpjava.customs.pageindicatorview.animation.controller.ValueController;
import com.android.puy.puymvpjava.customs.pageindicatorview.animation.data.Value;
import com.android.puy.puymvpjava.customs.pageindicatorview.draw.DrawManager;
import com.android.puy.puymvpjava.customs.pageindicatorview.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
