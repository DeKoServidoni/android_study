package com.dojoandroid.dojoapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.dojoandroid.dojoapp.entity.Character;
import com.dojoandroid.dojoapp.task.CharactersTask;

import java.util.ArrayList;
import java.util.List;

public class CharactersService extends Service implements CharactersTask.CharactersTaskCallback {

    private static final String TAG = CharactersService.class.getSimpleName();

    private static final int HANDLER_TIME = 15000;

    public static final String GET_DATA = "getData";
    public static final String DATA = "data";
    public static final String BROADCAST_ACTION = "com.dojoandroid.dojoapp.LOADED";
    public static final String BROADCAST_UPDATE = "com.dojoandroid.dojoapp.UPDATE";

    public static int mPage = 1;
    private Handler mHandler = new Handler();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"Start command!");

        if(intent != null) {
            boolean isToGet = intent.getBooleanExtra(GET_DATA, false);

            if(isToGet) {
                startTask(mPage);
                mHandler.postDelayed(mRunnable, HANDLER_TIME);
            }
        }

        return START_STICKY;
    }

    @Override
    public void onFinishWithError() {
        Toast.makeText(CharactersService.this, "Erro ao carregar lista!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishWithSuccess(List<Character> content) {
        mPage++;

        ArrayList<String> names = new ArrayList<>();

        // insert the data from server to the database
        for(Character character : content) {
            names.add(character.getName());
        }

        // send broadcast
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_ACTION);
        broadcastIntent.putStringArrayListExtra(DATA, names);
        sendBroadcast(broadcastIntent);
    }

    private void startTask(int page) {
        Log.e(TAG, "Buscando do servidor! página: " + page);
        CharactersTask task = new CharactersTask(CharactersService.this, this);
        task.execute(page);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            startTask(mPage);

            if(mPage <= 5) {
                Log.e(TAG, "Agendando novo handler!");
                mHandler.postDelayed(mRunnable, HANDLER_TIME);
            }
        }
    };
}
