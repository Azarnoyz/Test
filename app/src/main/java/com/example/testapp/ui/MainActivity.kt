package com.example.testapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testapp.R
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.list.TodoListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(TodoListFragment())
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }

    @SuppressLint("NewApi")
    fun networkConnectionListener(connection: (result: Boolean) -> Unit) {

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    connection.invoke(true)
                }

                override fun onLost(network: Network) {
                    connection.invoke(false)
                }

            })
        } else {

            val builder = NetworkRequest.Builder()
            val networkRequest = builder.build()
            connectivityManager.registerNetworkCallback(networkRequest,
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        connection.invoke(true)
                        super.onAvailable(network)
                    }

                    override fun onLost(network: Network) {
                        connection.invoke(false)
                        super.onLost(network)
                    }
                })
        }
        val currentNetwork = connectivityManager.activeNetwork
        if (currentNetwork == null) connection.invoke(false)
    }
}