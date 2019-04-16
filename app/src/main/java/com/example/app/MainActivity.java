package com.example.app;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;



import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    private TextView txvResult;
    public static TextView  text_view_result;
    public TextToSpeech myTTS;
    public static String openapp="";
Button getdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = (TextView) findViewById(R.id.txvResult);
        text_view_result=(TextView) findViewById(R.id.text_view_result);
        getdata=(Button) findViewById(R.id.getdata);
       getdata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(Intent.ACTION_DIAL);
               i.setData(Uri.parse("tel:+0201003975129"));
               startActivity(i);
             /*  Intent i=getPackageManager().getLaunchIntentForPackage("com.whatsapp");
               startActivity(i);*/
               /*try {
                   String text = "This is a test";// Replace with your message.

                   String toNumber = "+0201003975129"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                   //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                   startActivity(intent);
               }
               catch (Exception e){
                   e.printStackTrace();
               }*/
           }
       });
      //  Toast.makeText(context: this ,text:"aprobado",Toast.LENGTH_SHORT).show();
    //    Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

/*       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
initializeTextToSpeech();


    }

    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(myTTS.getEngines().size()==0){
                    Toast.makeText(MainActivity.this,"there is no TTS in your device",Toast.LENGTH_LONG).show();
                }else{
                   // Locale loc = new Locale("ar");

                 //   myTTS.setLanguage(loc);
                    myTTS.setLanguage(Locale.getDefault());
                    speak("السلام عليكم");

                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT>=21){
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }else{
            myTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }

    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    fetchData process =new fetchData();
                    process.search=result.get(0);
                    process.execute();
                  goopenapp(openapp);
                    //uncomment to open whatsapp
                //    Intent i=getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                 //   startActivity(i);
                 //   txvResult.setText(result.get(0));

                }
                break;
        }
    }

    private void goopenapp(String openapp) {
        openapp.toLowerCase();

        if (openapp.contains("WhatsApp")) {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            startActivity(i);
        }else {
            if (openapp.contains("Contact")) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:+0201003975129"));
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
