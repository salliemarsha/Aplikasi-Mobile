package com.sallie.asesmengasal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FormResultActivity : AppCompatActivity() {

    lateinit var tvNameFR: TextView
    lateinit var tvAddressFR: TextView
    lateinit var tvNumberFR: TextView
    lateinit var tvReligionFR: TextView
    lateinit var tvGenderFR: TextView
    lateinit var tvHobbiesFR: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_result)

        // Jika hanya menampilkan hasil, tidak perlu membuat fun init baru
        tvNameFR = findViewById(R.id.tvNameFR)
        tvAddressFR = findViewById(R.id.tvAddressFR)
        tvNumberFR = findViewById(R.id.tvNumberFR)
        tvReligionFR = findViewById(R.id.tvReligionFR)
        tvGenderFR = findViewById(R.id.tvGenderFR)
        tvHobbiesFR = findViewById(R.id.tvHobbiesFR)

        // Langkah 1 : Mengambil data dari intent di FormActivity
        val nama = intent.getStringExtra("Nama")
        val alamat = intent.getStringExtra("Alamat")
        val nomorhp = intent.getStringExtra("Nomor HP")
        val agama = intent.getStringExtra("Agama")
        val gender = intent.getStringExtra("Gender")
        val hobi = intent.getStringExtra("Hobi")

        // Langkah 2 : Menampilkan data
        tvNameFR.text = "Nama: $nama"
        tvAddressFR.text = "Alamat: $alamat"
        tvNumberFR.text = "Nomor HP: $nomorhp"
        tvReligionFR.text = "Agama: $agama"
        tvGenderFR.text = "Jenis Kelamin: $gender"
        tvHobbiesFR.text = "Hobi: $hobi"
    }
}
