package appsforu.lights.on;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.util.Size;
import android.view.Surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avi on 1/14/2016.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class HigherVersion_Camera {
    private final Context mcontext;
    private CameraManager mCameraManager;
    private CameraCaptureSession mSession;
    private CaptureRequest.Builder mBuilder;
    private CameraDevice mCameraDevice;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;

    HigherVersion_Camera(Context context)
    {
        mcontext = context;
    }

    @SuppressWarnings("ResourceType")
    public void init() {
        mCameraManager = (CameraManager) mcontext.getSystemService(Context.CAMERA_SERVICE);
        CameraCharacteristics cameraCharacteristics = null;
        try {
            cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
            boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            if (flashAvailable) {
                mCameraManager.openCamera("0", new MyCameraDeviceStateCallback(), null);
            } else {
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turn_on() {
        if(mBuilder==null||mSession==null)
        {
            return;
        }
        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        try {
            mSession.setRepeatingRequest(mBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turn_off() {
        if(mBuilder==null||mSession==null)
        {
            return;
        }
        mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
        try {
            mSession.setRepeatingRequest(mBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        init();
    }

    class MyCameraDeviceStateCallback extends CameraDevice.StateCallback {
        @Override
        public void onOpened(CameraDevice camera) {
                mCameraDevice = camera;
                //get builder
                try {
                mBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_MANUAL);
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                mBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
                List<Surface> list = new ArrayList<Surface>();
                mSurfaceTexture = new SurfaceTexture(1);
                Size size = getSmallestSize(mCameraDevice.getId());
                mSurfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
                mSurface = new Surface(mSurfaceTexture);
                list.add(mSurface);
                mBuilder.addTarget(mSurface);
                camera.createCaptureSession(list, new MyCameraCaptureSessionStateCallback(), null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
        }

        @Override
        public void onError(CameraDevice camera, int error) {
        }
    }

    private Size getSmallestSize(String cameraId) throws CameraAccessException {
        Size[] outputSizes = mCameraManager.getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                .getOutputSizes(SurfaceTexture.class);
        if (outputSizes == null || outputSizes.length == 0) {
            throw new IllegalStateException(
                    "Camera " + cameraId + "doesn't support any outputSize.");
        }
        Size chosen = outputSizes[0];
        for (Size s : outputSizes) {
            if (chosen.getWidth() >= s.getWidth() && chosen.getHeight() >= s.getHeight()) {
                chosen = s;
            }
        }
        return chosen;
    }

    class MyCameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            mSession = session;
            try {
                mSession.setRepeatingRequest(mBuilder.build(), null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    }


    public void close() {
        if (mCameraDevice == null || mSession == null) {
            return;
        }
        mSession.close();
        mCameraDevice.close();
        mCameraDevice = null;
        mSession = null;
    }

}
