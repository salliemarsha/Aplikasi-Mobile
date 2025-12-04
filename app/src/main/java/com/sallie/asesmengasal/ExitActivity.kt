package com.sallie.asesmengasal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.activity.addCallback

class ExitActivity : AppCompatActivity() {

    // View declarations
    private lateinit var btnClose: ImageView
    private lateinit var tvExit: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnKeluar: Button
    private lateinit var btnBatal: Button
    private lateinit var cvExit2: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        init()
        setupClickListeners()
        setupViewCustomizations()
    }

    /**
     * Initialize all views from the layout
     */
    private fun init() {
        cvExit2 = findViewById(R.id.cvExit2)
        btnClose = findViewById(R.id.btnClose)
        tvExit = findViewById(R.id.tvExit)
        tvDescription = findViewById(R.id.tvDescription)
        btnKeluar = findViewById(R.id.btnKeluar)
        btnBatal = findViewById(R.id.btnBatal)
    }

    /**
     * Set up all click listeners for interactive elements
     */
    private fun setupClickListeners() {
        // Close button (X icon) - dismiss activity
        btnClose.setOnClickListener {
            finishWithAnimation()
        }

        // Exit button - close the app
        btnKeluar.setOnClickListener {
            exitApp()
        }

        // Cancel button - go back
        btnBatal.setOnClickListener {
            finishWithAnimation()
        }

        // Back button pressed - same as cancel
        onBackPressedDispatcher.addCallback(this) {
            finishWithAnimation()
        }
    }

    /**
     * Set up custom view styles and appearances
     */
    private fun setupViewCustomizations() {
        customizeButtonAppearance()
        setupCardViewShadow()
    }

    /**
     * Customize button appearance with rounded corners
     */
    private fun customizeButtonAppearance() {
        // Style for Exit button
        val exitButtonBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 12.dpToPx().toFloat()
            setColor(ContextCompat.getColor(this@ExitActivity, R.color.light_mode1))
        }
        btnKeluar.background = exitButtonBackground

        // Style for Cancel button
        val cancelButtonBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 12.dpToPx().toFloat()
            setColor(ContextCompat.getColor(this@ExitActivity, R.color.light_mode3))
            setStroke(1.dpToPx(), ContextCompat.getColor(this@ExitActivity, R.color.light_mode2))
        }
        btnBatal.background = cancelButtonBackground

        // Set ripple effect
        btnKeluar.isClickable = true
        btnBatal.isClickable = true
    }

    /**
     * Enhance CardView shadow and elevation
     */
    private fun setupCardViewShadow() {
        cvExit2.apply {
            elevation = 16f
            cardElevation = 16f
            maxCardElevation = 24f
        }
    }

    /**
     * Exit the application completely
     */
    private fun exitApp() {
        // Optional: Add analytics or logging here
        // Log.d("ExitActivity", "User exited the application")

        // Finish all activities and exit
        finishAffinity()

        // Optional: System exit (use with caution)
        // System.exit(0)
    }

    /**
     * Finish activity with smooth animation
     */
    private fun finishWithAnimation() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * Extension function to convert dp to pixels
     */
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    /**
     * Handle configuration changes if needed
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save any state if needed
    }

    /**
     * Clean up resources on destroy
     */
    override fun onDestroy() {
        super.onDestroy()
        // Clean up any listeners or resources
    }
}

// Optional extension for View visibility
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}