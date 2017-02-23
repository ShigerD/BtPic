package  com.imotor.BtPic.camera;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BluetoothActivity extends Activity {
	
	protected static final String ACTIVITY_TAG="MyLogTest";
	
	public static Boolean isconnected = false;
	public static BluetoothSocket mSocket = null;
	public static Handler handler = null;
    public  Bluetooth mybt=new Bluetooth();
    public static TextView tv;
    public static EditText et;
    
	/**
	 * 
	 */
	public  void Openblue() {
		BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mAdapter.isEnabled()) {
			Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enabler, 0x1);
		}	
		if(
				
				mAdapter.isEnabled()) {
			//	Toast.makeText(this, "�����ѿ���", Toast.LENGTH_SHORT).show();
		}
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
		Button search_blue = (Button) this.findViewById(R.id.bt_search);
		Button connect_blue = (Button) this.findViewById(R.id.bt_connect);
		Button btnsend = (Button) this.findViewById(R.id.next);
		
		//et=(EditText)findViewById(R.id.editText1);
		tv=(TextView)findViewById(R.id.textView1);
		Openblue();
		tv.setText("BlueTooth Open");
				

        handler = new Handler();
		

		search_blue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSearchBluetooth();
			    Toast.makeText(getApplicationContext(), "Search Completed!", Toast.LENGTH_SHORT).show();
			    //SystemClock.sleep(3000);
			    //while(mybt.btNameAdress=="Hi");
				//mybt.connect2Bt();		  
				//Toast.makeText(getApplicationContext(), "connect:"+mybt.btNameAdress, Toast.LENGTH_SHORT).show();
			}
		});

		connect_blue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mybt.connect2Bt();
				Toast.makeText(getApplicationContext(), "connect:"+mybt.btNameAdress, Toast.LENGTH_SHORT).show();
			}
		});		
	
		btnsend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//mybt.sendString();
				//tv.setText("bit Send");
				Intent intent =new Intent(BluetoothActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

    }
    /*
     * 
     */
    protected void mSearchBluetooth(){
		BroadcastReceiver mReceiver=mybt.Searchblue();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		IntentFilter filter2 = new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
		registerReceiver(mReceiver, filter2);
		tv.setText("BlueTooth Scaned");
    
    }

/*****************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.bt_search) {
    		mSearchBluetooth();
		    Toast.makeText(getApplicationContext(), "Search finished", Toast.LENGTH_SHORT).show();
            return true;
        }
        else  if (id == R.id.bt_connect) {
        	mybt.connect2Bt();
			Toast.makeText(getApplicationContext(), "connect:"+mybt.btNameAdress, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
