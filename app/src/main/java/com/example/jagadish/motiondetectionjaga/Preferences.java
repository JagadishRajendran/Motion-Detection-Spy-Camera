package com.example.jagadish.motiondetectionjaga;

/**
 * Created by jagadish on 4/1/2017.
 */

public abstract class Preferences {

    private Preferences() {
    }
    // Which photos to save
    public static boolean SAVE_PREVIOUS = false;
    public static boolean SAVE_ORIGINAL = true;
    public static boolean SAVE_CHANGES = true;

    // Time between saving photos
    public static int PICTURE_DELAY = 3000;
}
