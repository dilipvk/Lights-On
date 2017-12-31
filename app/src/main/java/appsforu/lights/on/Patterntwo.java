package appsforu.lights.on;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

/**
 * Created by Avi on 1/15/2016.
 */
public class Patterntwo {
    private final Context mcontext;
    public boolean stop;
    private HigherVersion_Camera camera_high;
    private LowerVersion_Camera camera;
    Patterntwo(Context context)
    {
        stop = false;
        mcontext = context;
    }

    public void start()
    {

        stop = false;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            camera_high = new HigherVersion_Camera(mcontext);
            camera_high.init();
        }
        else
            camera = new LowerVersion_Camera(mcontext);
        final Handler m_Handler = new Handler();
        final boolean[] count = {true};
        final Runnable[] mRunnable = {null};
        mRunnable[0] = new Runnable() {
            @Override
            public void run() {
                if (stop)
                    return;
                if (count[0]) {
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                        camera_high.turn_on();
                    else
                        camera.turnOnFlash();
                    count[0] = false;
                } else {
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
                        camera_high.turn_off();
                    else
                        camera.turnOffFlash();
                    count[0] = true;
                }
                m_Handler.postDelayed(mRunnable[0], 100);// move this inside the run method
            }
        };
        mRunnable[0].run();
    }

    public void StopPattern()
    {
        if(camera_high!=null)
            camera_high.close();
        else if(camera!=null)
            camera.close();
        stop = true;
    }
}
