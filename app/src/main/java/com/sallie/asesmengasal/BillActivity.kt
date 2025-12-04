package com.sallie.asesmengasal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class BillActivity : AppCompatActivity() {

    // TextView declarations
    private lateinit var tvCustomer: TextView
    private lateinit var tvTableNumber: TextView
    private lateinit var tvOrder: TextView
    private lateinit var tvBill: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView

    // Header views (already populated by layout XML, but kept for completeness if needed)
    private lateinit var tvRestaurant: TextView
    private lateinit var tvCashier: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Memastikan layout yang dimodifikasi digunakan
        setContentView(R.layout.activity_bill)

        initializeViews()
        populateBillData()
    }

    /**
     * Initialize all TextView views from layout
     */
    private fun initializeViews() {
        // Inisialisasi TextView berdasarkan ID dari XML yang sudah dimodifikasi
        tvCustomer = findViewById(R.id.tvCustomer)
        tvTableNumber = findViewById(R.id.tvTableNumber)
        tvOrder = findViewById(R.id.tvOrder)
        tvBill = findViewById(R.id.tvBill)
        tvDate = findViewById(R.id.tvDate)
        tvTime = findViewById(R.id.tvTime)

        // Inisialisasi Header
        tvRestaurant = findViewById(R.id.tvRestaurant1)
        tvCashier = findViewById(R.id.tvCashier)
    }

    /**
     * Populate bill data from intent and current time
     * Data yang di-set di sini adalah NILAI SAJA, karena label sudah ada di XML.
     */
    private fun populateBillData() {
        // 1. Get data from intent with default values
        val customer = intent.getStringExtra("customer") ?: DEFAULT_VALUE
        val table = intent.getStringExtra("table") ?: DEFAULT_VALUE
        val order = intent.getStringExtra("order") ?: DEFAULT_VALUE
        val total = intent.getIntExtra("total", DEFAULT_TOTAL)

        // 2. Set customer and table info (HANYA NILAI)
        tvCustomer.text = customer
        tvTableNumber.text = table

        // 3. Set order details
        // Mengasumsikan 'order' adalah string yang sudah diformat (misal: "3x Nasi Goreng\n2x Es Teh")
        tvOrder.text = if (order.isNotEmpty()) order else DEFAULT_VALUE

        // 4. Set total bill (HANYA NILAI Rupiah)
        tvBill.text = formatRupiah(total)

        // 5. Set current date and time
        setCurrentDateTime()
    }

    /**
     * Set current date and time to TextViews
     */
    private fun setCurrentDateTime() {
        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

        tvDate.text = dateFormat.format(calendar.time)
        tvTime.text = timeFormat.format(calendar.time)
    }

    /**
     * Format integer amount to Indonesian Rupiah currency format
     * @param amount Amount to format
     * @return Formatted currency string (e.g., "Rp100.000")
     */
    private fun formatRupiah(amount: Int): String {
        val numberFormat = NumberFormat.getCurrencyInstance(INDONESIAN_LOCALE)
        numberFormat.maximumFractionDigits = NO_FRACTION_DIGITS
        // Mengganti symbol mata uang Rupiah dari format default (jika diperlukan)
        // Pada Locale("in", "ID"), hasilnya biasanya sudah "Rp"
        return numberFormat.format(amount)
    }

    companion object {
        // Constants
        private const val DEFAULT_VALUE = "-"
        private const val DEFAULT_TOTAL = 0
        private const val NO_FRACTION_DIGITS = 0
        private const val DATE_FORMAT = "dd/MM/yyyy"
        private const val TIME_FORMAT = "HH:mm:ss"

        // Locale for Indonesian Rupiah
        private val INDONESIAN_LOCALE = Locale("in", "ID")
    }
}