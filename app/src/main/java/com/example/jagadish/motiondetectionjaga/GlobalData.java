package com.example.jagadish.motiondetectionjaga;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jagadish on 3/17/2017.
 */

public abstract class GlobalData {
    private GlobalData() {
    };

    private static final AtomicBoolean phoneInMotion = new AtomicBoolean(false);

    public static boolean isPhoneInMotion() {
        return phoneInMotion.get();
    }

    public static void setPhoneInMotion(boolean bool) {
        phoneInMotion.set(bool);
    }
}
