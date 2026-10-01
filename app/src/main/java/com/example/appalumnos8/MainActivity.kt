package com.example.appalumnos8

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appalumnos8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var alumnosAdapter: AlumnosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupRecyclerView()

        binding.fabAgregar.setOnClickListener {
            val intent = Intent(this, AgregarAlumnoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val listaAlumnos = dbHelper.obtenerAlumnos()
        alumnosAdapter = AlumnosAdapter(listaAlumnos)
        binding.rvAlumnos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = alumnosAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        // Refrescar la lista cada vez que la actividad vuelve a primer plano (por ejemplo, al regresar de AgregarAlumnoActivity)
        val listaAlumnos = dbHelper.obtenerAlumnos()
        if (::alumnosAdapter.isInitialized) {
            alumnosAdapter.actualizarLista(listaAlumnos)
        }
    }
}
