package com.kelvsricafort101.wordpress.inventory.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkMonitor(context: Context) {
    private val connectivityMonitor = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isOnline(): Boolean {
        val network = connectivityMonitor.activeNetwork ?: return false

        val capabilities = connectivityMonitor.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }

    fun registerOnAvailableListener(onAvailable: () -> Unit): ConnectivityManager.NetworkCallback {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailable()
            }
        }

        connectivityMonitor.registerDefaultNetworkCallback(callback)

        return callback
    }

    fun unregister(
        callback: ConnectivityManager.NetworkCallback
    ) {
        connectivityMonitor.unregisterNetworkCallback(callback)
    }
}