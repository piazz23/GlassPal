package org.hackmed.glasspal;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;

/**
 * Created by michele on 13/09/14.
 */
public class EmpaDelegate implements EmpaDataDelegate, EmpaStatusDelegate {

    private EmpaDeviceManager deviceManager;

    public EmpaDelegate(Context context) {
        deviceManager = new EmpaDeviceManager(context, this, this);
    }

    @Override
    public void didReceiveGSR(float v, double v2) {

    }

    @Override
    public void didReceiveBVP(float v, double v2) {

    }

    @Override
    public void didReceiveIBI(float v, double v2) {

    }

    @Override
    public void didReceiveTemperature(float v, double v2) {

    }

    @Override
    public void didReceiveAcceleration(int i, int i2, int i3, double v) {

    }

    @Override
    public void didReceiveBatteryLevel(float v, double v2) {

    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {
// The device manager is ready for use
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
    public void didDiscoverDevice(BluetoothDevice bluetoothDevice, int i, boolean b) {

    }

    @Override
    public void didRequestEnableBluetooth() {

    }

    public void connect() {
        deviceManager.authenticateWithAPIKey("fdfe03151260495f854a9338d115d6b2");
    }
}
