package com.example.managedconfig

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private lateinit var configManager: ManagedConfigManager
    private lateinit var containerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create a simple layout programmatically
        containerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
        }
        setContentView(containerLayout)

        configManager = ManagedConfigManager.getInstance(this)
        
        // Observe changes in managed configuration
        configManager.configData.observe(this) { configMap ->
            updateUI(configMap)
        }

        // Initial load of restrictions
        configManager.updateRestrictions()
    }

    private fun updateUI(configMap: Map<String, Any>) {
        containerLayout.removeAllViews()

        // Add title
        TextView(this).apply {
            text = "Managed Configuration Values"
            textSize = 24f
            setPadding(0, 0, 0, 32)
            containerLayout.addView(this)
        }

        // Add each config value in a card
        configMap.forEach { (key, value) ->
            MaterialCardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 16)
                }
                radius = 8f
                elevation = 4f

                val padding = (16 * resources.displayMetrics.density).toInt()
                setPadding(padding, padding, padding, padding)

                val contentLayout = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                }

                TextView(context).apply {
                    text = key
                    textSize = 18f
                    contentLayout.addView(this)
                }

                TextView(context).apply {
                    text = value.toString()
                    textSize = 16f
                    setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                    contentLayout.addView(this)
                }

                addView(contentLayout)
                containerLayout.addView(this)
            }
        }

        if (configMap.isEmpty()) {
            TextView(this).apply {
                text = "No managed configuration values found"
                textSize = 16f
                setPadding(0, 32, 0, 0)
                containerLayout.addView(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        configManager.updateRestrictions()
    }
}