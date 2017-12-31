package appsforu.lights.on;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            SharedPreferences pref = context.getSharedPreferences(MainActivity.Preferences, context.MODE_PRIVATE);
            String password = pref.getString(MainActivity.Preferences_SmSPassword, "QWE!@#EQE!23");
            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if(messages[i].getMessageBody().contains(password))
                {
                    Intent intentone = new Intent(context.getApplicationContext(), MainActivity.class);
                    intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentone.putExtra("SmS",true);
                    context.startActivity(intentone);
                }
            }
        }
    }
}