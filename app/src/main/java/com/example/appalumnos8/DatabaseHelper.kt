package com.example.appalumnos8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Escuela.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_ALUMNOS = "Alumnos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CUENTA = "cuenta"
        private const val COLUMN_IMAGEN = "imagen"
        private const val COLUMN_CORREO = "correo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_ALUMNOS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NOMBRE TEXT, "
                + "$COLUMN_CUENTA TEXT, "
                + "$COLUMN_IMAGEN TEXT, "
                + "$COLUMN_CORREO TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        onCreate(db)
    }

    fun insertarAlumno(alumno: Alumno): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, alumno.nombre)
            put(COLUMN_CUENTA, alumno.cuenta)
            put(COLUMN_IMAGEN, alumno.imagen)
            put(COLUMN_CORREO, alumno.correo)
        }
        val id = db.insert(TABLE_ALUMNOS, null, values)
        db.close()
        return id
    }

    fun obtenerAlumnos(): List<Alumno> {
        val listaAlumnos = mutableListOf<Alumno>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_ALUMNOS ORDER BY $COLUMN_ID DESC"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(COLUMN_NOMBRE)
            val cuentaIndex = cursor.getColumnIndex(COLUMN_CUENTA)
            val imagenIndex = cursor.getColumnIndex(COLUMN_IMAGEN)
            val correoIndex = cursor.getColumnIndex(COLUMN_CORREO)

            do {
                val id = if (idIndex != -1) cursor.getLong(idIndex) else 0L
                val nombre = if (nombreIndex != -1) cursor.getString(nombreIndex) ?: "" else ""
                val cuenta = if (cuentaIndex != -1) cursor.getString(cuentaIndex) ?: "" else ""
                val imagen = if (imagenIndex != -1) cursor.getString(imagenIndex) ?: "" else ""
                val correo = if (correoIndex != -1) cursor.getString(correoIndex) ?: "" else ""

                val alumno = Alumno(id, nombre, cuenta, imagen, correo)
                listaAlumnos.add(alumno)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaAlumnos
    }
}
