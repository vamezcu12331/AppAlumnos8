package com.example.appalumnos8

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appalumnos8.databinding.ItemAlumnoBinding

class AlumnosAdapter(private var listaAlumnos: List<Alumno>) :
    RecyclerView.Adapter<AlumnosAdapter.AlumnoViewHolder>() {

    inner class AlumnoViewHolder(val binding: ItemAlumnoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val binding = ItemAlumnoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlumnoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        with(holder.binding) {
            tvNombre.text = alumno.nombre
            tvCuenta.text = "Cuenta: ${alumno.cuenta}"
            tvCorreo.text = alumno.correo
            
            // Si la imagen no está vacía, podrías cargarla con Coil/Glide.
            // Para SQLite básico, si es un drawable resource o uri se puede manejar o mostrar por defecto.
            if (alumno.imagen.isNotBlank()) {
                try {
                    ivAlumno.setImageURI(android.net.Uri.parse(alumno.imagen))
                } catch (e: Exception) {
                    ivAlumno.setImageResource(R.mipmap.ic_launcher)
                }
            } else {
                ivAlumno.setImageResource(R.mipmap.ic_launcher)
            }
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size

    fun actualizarLista(nuevaLista: List<Alumno>) {
        listaAlumnos = nuevaLista
        notifyDataSetChanged()
    }
}
