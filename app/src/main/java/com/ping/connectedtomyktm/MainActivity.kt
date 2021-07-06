package com.ping.connectedtomyktm

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ping.connectedtomyktm.bluetooth.Communication
import com.ping.connectedtomyktm.fragments.FragmentHome
import com.ping.connectedtomyktm.fragments.FragmentPreview
import com.ping.connectedtomyktm.fragments.FragmentSettings
import com.ping.connectedtomyktm.fragments.FragmentTesting
import com.ping.connectedtomyktm.notification.NotificationListener
import com.ping.connectedtomyktm.tools.ContextObject
import com.ping.connectedtomyktm.tools.LocaleHelper
import com.ping.connectedtomyktm.tools.MyToast
import com.ping.connectedtomyktm.viewModel.DataModel
import com.ping.connectedtomyktm.viewModel.IsConnectedModel

class MainActivity : AppCompatActivity() {
    private lateinit var navMenu: BottomNavigationView
    private lateinit var home: FragmentHome
    private lateinit var preview: FragmentPreview
    private lateinit var testing: FragmentTesting
    private lateinit var settings: FragmentSettings
    private lateinit var textViewError: TextView
    private lateinit var buttonPermission: Button

    private val model: DataModel by viewModels()
    private val connectedModel: IsConnectedModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: https://developer.android.com/guide/navigation/navigation-getting-started
        this.navMenu = findViewById(R.id.bottomNavigationView)
        this.home = FragmentHome()
        this.preview = FragmentPreview()
        this.testing = FragmentTesting()
        this.settings = FragmentSettings()
        this.buttonPermission = findViewById(R.id.buttonPermission)
        this.textViewError = findViewById(R.id.textViewError)

        ContextObject.setContext(this)
        LocaleHelper.loadLocale(this)?.also {
            ContextObject.setContext(it)
        }

        dataModel = this.model
        isConnectedModel = this.connectedModel
        MyToast.context = this
        ContextObject.getContext()?.let { Communication.setContext(it) }

        setCurrentFragment(this.home)

        this.navMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.preview-> setCurrentFragment(this.preview)
                R.id.home -> setCurrentFragment(this.home)
                R.id.testing -> setCurrentFragment(this.testing)
                R.id.settings -> setCurrentFragment(this.settings)
            }
            true
        }

        this.buttonPermission.setOnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }

        Intent(this, NotificationListener::class.java).also {
            startService(it)
        }
    }

    override fun onStart() {
        super.onStart()
        if (verifyNotificationPermission()) {
            this.buttonPermission.visibility = View.GONE
            this.textViewError.visibility = View.GONE
        } else {
            this.buttonPermission.visibility = View.VISIBLE
            this.textViewError.visibility = View.VISIBLE
        }
    }

    private fun verifyNotificationPermission(): Boolean {
        val theList = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val theListList = theList.split(":").toTypedArray()
        val me: String = ComponentName(this, NotificationListener::class.java).flattenToString()
        for (next in theListList) {
            if (me == next) return true
        }
        return false
    }

    private fun setCurrentFragment(f: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, f)
            commit()
        }

    companion object {
        lateinit var dataModel: DataModel
        lateinit var isConnectedModel: IsConnectedModel
    }
}