package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.omni.mylibrary.JokesActivity;
import com.udacity.gradle.jokes.Joker;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JokesAsyncTask().execute();
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

    public void tellJoke(View view){
        Joker joker = new Joker();
        Toast.makeText(this,joker.getJoke() , Toast.LENGTH_SHORT).show();
    }

    public void launchLibraryActivity(View view) {
        Intent myIntent = new Intent(this, JokesActivity.class);
        Joker joker = new Joker();
        myIntent.putExtra("Joke" ,joker.getJoke());
        startActivity(myIntent);
    }

    class JokesAsyncTask extends AsyncTask<Void, Void, String> {
        private  MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... voids) {
            if(myApiService == null){
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("http://192.168.10.5:8080/_ah/api");

                myApiService = builder.build();
            }

            try{
                return myApiService.sayAJoke().execute.getData();
            } catch (IOException e){
                return e.getMessage();
            }


        }


        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}