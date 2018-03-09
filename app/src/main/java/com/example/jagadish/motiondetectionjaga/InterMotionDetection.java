package com.example.jagadish.motiondetectionjaga;

/**
 * Created by jagadish on 3/24/2017
 * This interface is used to represent a class that can detect motion
 */

public interface InterMotionDetection {
    /**
     * Get the previous image in integer array format
     *
     * @return int array of previous image.
     */
    public int[] getPrevious();

    /**
     * @param data
     *            integer array representing an image.
     * @param width
     *            Width of the image.
     * @param height
     *            Height of the image.
     * @return boolean True is there is motion.
     */
    public boolean detect(int[] data, int width, int height);
}
