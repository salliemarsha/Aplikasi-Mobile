package com.sallie.asesmengasal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.*

class TransactionActivity : AppCompatActivity() {

    private lateinit var etCustomer: EditText
    private lateinit var etTable: EditText
    private lateinit var etSotoQuantity: EditText
    private lateinit var etTimloQuantity: EditText
    private lateinit var etTehQuantity: EditText
    private lateinit var etJerukQuantity: EditText
    private lateinit var etEsTehQuantity: EditText
    private lateinit var etEsJerukQuantity: EditText
    private lateinit var btCalculate: Button

    // Harga menu (dalam rupiah, integer)
    private val hargaSoto = 8000
    private val hargaTimlo = 10000
    private val hargaTeh = 2500
    private val hargaJeruk = 3000
    private val hargaEsTeh = 4000
    private val hargaEsJeruk = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transaction)

        init()
        initListener()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.transaction)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        etCustomer = findViewById(R.id.etCustomer)
        etTable = findViewById(R.id.etTable)
        etSotoQuantity = findViewById(R.id.etSotoQuantity)
        etTimloQuantity = findViewById(R.id.etTimloQuantity)
        etTehQuantity = findViewById(R.id.etTehQuantity)
        etJerukQuantity = findViewById(R.id.etJerukQuantity)
        etEsTehQuantity = findViewById(R.id.etEsTehQuantity)
        etEsJerukQuantity = findViewById(R.id.etEsJerukQuantity)
        btCalculate = findViewById(R.id.btCalculate)
    }

    private fun initListener() {
        btCalculate.setOnClickListener {
            hitungTransaksi()
        }
    }

    private fun hitungTransaksi() {
        val customer = etCustomer.text.toString().trim()
        val table = etTable.text.toString().trim()

        // Ambil jumlah, default 0 jika kosong / invalid
        val soto = etSotoQuantity.text.toString().toIntOrNull() ?: 0
        val timlo = etTimloQuantity.text.toString().toIntOrNull() ?: 0
        val teh = etTehQuantity.text.toString().toIntOrNull() ?: 0
        val jeruk = etJerukQuantity.text.toString().toIntOrNull() ?: 0
        val esteh = etEsTehQuantity.text.toString().toIntOrNull() ?: 0
        val esjeruk = etEsJerukQuantity.text.toString().toIntOrNull() ?: 0

        // Jika tidak ada item dan tidak ada nama/meja, beri notifikasi
        if (customer.isEmpty()) {
            Toast.makeText(this, "Masukkan nama pelanggan", Toast.LENGTH_SHORT).show()
            return
        }
        if (table.isEmpty()) {
            Toast.makeText(this, "Masukkan nomor meja", Toast.LENGTH_SHORT).show()
            return
        }
        if (soto + timlo + teh + jeruk + esteh + esjeruk == 0) {
            Toast.makeText(this, "Tidak ada pesanan", Toast.LENGTH_SHORT).show()
            return
        }

        // Hitung total
        val total = (soto * hargaSoto) +
                (timlo * hargaTimlo) +
                (teh * hargaTeh) +
                (jeruk * hargaJeruk) +
                (esteh * hargaEsTeh) +
                (esjeruk * hargaEsJeruk)

        // Susun daftar pesanan rapi (hanya item yang > 0)
        val pesananBuilder = StringBuilder()
        if (soto > 0) pesananBuilder.append("Soto x$soto = ${formatRupiah(soto * hargaSoto)}\n")
        if (timlo > 0) pesananBuilder.append("Timlo x$timlo = ${formatRupiah(timlo * hargaTimlo)}\n")
        if (teh > 0) pesananBuilder.append("Teh Panas x$teh = ${formatRupiah(teh * hargaTeh)}\n")
        if (jeruk > 0) pesananBuilder.append("Jeruk Panas x$jeruk = ${formatRupiah(jeruk * hargaJeruk)}\n")
        if (esteh > 0) pesananBuilder.append("Es Teh x$esteh = ${formatRupiah(esteh * hargaEsTeh)}\n")
        if (esjeruk > 0) pesananBuilder.append("Es Jeruk x$esjeruk = ${formatRupiah(esjeruk * hargaEsJeruk)}\n")
        val pesanan = pesananBuilder.toString().trim()

        // Kirim ke BillActivity
        val intent = Intent(this, BillActivity::class.java).apply {
            putExtra("customer", customer)
            putExtra("table", table)
            putExtra("order", pesanan)
            putExtra("total", total)
        }
        startActivity(intent)
    }

    // formatting rupiah tanpa desimal, e.g. "Rp12.000"
    private fun formatRupiah(amount: Int): String {
        val localeID = Locale("in", "ID")
        val nf = NumberFormat.getCurrencyInstance(localeID)
        nf.maximumFractionDigits = 0
        return nf.format(amount)
    }
}
