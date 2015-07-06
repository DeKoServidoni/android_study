package com.dojoandroid.dojoapp.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dojoandroid.dojoapp.R;
import com.dojoandroid.dojoapp.database.DBManager;
import com.dojoandroid.dojoapp.service.CharactersService;

import java.util.ArrayList;


public class CharactersReceiver extends BroadcastReceiver {

    public static final String TAG = CharactersReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null && intent.getAction().equals(CharactersService.BROADCAST_ACTION)) {
            ArrayList<String> content = intent.getStringArrayListExtra(CharactersService.DATA);
            DBManager manager = new DBManager(context);

            for(String name : content) {
                manager.insert(name);
            }

            Log.e(TAG, "New items saved on database!");
            notifyUser(context);

            Intent updateUI = new Intent();
            updateUI.setAction(CharactersService.BROADCAST_UPDATE);
            context.sendBroadcast(updateUI);
        }
    }

    private void notifyUser(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Dojo App")
                        .setContentText("Novos personagens baixados!");

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }
}
