package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dojoandroid.dojoapp.adapters.CharacterAdapter;
import com.dojoandroid.dojoapp.entity.Character;

import java.util.ArrayList;

public class CharactersActivity extends Activity {

    private int[] mImages = {R.drawable.c3po, R.drawable.chewbacca, R.drawable.hansolo, R.drawable.leia,
                                R.drawable.luke, R.drawable.obiwan, R.drawable.r2d2, R.drawable.vader,
                                R.drawable.jabba, R.drawable.maul, R.drawable.boba};

    private String[] mNames = {"C3PO", "CHEWBACCA", "HANSOLO", "LEIA", "LUKE", "OBI WAN KENOBI",
                                "R2D2", "DARTH VADER", "JABBA THE HUT", "DARTH MAUL", "BOBA FETT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_characters);

        final ArrayList<Character> content = new ArrayList<>();

        for(int i=0 ; i<mImages.length ; i++) {
            Character character = new Character();
            character.setResource(mImages[i]);
            character.setName(mNames[i]);

            content.add(character);
        }

        ListView list = (ListView) findViewById(R.id.list_characters);

        CharacterAdapter adapter = new CharacterAdapter(this, content);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character character = content.get(i);
                Toast.makeText(CharactersActivity.this, character.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
