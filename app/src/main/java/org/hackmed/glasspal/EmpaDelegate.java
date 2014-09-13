package org.hackmed.glasspal;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;

public class EmpaDelegate implements EmpaDataDelegate, EmpaStatusDelegate {

    private final Context context;
    private final Activity activity;
    private EmpaDeviceManager deviceManager;
    private static final String TAG = "EmpaDelegate";

    public EmpaDelegate(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        deviceManager = new EmpaDeviceManager(context, this, this);
    }

    @Override
    public void didReceiveBVP(float v, double v2) {
        Log.i(TAG, "BVP: " + v + " " + v2);
    }

    @Override
    public void didReceiveGSR(float v, double v2) {
        Log.i(TAG, "SGR: " + v + " " + v2);
    }

    @Override
    public void didReceiveIBI(float v, double v2) {
        Log.i(TAG, "IBI: " + v + " " + v2);
    }

    @Override
    public void didReceiveTemperature(float v, double v2) {
        Log.i(TAG, "Temperature: " + v + " " + v2 );
    }

    @Override
    public void didReceiveAcceleration(int i, int i2, int i3, double v) {
        Log.i(TAG, "Acceleration: " + i + " " + i2 + " " + i3 + " " + v );
    }

    @Override
    public void didReceiveBatteryLevel(float v, double v2) {
        Log.i(TAG, "Battery: " + v + " " + v2);
    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {
        Log.i(TAG, "Update status: " + status);
        if (status == EmpaStatus.READY) {
            // Start scanning
            deviceManager.startScanning();
            // The device manager has established a connection
        } else if (status == EmpaStatus.CONNECTED) {
        }
    }

    @Override
    public void didUpdateSensorStatus(EmpaSensorStatus empaSensorStatus, EmpaSensorType empaSensorType) {

    }

    @Override
    public void didDiscoverDevice(BluetoothDevice device, int i, boolean allowed) {
        Log.i(TAG, "Did discover");
// Stop scanning. The first allowed device will do.
        if (allowed) {
            deviceManager.stopScanning();
            // Connect to the device
            try {
                deviceManager.connectDevice(device);
                // Depending on your configuration profile, you might be unable to connect to a device.
                // This should happen only if you try to connect when allowed == false.
            } catch (ConnectionNotAllowedException e) {
                Toast.makeText(context, "Sorry, can't connect to this device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void didRequestEnableBluetooth() {
        Log.i(TAG,"Did request enable BT");
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);
    }

    public void connect() {
        Log.i(TAG, "connecting");
        deviceManager.authenticateWithAPIKey("fdfe03151260495f854a9338d115d6b2");
    }
}
