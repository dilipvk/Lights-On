package appsforu.lights.on;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.rey.material.app.Dialog;

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Vibrator vibrate;
    LinearLayout settings1,settings2;
    private Dialog dialog;
    private Patterntwo pattern2;
    private Patternthree pattern3;
    private PatternOne pattern1;
    private PatternFour pattern4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        settings1 = (LinearLayout)findViewById(R.id.settings1);
        settings2 = (LinearLayout)findViewById(R.id.settings2);
        setListeners();
    }

    @Override
    protected void onResume() {
        Appodeal.show(this, Appodeal.BANNER_VIEW);
        super.onResume();
    }

    private void Find_Phone_Dialog() {
        final Dialog dialog = new Dialog(Settings.this);
        dialog.setContentView(R.layout.dialog_findphone);
        final EditText setpin = (EditText)dialog.findViewById(R.id.setpin);
        final EditText verifypin = (EditText)dialog.findViewById(R.id.verifypin);
        TextView ok,cancel;
        ok = (TextView) dialog.findViewById(R.id.find_phone_ok);
        cancel= (TextView) dialog.findViewById(R.id.find_phone_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate.vibrate(MainActivity.vibratesecond);
                if(setpin.length()!=4)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 4 digit pin",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!verifypin.getText().toString().equals(setpin.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Pin not equal",Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferences pref = getSharedPreferences(MainActivity.Preferences, getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(MainActivity.Preferences_SmSPassword, verifypin.getText().toString()).commit();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate.vibrate(MainActivity.vibratesecond);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setListeners() {
        vibrate = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        String appKey = "cc5f5dbfdf534bc39a84138e5accf69ef93398f024af19e1";
        Appodeal.initialize(this, appKey,Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.setting));
        settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate.vibrate(MainActivity.vibratesecond);
                Find_Phone_Dialog();
            }
        });
        settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate.vibrate(MainActivity.vibratesecond);
                Disco_Dialog();
            }
        });
    }

    private void Disco_Dialog() {
        dialog = new Dialog(Settings.this);
        dialog.setContentView(R.layout.dialog_disco);
        RadioButton RBpattern1= (RadioButton) dialog.findViewById(R.id.pattern1);
        RadioButton RBpattern2= (RadioButton) dialog.findViewById(R.id.pattern2);
        RadioButton RBpattern3= (RadioButton) dialog.findViewById(R.id.pattern3);
        RadioButton RBpattern4= (RadioButton) dialog.findViewById(R.id.pattern4);
        RBpattern1.setOnCheckedChangeListener(this);
        RBpattern2.setOnCheckedChangeListener(this);
        RBpattern3.setOnCheckedChangeListener(this);
        RBpattern4.setOnCheckedChangeListener(this);
        pattern1 = new PatternOne(this);
        pattern2 = new Patterntwo(this);
        pattern3 = new Patternthree(this);
        pattern4 = new PatternFour(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                pattern1.StopPattern();
                pattern2.StopPattern();
                pattern3.StopPattern();
                pattern4.StopPattern();
            }
        });
        dialog.show();
        pattern1.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
        {
            pattern1.StopPattern();
            pattern2.StopPattern();
            pattern3.StopPattern();
            pattern4.StopPattern();
            switch (compoundButton.getId())
            {
                case R.id.pattern1:
                    MainActivity.pattern = 1;
                    pattern1.start();
                    break;
                case R.id.pattern2: {
                    MainActivity.pattern = 2;
                    pattern2.start();
                }   break;
                case R.id.pattern3: {
                    MainActivity.pattern = 3;
                    pattern3.start();
                }   break;
                case R.id.pattern4:{
                    MainActivity.pattern = 4;
                    pattern4.start();
                    break;}
            }
        }
    }


}
