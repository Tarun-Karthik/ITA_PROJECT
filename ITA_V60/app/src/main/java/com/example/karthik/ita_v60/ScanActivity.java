package com.example.karthik.ita_v60;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class ScanActivity extends AppCompatActivity {

    static int part = 0;
    private StringBuffer language;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button button;
    private String languagePair;
    Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        part = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        button = (Button)findViewById(R.id.btnDisplay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioGroup.getCheckedRadioButtonId();
                View radio = radioGroup.findViewById(id);
                int rid = radioGroup.indexOfChild(radio);
                radioButton = (RadioButton) radioGroup.getChildAt(rid);
                String lang = (String) radioButton.getText();
                TextView textView = (TextView)findViewById(R.id.output);
                textView.setText(lang);
                //Toast.makeText(con, lang,LENGTH_LONG).show();
                language = new StringBuffer("en-en");
                if(lang.equals("French"))
                {
                    language.delete(0,language.length());
                    language.append("en-fr");
                    //language = new StringBuffer("en-fr");
                    //languagePair = "en-fr";
                }
                else if(lang.equals("Hindi"))
                {
                    language.delete(0,language.length());
                    language.append("en-hi");
                    //language = new StringBuffer("en-hi");
                    Log.w("Language", "Hindi");
                    //languagePair = "en-hi";
                }
                else if(lang.equals("Kannada"))
                {
                    language.delete(0,language.length());
                    language.append("en-kn");
                   // language = new StringBuffer("en-kn");
                    Log.w("Language", "Kannada");
                    // languagePair = "en-kn";
                }
                else if(lang.equals("Malayalam"))
                {
                    language.delete(0,language.length());
                    language.append("en-ml");
                    //language = new StringBuffer("en-ml");
                    Log.w("Language", "Malayalam");
                    // languagePair = "en-ml";
                }
            }
        });

        Button livecapture = (Button)findViewById(R.id.button);
        livecapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(con, LiveCapture.class);
                Bundle bundle = new Bundle();
                bundle.putString("lang",language.toString());
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        Button choosefromgallary = (Button)findViewById(R.id.button2);
        choosefromgallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(con, ChoosefromGallary.class);
                Bundle bundle = new Bundle();
                bundle.putString("lang",language.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



}