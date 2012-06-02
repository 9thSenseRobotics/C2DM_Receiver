package com.denbar.C2DM_Receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import at.abraxas.amarino.AmarinoIntent;

/** Receives messages from the cloud via push notifications
 * (Google's C2DM)
 *
 * When it gets the message, it fires an intent that contains the message
 * payload.  The intent calls UsbFromC2DMService's onStartCommand() function
 * which then interprets the payload and sends a USB command to the robot
 */
public class C2DMMessageReceiver extends BroadcastReceiver {

	private static final String TAG = "C2DMMessageReceiver";
	//public static final String ROBOT_COMMAND_INTENT = "com.denbar.action.ROBOT_COMMMAND";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d(TAG, "Message Receiver called");
		//Toast.makeText(context, "Message Receiver called", Toast.LENGTH_SHORT).show();

		// check to see if this is a C2DM receive notification
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			//Log.w("C2DM", "Received message");
			//Toast.makeText(context, "Received message", Toast.LENGTH_SHORT).show();
			final String payload = intent.getStringExtra("payload");
			Log.d(TAG, "message = " + payload);
			String message = "C2DM" + payload;
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			broadcastIntent(context, payload);
		}
	}

	void broadcastIntent(Context context, String RobotCommand) {
        // fire an intent that will send the data through amarino
		char cmdChar = RobotCommand.charAt(0);
		int commandedSpeed = 178;	// we will get this as a commanded value later
		Log.d(TAG, "message from C2DM: " + RobotCommand);
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.denbar.RobotComm.RobotCommService");
		serviceIntent.putExtra("C2DMmessage", RobotCommand);
		// if (data.startsWith("echo"))
		// _echoReceivedTime =
		// System.currentTimeMillis();
		context.startService(serviceIntent);

		//Intent intentAmarino = new Intent(AmarinoIntent.ACTION_SEND);
		//intentAmarino.putExtra(AmarinoIntent.EXTRA_DEVICE_ADDRESS, "00:06:66:46:5A:91");
		//String bluetoothAddress = C2DMApplication.getInstance().getBluetoothAddress();
		//intentAmarino.putExtra(AmarinoIntent.EXTRA_DEVICE_ADDRESS,bluetoothAddress);
		//intentAmarino.putExtra(AmarinoIntent.EXTRA_DATA_TYPE, AmarinoIntent.INT_EXTRA);
		//intentAmarino.putExtra(AmarinoIntent.EXTRA_FLAG, cmdChar);
		//intentAmarino.putExtra(AmarinoIntent.EXTRA_DATA, commandedSpeed);
		//context.sendBroadcast(intentAmarino);
        Toast.makeText(context, "C2DM Command: " + RobotCommand, Toast.LENGTH_SHORT).show();
        //sendDataToServer(robotCommand);	// echo the command back to the C2DM server

		// also fire a general intent for robot commmands
        //Intent BroadcastIntent = new Intent(ROBOT_COMMAND_INTENT);
		//BroadcastIntent.putExtra("robotCommand", RobotCommand);
		//context.sendBroadcast(BroadcastIntent);
        //Toast.makeText(context, RobotCommand, Toast.LENGTH_SHORT).show();
	}
}
