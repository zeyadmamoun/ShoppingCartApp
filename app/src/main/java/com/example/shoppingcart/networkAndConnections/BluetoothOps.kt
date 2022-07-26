package com.example.shoppingcart.networkAndConnections

// here an interface that include all bluetooth operations we need in the project.
interface BluetoothOps {
    fun checkPermission() : Boolean
    fun enableBluetooth()
    fun startDiscovery()
}