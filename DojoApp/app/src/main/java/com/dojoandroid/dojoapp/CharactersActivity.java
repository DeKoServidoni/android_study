package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dojoandroid.dojoapp.adapters.CharacterAdapter;
import com.dojoandroid.dojoapp.database.DBManager;
import com.dojoandroid.dojoapp.entity.Character;
import com.dojoandroid.dojoapp.task.CharactersTask;

import java.util.ArrayList;
import java.util.List;

public class CharactersActivity extends Activity implements CharactersTask.CharactersTaskCallback {

    private ListView mList = null;
    private List<Character> mContent = new ArrayList<>();

    private DBManager mManager = null;

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
            Log.e(CharactersActivity.class.getSimpleName(), "Buscando do servidor!");
            CharactersTask task = new CharactersTask(this, this);
            task.execute();
        } else {
            Log.e(CharactersActivity.class.getSimpleName(), "Resultados do banco local!");
            initializeList();
        }
    }

    @Override
    public void onFinishWithError() {
        Toast.makeText(CharactersActivity.this, "Erro ao carregar lista!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishWithSuccess(List<Character> content) {
        mContent.clear();
        mContent.addAll(content);
        initializeList();

        // insert the data from server to the database
        for(Character character : mContent) {
            mManager.insert(character.getName());
        }
    }

    /**
     * Initialize the list with and adapter
     */
    private void initializeList() {
        CharacterAdapter adapter = new CharacterAdapter(this, mContent);
        mList.setAdapter(adapter);
    }
}
