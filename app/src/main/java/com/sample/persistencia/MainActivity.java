package com.sample.persistencia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateText();
    }

    public void contadorClick(View view) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        int counter = sharedPrefs.getInt("contador", 0);
        counter++;
        sharedPrefs.edit().putInt("contador", counter).commit();
        updateText();
    }

    private void updateText() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        int counter = sharedPrefs.getInt("contador", 0);

        String sampleText = sharedPrefs.getString("example_text", "Hola");

        String newText = counter + "/" + sampleText;

        ((TextView)findViewById(R.id.helloText)).setText(newText);

        String FILENAME = "persistence.html";
        StringBuilder string = new StringBuilder();
        string.append("<html><head><title>test</title></head><body style='background-color:red'><div 'style=color:black'>").append(newText).append("</div></body></html>");

        Log.i(this.getClass().getName(),"Writing:"+string.toString());

        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(FILENAME,Context.MODE_PRIVATE));
            out.write(string.toString());
            out.close();
        } catch (Exception e) {
            Log.e(this.getClass().getName(),"Error writing file", e);
        }

        WebView webView = (WebView) findViewById(R.id.webView);

        File htmlFile = this.getFileStreamPath(FILENAME);
        Log.i(this.getClass().getName(), "Opening:" + htmlFile.getAbsolutePath());
        webView.loadUrl("file://"+htmlFile.getAbsolutePath());
        //webView.loadData(string.toString(), "text/html", "UTF-8");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
