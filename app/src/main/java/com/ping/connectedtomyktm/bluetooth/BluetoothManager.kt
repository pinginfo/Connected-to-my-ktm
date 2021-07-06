package com.ping.connectedtomyktm.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothSocket
import android.content.Context
import com.ping.connectedtomyktm.MainActivity
import com.ping.connectedtomyktm.entities.SendingObject
import java.io.IOException
import java.io.OutputStream
import java.lang.RuntimeException
import java.util.UUID

class BluetoothManager(context: Context) {
    private var context: Context = context
    private val ktmBluetoothUUID: UUID = UUID.fromString("cc4c1fb3-482e-4389-bdeb-57b7aac889ae")
    private var outputStream: OutputStream? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothDevice: BluetoothDevice? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var bluetoothProxy: BluetoothProfile? = null

    private val bluetoothListener = object: BluetoothProfile.ServiceListener {
        override fun onServiceConnected(profile: Int, proxy: BluetoothProfile): Unit =
            proxy.connectedDevices.forEach {
                if (isKtmDevice(it.name) && it.bondState == BluetoothDevice.BOND_BONDED) {
                    bluetoothDevice = it
                    bluetoothProxy = proxy
                }
            }

        override fun onServiceDisconnected(profile: Int) {
            close()
        }
    }

    fun connect() {
        close()
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (this.bluetoothAdapter?.isEnabled == true) {
            if (this.bluetoothDevice == null || !initConnection(this.bluetoothDevice!!)) {
                this.bluetoothAdapter?.bondedDevices?.forEach {
                    if (initConnection(it)) {
                        MainActivity.isConnectedModel.select(true)
                        send(SendingObject().getBytes(idMessage++))
                        send(SendingObject().getBytes(idMessage++))
                        return
                    }
                }
                close()
            }
        }
    }

    fun send(value: ByteArray?) {
        try {
            this.outputStream?.also {
                it.flush()
                it.write(value)
                it.flush()
            }
        } catch (e: IOException) {
            close()
        }
    }

    fun close(): Unit = try {
        this.bluetoothSocket?.close()
        this.bluetoothSocket = null
        this.outputStream?.close()
        this.outputStream = null
        this.bluetoothAdapter?.closeProfileProxy(BluetoothProfile.A2DP, bluetoothProxy)
        this.bluetoothAdapter = null
        this.bluetoothDevice = null
        idMessage = 0
        MainActivity.isConnectedModel.select(false)
    } catch (e: IOException) {
        throw RuntimeException("An error occurred when closing the connection", e)
    }

    private fun initConnection(bluetoothDevice: BluetoothDevice): Boolean {
        if (!isKtmDevice(bluetoothDevice.name)) { return false }
        return try {
            this.bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(this.ktmBluetoothUUID)
            this.outputStream = this.bluetoothSocket?.outputStream
            this.bluetoothSocket?.connect()
            true
        } catch (e: IOException) {
            this.bluetoothSocket?.close()
            this.bluetoothSocket = null
            this.outputStream?.close()
            this.outputStream = null
            false
        }
    }

    private fun isKtmDevice(name: String): Boolean {
        // Todo: Allow to user to select the name of his motorcycle
        return name.contains("KTM") || name.contains("LC8")
    }

    init {
        if (!BluetoothAdapter.getDefaultAdapter().getProfileProxy(this.context, bluetoothListener, BluetoothProfile.A2DP)) {
            throw RuntimeException("An error occurred while selecting the BluetoothAdapter profile")
        }
    }

    companion object {
        var idMessage: Int = 0
    }
}
