package com.example.managedconfig

import android.content.Context
import android.content.RestrictionsManager
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

class ManagedConfigManager(private val context: Context) {
    private val restrictionsManager = context.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager
    private val _configData = MutableLiveData<Map<String, Any>>()
    val configData: LiveData<Map<String, Any>> = _configData

    fun updateRestrictions() {
        val restrictions = restrictionsManager.applicationRestrictions ?: Bundle()
        val configMap = mutableMapOf<String, Any>()

        restrictions.keySet().forEach { key ->
            when {
                restrictions.containsKey(key) -> {
                    val value = when (key) {
                        "server_url" -> restrictions.getString(key, "")
                        "enable_feature_x" -> restrictions.getBoolean(key, false)
                        "max_items" -> restrictions.getInt(key, 10)
                        "api_key" -> restrictions.getString(key, "")
                        else -> restrictions.get(key)
                    }
                    if (value != null) {
                        configMap[key] = value
                    }
                }
            }
        }

        _configData.value = configMap
    }

    companion object {
        private var instance: ManagedConfigManager? = null

        fun getInstance(context: Context): ManagedConfigManager {
            return instance ?: synchronized(this) {
                instance ?: ManagedConfigManager(context.applicationContext).also { instance = it }
            }
        }
    }
}