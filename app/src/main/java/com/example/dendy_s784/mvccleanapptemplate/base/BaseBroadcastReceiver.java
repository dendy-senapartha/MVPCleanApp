package com.example.dendy_s784.mvccleanapptemplate.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*
 * Created by dendy-prtha on 12/02/2019.
 * General broadcast receiver example
 */
public class BaseBroadcastReceiver extends BroadcastReceiver {

    public BaseBroadcastReceiver()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION"))
            Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();

        else {
            Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }
}
