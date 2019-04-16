package com.example.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data;
    String dataresult;

    String search;


    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        try {
            url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?lang=ar-en&key=trnsl.1.1.20190218T112754Z.175e64a454c72ee7.a5ce96aea0d966e70e5599d65a38f849e6403b37&text="+search);
            HttpURLConnection httpURLConnection;
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while(line !=null){
                line=bufferedReader.readLine();
                data=data+line;
            }
            JSONArray ja=new JSONArray(data);
            JSONObject jo = (JSONObject) ja.get(0);
            dataresult= (String) jo.get("lang");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       // Intent i=getPackageManager().getLaunchIntentForPackage("com.whatsapp");
        //   startActivity(i);

       // int f=this.data.indexOf("text");
        int l=this.data.indexOf("]");
        String order=this.data.substring(40,l-1);
        MainActivity.openapp=order;
        MainActivity.text_view_result.setText(order);
    }
}
