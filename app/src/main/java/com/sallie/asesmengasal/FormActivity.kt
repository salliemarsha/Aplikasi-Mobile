package com.sallie.asesmengasal

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etAddress: EditText
    lateinit var etNumber: EditText
    lateinit var spReligion: Spinner
    lateinit var rgGender: RadioGroup
    lateinit var rbMan: RadioButton
    lateinit var rbWoman: RadioButton
    lateinit var cbDance: CheckBox
    lateinit var cbSing: CheckBox
    lateinit var cbRead: CheckBox
    lateinit var cbCook: CheckBox
    lateinit var btRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_form)

        init()

        // Langkah 1 : Membuat spinner untuk pilihan agama
        val agamaList = arrayOf("Pilih", "Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spReligion.adapter = adapter

        // Langkah 2 : Memfungsikan tombol daftar
            btRegister.setOnClickListener {
            val nama = etName.text.toString()
            val alamat = etAddress.text.toString()
            val nomorhp = etNumber.text.toString()
            val agama = spReligion.selectedItem.toString()

            // Validasi : jika agama belum dipilih
            if (agama == "Pilih") {
                Toast.makeText(this, "Pilih agama terlebih dahulu.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validasi : Jika jenis kelamin belum dipilih
            val selectedGenderId = rgGender.checkedRadioButtonId
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin terlebih dahulu.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

            // Tampilan halaman hasil jika pilihan hobi di tekan
            val hobbies = mutableListOf<String>()
            if (cbDance.isChecked) hobbies.add("Menari")
            if (cbSing.isChecked) hobbies.add("Menyanyi")
            if (cbRead.isChecked) hobbies.add("Membaca")
            if (cbCook.isChecked) hobbies.add("Memasak")

                // Tampilan halaman hasil jika tidak menekan pilihan hobi
            val hobiText = if (hobbies.isEmpty()) "Tidak ada hobi" else hobbies.joinToString(", ")

            // Kirim ke FormResultActivity
            val toResult = Intent(this, FormResultActivity::class.java)
            toResult.putExtra("Nama", nama)
            toResult.putExtra("Alamat", alamat)
            toResult.putExtra("Nomor HP", nomorhp)
            toResult.putExtra("Agama", agama)
            toResult.putExtra("Gender", gender)
            toResult.putExtra("Hobi", hobiText)
            startActivity(toResult)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun init() {
        etName = findViewById(R.id.etName)
        etAddress = findViewById(R.id.etAddress)
        etNumber = findViewById(R.id.etNumber)
        spReligion = findViewById(R.id.spReligion)
        rgGender = findViewById(R.id.rgGender)
        rbMan = findViewById(R.id.rbMan)
        rbWoman = findViewById(R.id.rbWoman)
        cbDance = findViewById(R.id.cbDance)
        cbSing = findViewById(R.id.cbSing)
        cbRead = findViewById(R.id.cbRead)
        cbCook = findViewById(R.id.cbCook)
        btRegister = findViewById(R.id.btRegister)
    }
}
