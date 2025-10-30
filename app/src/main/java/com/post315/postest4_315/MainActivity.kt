package com.post315.postest4_315

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.post315.postest4_315.data.AppDatabase
import com.post315.postest4_315.data.Resident
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var adapterStatus: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etNIK = findViewById<EditText>(R.id.etNIK)
        val etKab = findViewById<EditText>(R.id.etKabupaten)
        val etKec = findViewById<EditText>(R.id.etKecamatan)
        val etDesa = findViewById<EditText>(R.id.etDesa)
        val etRT = findViewById<EditText>(R.id.etRT)
        val etRW = findViewById<EditText>(R.id.etRW)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val spStatus = findViewById<Spinner>(R.id.spStatus)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val tvResidents = findViewById<TextView>(R.id.tvResidents)

        val statusList = listOf("Belum Menikah", "Menikah", "Cerai")
        adapterStatus = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusList)
        spStatus.adapter = adapterStatus

        fun loadResidents() {
            lifecycleScope.launch {
                val data = db.residentDao().getAll()
                val text = buildString {
                    data.forEachIndexed { index, r ->
                        append("${index + 1}. ${r.name} (${r.gender}) - ${r.maritalStatus}\n")
                        append("NIK: ${r.nik}\n")
                        append("Alamat: RT ${r.rt}/RW ${r.rw}, ${r.desa}, ${r.kecamatan}, ${r.kabupaten}\n\n")
                    }
                }
                tvResidents.text = text
            }
        }

        btnSimpan.setOnClickListener {
            val nik = etNIK.text.toString()
            if (nik.length != 16 || !nik.all { it.isDigit() }) {
                Toast.makeText(this, "NIK harus 16 digit angka!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedGenderId = rgGender.checkedRadioButtonId
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gender = findViewById<RadioButton>(selectedGenderId).text.toString()
            val status = spStatus.selectedItem.toString()

            val resident = Resident(
                nik = nik,
                name = etNama.text.toString(),
                gender = gender,
                maritalStatus = status,
                rt = etRT.text.toString(),
                rw = etRW.text.toString(),
                desa = etDesa.text.toString(),
                kecamatan = etKec.text.toString(),
                kabupaten = etKab.text.toString()
            )

            lifecycleScope.launch {
                db.residentDao().insert(resident)
                Toast.makeText(this@MainActivity, "Data disimpan!", Toast.LENGTH_SHORT).show()
                loadResidents()
            }
        }

        btnReset.setOnClickListener {
            lifecycleScope.launch {
                db.residentDao().deleteAll()
                tvResidents.text = ""
                Toast.makeText(this@MainActivity, "Data dihapus!", Toast.LENGTH_SHORT).show()
            }
        }

        loadResidents()
    }
}
