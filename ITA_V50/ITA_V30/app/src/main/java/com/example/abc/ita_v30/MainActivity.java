package com.example.abc.ita_v30;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    static int part = 0;
    static String languagePair;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        part = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        int selectedid = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)findViewById(selectedid);
        //String lang = radioButton.getText().toString();
        /*if(lang == "French")
        {
            languagePair = "en-fr";
        }
        else if(lang == "Hindi")
        {
            languagePair = "en-hi";
        }
        else if(lang == "Kannada")
        {
            languagePair = "en-kn";
        }
        else if(lang == "Malyalam")
        {
            languagePair = "en-ml";
        }*/
        languagePair = "en-fr";

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
