package com.google.zxing.camera.open;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by WoodenTea.
 * Date: 2016/6/3
 * Time: 13:52
 */
public class OpenCameraInterface {
    public static final int NO_REQUESTED_CAMERA = -1;
    private static final String TAG = OpenCameraInterface.class.getClass().getSimpleName();

    private OpenCameraInterface() {
    }

    public static OpenCamera open(int cameraId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            Log.w(TAG, "No cameras!");
            return null;
        }

        boolean explicitRequest = cameraId >= 0 && cameraId <= numCameras;
        Camera.CameraInfo selectedCameraInfo = null;
        int index;
        if (explicitRequest) {
            index = cameraId;
            selectedCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, selectedCameraInfo);
        } else {
            index = 0;
            while (index < numCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                if (cameraInfo.facing == 0) {
                    selectedCameraInfo = cameraInfo;
                    break;
                }
                index++;
            }
        }

        Camera camera;
        if (index < numCameras) {
            camera = Camera.open(index);
        } else {
            camera = Camera.open(0);
            selectedCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(0, selectedCameraInfo);
        }

        if (camera == null || selectedCameraInfo == null) {
            return null;
        }

        CameraFacing cameraFacing = new CameraFacing();
        if (selectedCameraInfo.facing == CameraFacing.BACK) {
            cameraFacing.facing = CameraFacing.BACK;
        } else {
            cameraFacing.facing = CameraFacing.FRONT;
        }

        return new OpenCamera(camera, index, cameraFacing, selectedCameraInfo.orientation);
    }
}
