package appsforu.lights.on;

import android.content.Context;
import android.os.Build;

/**
 * Created by Avi on 1/15/2016.
 */
public class PatternOne {
    private final Context mcontext;
    private HigherVersion_Camera camera_high;
    private LowerVersion_Camera camera;
    PatternOne(Context context)
    {
        mcontext = context;
    }

    public void start()
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            camera_high = new HigherVersion_Camera(mcontext);
            camera_high.init();
            camera_high.turn_on();
        }
        else
        {
            camera = new LowerVersion_Camera(mcontext);
            camera.turnOnFlash();
        }
    }

    public void StopPattern()
    {
        if(camera_high!=null)
            camera_high.close();
        else if(camera!=null)
            camera.close();
    }
}
