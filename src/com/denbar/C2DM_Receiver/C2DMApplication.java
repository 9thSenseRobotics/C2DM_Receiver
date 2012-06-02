package com.denbar.C2DM_Receiver;


import android.app.Application;
import android.content.res.Configuration;

public class C2DMApplication extends Application {

    private static C2DMApplication singleton;

    // global variables
    private boolean _bluetoothConnected;
    private String _bluetoothAddress;


    public static C2DMApplication getInstance()
    {
    		return singleton;
    }

    // setup global variables here
    // these methods can be called from any of the routines.
    // For example,
    // int valuetest = 10;
    // XMPPApplication.getInstance().setGlobalStateValue(valuetest);
    // or
    // int valuetest = XMPPApplication.getInstance().getGlobalStateValue();

    public void setBluetoothAddress(String value)
    {
    	_bluetoothAddress = value;
    }

    public String getBluetoothAddress()
    {
    	return _bluetoothAddress;
    }

    public boolean getBluetoothConnected()
    {
    	return _bluetoothConnected;
    }

    public void setBluetoothConnected(boolean value)
    {
    	_bluetoothConnected = value;
    }

    @Override
    public final void onCreate() {
    	super.onCreate();
    	singleton = this;
    	_bluetoothAddress = "incorrect address";  // this needs to set by the program,
    		// otherwise every bot would needs its own compiled software version
    	_bluetoothConnected = false;
    }

    // other life cycle events are included here just as a reminder of what overrides are available
    @Override
    public final void onTerminate() {
    	super.onTerminate();
    }

    @Override
    public final void onLowMemory() {
    	super.onLowMemory();
    }

    @Override
    public final void onConfigurationChanged(Configuration newConfig)
    {
    	super.onConfigurationChanged(newConfig);
    }
}
