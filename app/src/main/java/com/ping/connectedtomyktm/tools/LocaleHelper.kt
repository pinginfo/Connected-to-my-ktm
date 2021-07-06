package com.ping.connectedtomyktm.tools

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

class LocaleHelper {
    companion object {
        fun setLocale(context: Context, language: String): Context {
            persist(context, language)

            return updateResources(context, language)
        }

        fun loadLocale(context: Context): Context? {
            val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val language: String? = sharedPref.getString("language", "en")

            return language?.let { updateResources(context, it) }
        }

        fun getLocale(context: Context): String? {
            return context.getSharedPreferences("Settings", Context.MODE_PRIVATE).getString("language", "en")
        }

        private fun persist(context: Context, language: String) {
            val sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            sharedPref.putString("language", language)
            sharedPref.apply()
        }

        private fun updateResources(context: Context, language: String): Context {
            // TODO: Fix this shit, deprecated
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources: Resources = context.resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
            return context
        }
    }
}