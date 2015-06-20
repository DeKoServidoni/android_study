package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dojoandroid.dojoapp.adapters.CharacterAdapter;
import com.dojoandroid.dojoapp.entity.Character;
import com.dojoandroid.dojoapp.task.CharactersTask;

import java.util.ArrayList;
import java.util.List;

public class CharactersActivity extends Activity implements CharactersTask.CharactersTaskCallback {

//    private int[] mImages = {R.drawable.c3po, R.drawable.chewbacca, R.drawable.hansolo, R.drawable.leia,
//                                R.drawable.luke, R.drawable.obiwan, R.drawable.r2d2, R.drawable.vader,
//                                R.drawable.jabba, R.drawable.maul, R.drawable.boba};
//
//    private String[] mNames = {"C3PO", "CHEWBACCA", "HANSOLO", "LEIA", "LUKE", "OBI WAN KENOBI",
//                                "R2D2", "DARTH VADER", "JABBA THE HUT", "DARTH MAUL", "BOBA FETT"};

    private ListView mList = null;
    private List<Character> mContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_characters);

//        final ArrayList<Character> content = new ArrayList<>();
//
//        for(int i=0 ; i<mImages.length ; i++) {
//            Character character = new Character();
//            character.setResource(mImages[i]);
//            character.setName(mNames[i]);
//
//            content.add(character);
//        }

        mList = (ListView) findViewById(R.id.list_characters);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character character = mContent.get(i);
                Toast.makeText(CharactersActivity.this, character.getName(), Toast.LENGTH_LONG).show();
            }
        });

        CharactersTask task = new CharactersTask(this, this);
        task.execute();
    }

    @Override
    public void onFinishWithError() {
        Toast.makeText(CharactersActivity.this, "Erro ao carregar lista!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishWithSuccess(List<Character> content) {
        mContent.clear();
        mContent.addAll(content);

        CharacterAdapter adapter = new CharacterAdapter(this, mContent);
        mList.setAdapter(adapter);
    }
}
