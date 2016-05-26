package com.google.zxing.camera;

import android.hardware.Camera;

/**
 * Created by WoodenTea.
 * Date: 2016/6/3
 * Time: 11:55
 */
public class OpenCamera {
    private final int index;
    private final Camera camera;
    private final int orientation;
    private final CameraFacing cameraFacing;

    public OpenCamera(Camera camera, int index, CameraFacing cameraFacing, int orientation) {
        this.camera = camera;
        this.index = index;
        this.cameraFacing = cameraFacing;
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Camera #" + index + " : " + cameraFacing.facing + ',' + orientation;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getOrientation() {
        return orientation;
    }

    public CameraFacing getCameraFacing() {
        return cameraFacing;
    }
}
