package appsforu.lights.on;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

/**
 * Created by Avi on 1/14/2016.
 */
public class LowerVersion_Camera {
    private Camera camera;
    public boolean hasFlash;
    Camera.Parameters params;

    LowerVersion_Camera(Context context)
    {
        hasFlash = context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        getCamera();
    }

    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error", e.getMessage());
            }
        }
    }


    // Turning On flash
    public void turnOnFlash() {
        if (camera == null || params == null) {
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    // Turning Off flash
    public void turnOffFlash() {
        if (camera == null || params == null) {
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }

    public void close()
    {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void open() {
        getCamera();
    }
}
