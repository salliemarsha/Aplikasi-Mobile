package com.sallie.asesmengasal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var cvForm: CardView
    private lateinit var cvCalculator: CardView
    private lateinit var cvRestaurant: CardView
    private lateinit var cvTemperature: CardView
    private lateinit var cvProfile: CardView
    private lateinit var cvExit: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        cvForm = findViewById(R.id.cvForm)
        cvCalculator = findViewById(R.id.cvCalculator)
        cvRestaurant = findViewById(R.id.cvRestaurant)
        cvTemperature = findViewById(R.id.cvTemperature)
        cvProfile = findViewById(R.id.cvProfile)
        cvExit = findViewById(R.id.cvExit)

        cvForm.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        cvCalculator.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        cvRestaurant.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }

        cvTemperature.setOnClickListener {
            startActivity(Intent(this, TemperatureConversionActivity::class.java))
        }

        cvProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        cvExit.setOnClickListener {
            startActivity(Intent(this, ExitActivity::class.java))
        }
    }
}
