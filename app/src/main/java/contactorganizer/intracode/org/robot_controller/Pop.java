package contactorganizer.intracode.org.robot_controller;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.Set;
import android.content.IntentFilter;
import android.widget.AdapterView.OnItemClickListener;
/**
 * Created by macmini on 1/8/16.
 */

public class Pop extends Activity  {


ArrayAdapter<String> listAdapter;
    ListView listview;
    BluetoothAdapter btAdapter;
Set<BluetoothDevice> devicesArray;
    ArrayList<String> pairedDevices;
    IntentFilter filter;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));


    }





    public String start = "false";




    public void start(View v) {
        SharedPreferences sharedPref = getSharedPreferences("robotinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("start", "true");
        editor.apply();
        start = "true";
        finish();
    }



}


