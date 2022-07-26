package com.example.shoppingcart.networkAndConnections

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*


val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // hc-05 UUID
private const val TAG = "bluetoothTag"
const val BARCODE_PACKET: Int = 0
const val WEIGHT_PACKET: Int = 1

class BluetoothThread(
    private var context: Context,
    private var adapter: BluetoothAdapter,
    private var handler: Handler
) : BluetoothOps {

    private var device: BluetoothDevice = adapter.getRemoteDevice("98:D3:81:FD:7D:AB")

    @SuppressLint("MissingPermission")
    var mmSocket: BluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)

    enum class Mode {
        Barcode,
        Weight
    }

    override fun checkPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            println()
            false
        } else {
            true
        }
    }

    @SuppressLint("MissingPermission")
    override fun enableBluetooth() {
        Log.d(TAG, "inEnable")
        if (!adapter.isEnabled) {
            adapter.enable()
            Toast.makeText(context, "Bluetooth activated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Bluetooth is already active", Toast.LENGTH_SHORT).show()
        }
    }

    override fun startDiscovery() {
        checkPermission()
        adapter.startDiscovery()
        Log.d(TAG, "inDiscovery")
    }

    @SuppressLint("MissingPermission")
    inner class ConnectThread : Thread() {
        override fun run() {
            super.run()
            adapter.cancelDiscovery()
            while (!mmSocket.isConnected) {
                try {
                    Log.d(TAG, "Connecting")
                    mmSocket.connect()
                    if (mmSocket.isConnected) {
                        Log.d(TAG, "Connected to " + mmSocket.remoteDevice.name)
                        break
                    }
                } catch (e: Exception) {
                    Log.d(TAG, e.toString())
                }
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the client socket", e)
            }
        }
    }

    inner class ConnectedThread : Thread() {
        private val mmInStream = mmSocket.inputStream
        private val mmBuffer = ByteArray(1024)

        override fun run() {
            super.run()
            var numBytes: Int
            val packet: MutableList<Char> = mutableListOf()
            var packetType: Mode = Mode.Weight
            var readMsg: Message

            while (true) {
                try {
                    numBytes = mmInStream.read(mmBuffer)
                } catch (e: IOException) {
                    Log.d(TAG, "Input stream was disconnected", e)
                    break
                }

                for (i in 0 until numBytes) {
                    when (mmBuffer[i].toInt()) {
                        37 -> {
                            packetType = Mode.Barcode
                        }
                        43 -> {
                            packetType = Mode.Weight
                        }
                        42 -> {
                            if (packetType == Mode.Barcode) {
                                readMsg = handler.obtainMessage(
                                    BARCODE_PACKET, numBytes, -1,
                                    packet.toCharArray().concatToString()
                                )
                                readMsg.sendToTarget()
                            } else if (packetType == Mode.Weight) {
                                readMsg = handler.obtainMessage(
                                    WEIGHT_PACKET, numBytes, -1,
                                    packet.toCharArray().concatToString()
                                )
                                readMsg.sendToTarget()
                            }
                            packet.clear()
                        }
                        else -> {
                            packet.add(mmBuffer[i].toInt().toChar())
                        }
                    }
                }
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // broadcast receiver to check the state of bluetooth whether it is open or not.
    val bluetoothConnection = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> {
                        enableBluetooth()
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        Toast.makeText(
                            context,
                            "Bluetooth is turning on please wait",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
