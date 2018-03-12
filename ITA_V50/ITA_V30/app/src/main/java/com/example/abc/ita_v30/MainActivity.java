package com.example.abc.ita_v30;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    static int part = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        part = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LiveCapture(View view) {
        // Do something in response to button
        Intent intent1 = new Intent(this, LiveCapture.class);
        startActivity(intent1);
    }

    public void ChoosefromGallary2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChoosefromGallary2.class);
        startActivity(intent);
    }
}
