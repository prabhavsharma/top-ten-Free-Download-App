package com.prabhav.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public TextView txtview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtview =(TextView) findViewById(R.id.txt_xml);
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");


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

    private class DownloadData extends AsyncTask<String, Void, String> {
        private String mfilecontents;
@Override
protected void onPostExecute(String result)
{
    super.onPostExecute(result);
    Log.d("DownloadData","Result was:"+result);
    txtview.setText(mfilecontents);
}
        @Override
        protected String doInBackground(String... params) {
            mfilecontents = downloadXMLFlile(params[0]);
            if (mfilecontents == null) {
                Log.d("DownloadData", "Error downloading");
            }
            return mfilecontents;
        }

        private String downloadXMLFlile(String urlpath) {
            StringBuilder strbuffer = new StringBuilder();
            try {
                // connection to url and oppening the Http Connection
                URL url = new URL(urlpath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // getting a response code from the server
                int response = connection.getResponseCode();
                Log.d("DownloadData", "Connection Eastablished with response code " + response);
                // reading from the file
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int charRead;
                //reading 500 bytes a time
                char[] inputbuffer = new char[500];
                while (true) {
                    charRead = inputStreamReader.read(inputbuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    strbuffer.append(String.copyValueOf(inputbuffer, 0, charRead));
                }


                return strbuffer.toString();
            }
            catch(IOException e)
            {
                Log.d("DownloadData", "Ioexception occured" + e.getMessage());
            }
            //Handling No Internet Permission
            catch(SecurityException e)
            {
                Log.d("DownloadData","Security Exception"+e.getMessage());
            }
            return null;
        }
    }
}