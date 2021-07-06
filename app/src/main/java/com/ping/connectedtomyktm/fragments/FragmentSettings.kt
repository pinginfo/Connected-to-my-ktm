package com.ping.connectedtomyktm.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.ping.connectedtomyktm.R
import com.ping.connectedtomyktm.notification.K9
import com.ping.connectedtomyktm.notification.OsmAnd
import com.ping.connectedtomyktm.tools.ContextObject
import com.ping.connectedtomyktm.tools.LocaleHelper

class FragmentSettings : Fragment() {
    private lateinit var openPermission: Button
    private lateinit var switchOsmAnd: Switch
    private lateinit var switchK9: Switch
    private lateinit var spinnerLanguage: Spinner
    private var languageArray = arrayListOf("EN", "FR")
    private var mContext: Context? = null
    private val contextObject = ContextObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)

        this.openPermission = view.findViewById(R.id.btnOpenPermission)
        this.switchOsmAnd = view.findViewById(R.id.switchOsmand)
        this.switchK9 = view.findViewById(R.id.switchK9)
        this.spinnerLanguage = view.findViewById(R.id.spinnerLanguage)

        this.contextObject.getSharedPreferences()?.also {
            this.switchOsmAnd.isChecked = it.getBoolean(OsmAnd.getPackageName(), true)
            this.switchK9.isChecked = it.getBoolean(K9.namePackage, false)
        }

        this.spinnerLanguage.adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.select_dialog_item,
                this.languageArray
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.openPermission.setOnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }

        this.switchOsmAnd.setOnCheckedChangeListener { _, isChecked ->
            this.contextObject.getSharedPreferences()?.also {
               it.edit{
                   this.putBoolean(OsmAnd.getPackageName(), isChecked)
               }
            }
        }
        this.switchK9.setOnCheckedChangeListener { _, isChecked ->
            this.contextObject.getSharedPreferences()?.also {
                it.edit {
                    this.putBoolean(K9.namePackage, isChecked)
                }
            }
        }

        val locale = this.mContext?.let { LocaleHelper.getLocale(it) }
        this.spinnerLanguage.setSelection(this.languageArray.indexOf(locale))
        this.spinnerLanguage.onItemSelectedListener =  object : Activity(), AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val language = parent?.getItemAtPosition(pos).toString()
                ContextObject.getContext()?.also {
                    ContextObject.setContext(LocaleHelper.setLocale(it, language))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        this.mContext = null
    }
}