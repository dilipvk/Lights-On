package appsforu.lights.on;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;

public class MainActivity extends AppCompatActivity {

    Vibrator vibrate;
    public static int pattern = 1;
    public static String Preferences_SmSPassword="SmSPassword";
    public static String Preferences="AppGuysTorch";
    private Toolbar toolbar;
    private Patterntwo pattern2;
    private Patternthree pattern3;
    private PatternOne pattern1;
    private PatternFour pattern4;
    CheckBox keepon;
    boolean isKeepChecked;
    public static long vibratesecond=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String appKey = "cc5f5dbfdf534bc39a84138e5accf69ef93398f024af19e1";
        Appodeal.setAutoCache(Appodeal.INTERSTITIAL, false);
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_VIEW);
        Appodeal.cache(MainActivity.this, Appodeal.INTERSTITIAL);
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            public void onInterstitialLoaded(boolean isPrecache) {
                Appodeal.show(MainActivity.this, Appodeal.INTERSTITIAL);
            }
            public void onInterstitialFailedToLoad() {
                // your code for onInterstitialFailedToLoad
            }
            public void onInterstitialShown() {
                // your code for onInterstitialShown
            }
            public void onInterstitialClicked() {
                // your code for onInterstitialClicked
            }
            public void onInterstitialClosed() {
                // your code for onInterstitialClosed
            }
        });
        setContentView(R.layout.activity_main);
        setListeners();
        intilaze_torch();
    }

    private void intilaze_torch() {
        ((Switch)findViewById(R.id.torchswitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vibrate.vibrate(vibratesecond);
                if(b)
                    turnOn();
                else
                    turnOff();
            }
        });
        keepon.setChecked(isKeepChecked);
        ((Switch)findViewById(R.id.torchswitch)).setChecked(true);
    }

    private void turnOn() {
        turnOff();
        switch (pattern)
        {
            case 1:
                pattern1.start();
                break;
            case 2:
                pattern2.start();
                break;
            case 3:
                pattern3.start();
                break;
            case 4:
                pattern4.start();
                break;
        }
    }

    private void turnOff() {
        pattern1.StopPattern();
        pattern2.StopPattern();
        pattern3.StopPattern();
        pattern4.StopPattern();
    }

    private void setListeners() {
        if(getIntent()!=null&&getIntent().getExtras()!=null&&getIntent().getExtras().getBoolean("SmS"))
            pattern = 2;
        vibrate = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        keepon = (CheckBox)findViewById(R.id.keepOn);
        keepon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vibrate.vibrate(vibratesecond);
                isKeepChecked = b;
            }
        });
        pattern1 = new PatternOne(this);
        pattern2 = new Patterntwo(this);
        pattern3 = new Patternthree(this);
        pattern4 = new PatternFour(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        vibrate.vibrate(vibratesecond);
        if(item.getItemId()==R.id.settings)
        {
            ((Switch)findViewById(R.id.torchswitch)).setChecked(false);
            Intent i =new Intent(MainActivity.this,Settings.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        Appodeal.show(this, Appodeal.BANNER_VIEW);
        intilaze_torch();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(keepon.isChecked())
            return;
        ((Switch)findViewById(R.id.torchswitch)).setChecked(false);
    }
}
