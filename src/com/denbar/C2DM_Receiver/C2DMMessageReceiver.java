package com.denbar.C2DM_Receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/** Receives messages from the cloud via push notifications
 * (Google's C2DM)
 *
 * When it gets the message, it fires an intent that contains the message
 * payload.  The intent calls UsbFromC2DMService's onStartCommand() function
 * which then interprets the payload and sends a USB command to the robot
 */
public class C2DMMessageReceiver extends BroadcastReceiver {

	public static final String ROBOT_COMMAND_INTENT = "com.denbar.action.ROBOT_COMMMAND";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d("C2DM", "Message Receiver called");
		Toast.makeText(context, "Message Receiver called", Toast.LENGTH_SHORT).show();

		// check to see if this is a C2DM receive notification
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			Log.w("C2DM", "Received message");
			Toast.makeText(context, "Received message", Toast.LENGTH_SHORT).show();
			final String payload = intent.getStringExtra("payload");
			Log.d("C2DM", "dmControl: payload = " + payload);

			broadcastIntent(context, payload);
		}
	}

	void broadcastIntent(Context context, String RobotCommand) {
		Intent BroadcastIntent = new Intent(ROBOT_COMMAND_INTENT);
		BroadcastIntent.putExtra("command", RobotCommand);
		context.sendBroadcast(BroadcastIntent);
        Toast.makeText(context, RobotCommand, Toast.LENGTH_SHORT).show();
	}
}
