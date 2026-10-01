package com.example.appalumnos8

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalumnos8.databinding.ActivityAgregarAlumnoBinding

class AgregarAlumnoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarAlumnoBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnGuardar.setOnClickListener {
            guardarAlumno()
        }
    }

    private fun guardarAlumno() {
        val nombre = binding.etNombre.text.toString().trim()
        val cuenta = binding.etCuenta.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val imagen = binding.etImagen.text.toString().trim()

        if (nombre.isEmpty() || cuenta.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val alumno = Alumno(
            nombre = nombre,
            cuenta = cuenta,
            imagen = imagen,
            correo = correo
        )

        val id = dbHelper.insertarAlumno(alumno)
        if (id != -1L) {
            Toast.makeText(this, "Alumno guardado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al guardar el alumno", Toast.LENGTH_SHORT).show()
        }
    }
}
