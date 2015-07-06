package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dojoandroid.dojoapp.adapters.CharacterAdapter;
import com.dojoandroid.dojoapp.database.DBManager;
import com.dojoandroid.dojoapp.entity.Character;
import com.dojoandroid.dojoapp.service.CharactersService;

import java.util.ArrayList;
import java.util.List;

public class CharactersActivity extends Activity {

    private ListView mList = null;
    private List<Character> mContent = new ArrayList<>();

    private DBManager mManager = null;

    private BroadcastReceiver mReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_characters);

        mList = (ListView) findViewById(R.id.list_characters);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character character = mContent.get(i);
                Toast.makeText(CharactersActivity.this, character.getName(), Toast.LENGTH_LONG).show();
            }
        });

        mManager = new DBManager(this);

        mContent.addAll(mManager.get());

        if(mContent.size() == 0) {
            Intent service = new Intent(this, CharactersService.class);
            service.putExtra(CharactersService.GET_DATA, true);
            startService(service);
        } else {
            Log.e(CharactersActivity.class.getSimpleName(), "Resultados do banco local!");
            initializeList();
        }

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null && intent.getAction().equals(CharactersService.BROADCAST_UPDATE)) {
                    Toast.makeText(CharactersActivity.this, "Lista atualizada!", Toast.LENGTH_SHORT).show();
                    mContent.clear();
                    mContent.addAll(mManager.get());
                    initializeList();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(CharactersService.BROADCAST_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * Initialize the list with and adapter
     */
    private void initializeList() {
        CharacterAdapter adapter = new CharacterAdapter(this, mContent);
        mList.setAdapter(adapter);
    }
}
