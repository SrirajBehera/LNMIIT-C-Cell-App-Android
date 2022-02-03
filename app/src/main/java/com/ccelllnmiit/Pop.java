package com.ccelllnmiit;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

public class Pop extends AppCompatActivity {

    TextView h1,h2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);
        Slidr.attach(this);



        h1=findViewById(R.id.textView4);
        h2=findViewById(R.id.textView5);

        Intent myIntent =getIntent();
        if (myIntent.hasExtra("copyname"))
        {
            h1.setText(myIntent.getStringExtra("copyname"));
        }
        if (myIntent.hasExtra("copycountry"))
        {
            h2.setText(myIntent.getStringExtra("copycountry"));
        }

        h2.setMovementMethod(BetterLinkMovementMethod.newInstance());
        h2.setMovementMethod(new ScrollingMovementMethod());

        if(h2.getText().toString().contains("https://"))
        {
            String strr=h2.getText().toString();
            String url="";
            int i=strr.lastIndexOf("https");
            for(;i<strr.length() && strr.charAt(i)!=' ' && strr.charAt(i)!='\n' && strr.charAt(i)!='\r';i++)
            {
                Log.d("index",String.valueOf(i));
                url+=strr.charAt(i);
            }
            Linkify.addLinks(h2,Linkify.WEB_URLS);
        }

       /* h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(h2.getText().toString().contains("https://"))
                {
                    String strr=h2.getText().toString();
                    String url="";
                    int i=strr.lastIndexOf("https");
                    for(;i<strr.length() && strr.charAt(i)!=' ' && strr.charAt(i)!='\n' && strr.charAt(i)!='\r';i++)
                    {
                        Log.d("index",String.valueOf(i));
                        url+=strr.charAt(i);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        });*/



        FloatingActionButton fab= findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }







}
