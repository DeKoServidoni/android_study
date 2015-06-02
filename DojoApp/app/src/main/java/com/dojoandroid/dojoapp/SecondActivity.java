package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity {

    public static final String BUNDLE_TAG = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        Button next = (Button) findViewById(R.id.second_activity_button);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_TAG,"Olá aqui é a SecondActivity!");

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
