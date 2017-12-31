package appsforu.lights.on;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

/**
 * Created by Avi on 1/15/2016.
 */
public class PatternFour {
    private final Context mcontext;
    public boolean stop;
    private HigherVersion_Camera camera_high;
    private LowerVersion_Camera camera;
    PatternFour(Context context)
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
        final int[] count = {0};
        final Runnable[] mRunnable = {null};
        mRunnable[0] = new Runnable() {
            @Override
            public void run() {
                if (stop)
                    return;
                if (count[0]==0) {
                    turn_ON();
                    count[0] = 1;
                    m_Handler.postDelayed(mRunnable[0], 20);// move this inside the run method
                } else if(count[0]==1){
                    turn_off();
                    count[0] = 2;
                    m_Handler.postDelayed(mRunnable[0], 20);// move this inside the run method
                } else if(count[0]==2){
                    turn_ON();
                    count[0] = 3;
                    m_Handler.postDelayed(mRunnable[0], 50);// move this inside the run method
                } else if(count[0]==3){
                    turn_off();
                    count[0] = 4;
                    m_Handler.postDelayed(mRunnable[0], 50);// move this inside the run method
                } else if(count[0]==4){
                    turn_ON();
                    count[0] = 5;
                    m_Handler.postDelayed(mRunnable[0], 300);// move this inside the run method
                } else if(count[0]==5){
                    turn_off();
                    count[0] = 6;
                    m_Handler.postDelayed(mRunnable[0], 200);// move this inside the run method
                } else if(count[0]==6){
                    turn_ON();
                    count[0] = 7;
                    m_Handler.postDelayed(mRunnable[0], 40);// move this inside the run method
                } else if(count[0]==7){
                    turn_off();
                    count[0] = 8;
                    m_Handler.postDelayed(mRunnable[0], 40);// move this inside the run method
                }
                else if(count[0]==8){
                    turn_ON();
                    count[0] = 9;
                    m_Handler.postDelayed(mRunnable[0], 500);// move this inside the run method
                } else if(count[0]==9){
                    turn_off();
                    count[0] = 10;
                    m_Handler.postDelayed(mRunnable[0], 400);// move this inside the run method
                }
                else if(count[0]==10){
                    turn_ON();
                    count[0] = 11;
                    m_Handler.postDelayed(mRunnable[0], 20);// move this inside the run method
                } else if(count[0]==11){
                    turn_off();
                    count[0] = 12;
                    m_Handler.postDelayed(mRunnable[0], 20);// move this inside the run method
                }
                else if(count[0]==12){
                    turn_ON();
                    count[0] = 13;
                    m_Handler.postDelayed(mRunnable[0], 100);// move this inside the run method
                } else if(count[0]==13){
                    turn_off();
                    count[0] = 0;
                    m_Handler.postDelayed(mRunnable[0], 100);// move this inside the run method
                }
            }
        };
        mRunnable[0].run();
    }


    private void turn_ON() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            camera_high.turn_on();
        }
        else
        {
            camera.turnOnFlash();
        }
    }

    private void turn_off()
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            camera_high.turn_off();
        }
        else
        {
            camera.turnOffFlash();
        }
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
