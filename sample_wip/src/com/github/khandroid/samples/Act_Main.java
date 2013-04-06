package com.github.khandroid.samples;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class Act_Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act__main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.act__main, menu);
        return true;
    }

}
