package com.example.martiansapp.picar;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class MainActivity extends android.app.Activity {

    private Socket mSocket; // just copy
    {
        try {
            mSocket = IO.socket("http://10.132.161.109:8000");//change ip 
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButton1 = (Button) findViewById(R.id.button1); // link button on xml to button in program
        Button mButton2 = (Button) findViewById(R.id.button2);
        Button mButton3 = (Button) findViewById(R.id.button3);
        SeekBar mSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        mSocket.connect();

        mButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { // thing do inside onClick()
                Context context = getApplicationContext();//just copy
                try {
                    JSONObject json = new JSONObject();
                    json.putOpt("state", 255);//this is a json
                    mSocket.emit("servo_control", json.toString());// servo_control is name of channel ,

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
               // mSocket.emit("servo_control", "{\"state\":255}");
                Toast.makeText(context, "Clicked on Button", Toast.LENGTH_LONG).show();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    JSONObject json = new JSONObject();
                    json.putOpt("state", 10);
                    mSocket.emit("servo_control", json.toString());

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                //mSocket.emit("servo_control", {"state":10});
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    JSONObject json = new JSONObject();
                    json.putOpt("state", 10);
                    mSocket.emit("servo_control", json.toString());

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                //mSocket.emit("servo_control", "{\"state\":120}");

            }
        });
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
