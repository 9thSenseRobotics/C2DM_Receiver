package com.denbar.C2DM_Receiver;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for the telo robot. Displays the GUI and allows you
 * to name the robot, see your registration key, and force a registration
 *
 */

public class C2DM_ReceiverActivity extends Activity {

	public final static String AUTH = "authentication";
	public final static String REGISTERED = "registered";

	private String bluetoothAddress;
	private EditText textbox, bluetoothAddressText, statusMessageText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fill in the name box
		Resources robotResources = getResources();
		SharedPreferences prefs = getSharedPreferences("com.denbar.C2DM_Receiver", MODE_WORLD_WRITEABLE );
		String phoneName = prefs.getString("phoneName", "default name");
		bluetoothAddress = prefs.getString("bluetooth",robotResources.getString(R.string.bluetoothAddress));
		setContentView(R.layout.main);

		C2DMApplication.getInstance().setBluetoothAddress(bluetoothAddress);

		textbox = (EditText) findViewById(R.id.phoneName);
		bluetoothAddressText = (EditText) findViewById(R.id.bluetoothAddress);
		statusMessageText = (EditText) findViewById(R.id.statusMessage);

		textbox.setText(phoneName);
		bluetoothAddressText.setText(bluetoothAddress);
		statusMessageText.setText(R.string.statusMessage);

	}

	/**
	 *  Called when you press the register button.
	 *  Starts the register service.
	 *
	 *  @param view
	 */
	public void register(View view) {
		// fire an intent to start the registration service
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.denbar.C2DM_Receiver.TeloStartupService");
		startService(serviceIntent);

	}

	/**
	 * Displays a toast with the registration ID
	 *
	 * @param view
	 */
	public void showRegistrationId(View view) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String string = prefs.getString(AUTH, "n/a");
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
		Log.d("C2DM RegId", string);

	}

	/**
	 * Saves the phone name in a shared preference
	 * (called when "Save Name" button is pressed)
	 * @param view
	 */
	public void saveNameAndAddress(View view) {
		// get the phone name out of the textbot
		EditText textbox = (EditText) findViewById(R.id.phoneName);
		EditText bluetoothAddressText = (EditText) findViewById(R.id.bluetoothAddress);
		SharedPreferences prefs = getSharedPreferences("com.denbar.C2DM_Receiver", MODE_WORLD_WRITEABLE );
		Editor edit = prefs.edit();
		String thisName = textbox.getText().toString();
		bluetoothAddress = bluetoothAddressText.getText().toString();

    	// do some checking to see if the entries are valid
		// check bluetooth
		boolean checkGood = true;
		String status = "Parameter entries:";
		if ( (!bluetoothAddress.contains(":")) || (!(bluetoothAddress.length() == 17)))
    	{
			status += " bluetooth wrong format";
			checkGood = false;
    	}

    	// check the name
		if (thisName.length() == 0) // will this throw an NPE?  check null?
		{
			status += " name missing ";
			checkGood = false;
		}

		if (checkGood)
		{
			edit.putString("bluetoothAddress", bluetoothAddress);
			edit.putString("phoneName", thisName);
			edit.commit();
			C2DMApplication.getInstance().setBluetoothAddress(bluetoothAddress);
			statusMessageText.setText("Name and bluetooth address saved");
		}
		else {
			statusMessageText.setText(status + "errors");
		}
	}
}