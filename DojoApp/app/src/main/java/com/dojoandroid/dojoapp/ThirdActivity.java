package com.dojoandroid.dojoapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_third);

        TextView label = (TextView) findViewById(R.id.third_activity_label);

        Bundle bundle = getIntent().getExtras();
        label.setText(bundle.getString(SecondActivity.BUNDLE_TAG));
    }
}
