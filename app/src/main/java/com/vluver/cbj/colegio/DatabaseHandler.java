package com.vluver.cbj.colegio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.vluver.cbj.colegio.Docente.Modelo.HorarioDocenteModel;
import com.vluver.cbj.colegio.Estudiante.Modelo.HorarioEstudianteModel;

import java.util.LinkedList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mr_search_cbj";
    private static final int DATABASE_VERSION = 1;

    //Tabla para los horarios del estudiante
    public static final String TABLE_NAME_ESTUDIANTE = "horario_estudiante";

    public static final String COLUMN_ID_ESTUDIANTE = "_id";

    public static final String COLUMN_DOCENTE_ESTUDIANTE = "docente";
    public static final String COLUMN_DIA_ESTUDIANTE = "dia";
    public static final String COLUMN_HORA_INI_ESTUDIANTE = "hora_inicial";
    public static final String COLUMN_HORA_FIN_ESTUDIANTE = "hora_final";
    public static final String COLUMN_MATERIA_ESTUDIANTE = "materia";


   //Tabla para los horarios del docente
    public static final String TABLE_NAME_DOCENTE = "horario_docente";

    public static final String COLUMN_ID_DOCENTE = "_id";

    public static final String COLUMN_CURSOS = "cursos";
    public static final String COLUMN_DIA_DOCENTE = "dia";
    public static final String COLUMN_HORA_INI_DOCENTE = "hora_inicial";
    public static final String COLUMN_HORA_FIN_DOCENTE = "hora_final";
    public static final String COLUMN_MATERIA_DOCENTE = "materia";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DOCENTE + " (" +
                COLUMN_ID_DOCENTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CURSOS + " TEXT  NOT NULL, " +
                COLUMN_DIA_DOCENTE + " TEXT  NOT NULL, " +
                COLUMN_HORA_INI_DOCENTE + " TEXT  NOT NULL, " +
                COLUMN_HORA_FIN_DOCENTE + " TEXT  NOT NULL, " +
                COLUMN_MATERIA_DOCENTE + " TEXT NOT NULL); "
        );

        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ESTUDIANTE + " (" +
                COLUMN_ID_ESTUDIANTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DOCENTE_ESTUDIANTE + " TEXT  NOT NULL, " +
                COLUMN_DIA_ESTUDIANTE + " TEXT  NOT NULL, " +
                COLUMN_HORA_INI_ESTUDIANTE + " TEXT  NOT NULL, " +
                COLUMN_HORA_FIN_ESTUDIANTE + " TEXT  NOT NULL, " +
                COLUMN_MATERIA_ESTUDIANTE + " TEXT NOT NULL); "
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        switch (oldVersion){
            case 1:
               // db.execSQL("ALTER TABLE "+ TABLE_CLOCKING_NAME+" ADD COLUMN "+ COLUMN_CLOCKING_FORMATDATE +" TEXT ");
                break;
        }
        this.onCreate(db);
    }


    public void borrarHorarioEstudiante( Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_ESTUDIANTE);
        Toast.makeText(context,"Sesion cerrada!", Toast.LENGTH_SHORT).show();
    }

    public void borrarHorarioDocente( Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_DOCENTE);
        Toast.makeText(context,"Sesion cerrada!", Toast.LENGTH_SHORT).show();
    }


    //insertar horario_estudiante
    public void insertar_horario_estudiante( String docente, String dia,String hora_ini,String horafin,String materia) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DOCENTE_ESTUDIANTE, docente);
        values.put(COLUMN_DIA_ESTUDIANTE, dia);
        values.put(COLUMN_HORA_INI_ESTUDIANTE,hora_ini);
        values.put(COLUMN_HORA_FIN_ESTUDIANTE, horafin);
        values.put(COLUMN_MATERIA_ESTUDIANTE, materia);
        // insert
        db.insert(TABLE_NAME_ESTUDIANTE,null, values);
        db.close();
    }

    //insertar horario_docentes
    public void insertar_horario_docente( String cursos, String dia,String hora_ini,String horafin,String materia) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CURSOS, cursos);
        values.put(COLUMN_DIA_DOCENTE, dia);
        values.put(COLUMN_HORA_INI_DOCENTE,hora_ini);
        values.put(COLUMN_HORA_FIN_DOCENTE, horafin);
        values.put(COLUMN_MATERIA_DOCENTE, materia);
        // insert
        db.insert(TABLE_NAME_DOCENTE,null, values);
        db.close();
    }

    public List<HorarioEstudianteModel> getHorarioEstudiantePorDia(String dia_) {
        String query ;
        query = "SELECT  * FROM " + TABLE_NAME_ESTUDIANTE + " WHERE dia='"+dia_+"'";
        List<HorarioEstudianteModel> listadoHorario = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        HorarioEstudianteModel horarioEstudianteModel;
        if (cursor.moveToFirst()) {
            do {
                horarioEstudianteModel = new HorarioEstudianteModel();
                horarioEstudianteModel.setDocente(cursor.getString(cursor.getColumnIndex(COLUMN_DOCENTE_ESTUDIANTE)));
                horarioEstudianteModel.setDia(cursor.getString(cursor.getColumnIndex(COLUMN_DIA_ESTUDIANTE)));
                horarioEstudianteModel.setHoraInicial(cursor.getString(cursor.getColumnIndex(COLUMN_HORA_INI_ESTUDIANTE)));
                horarioEstudianteModel.setHoraFinal(cursor.getString(cursor.getColumnIndex(COLUMN_HORA_FIN_ESTUDIANTE)));
                horarioEstudianteModel.setMateria(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIA_ESTUDIANTE)));
                listadoHorario.add(horarioEstudianteModel);
            } while (cursor.moveToNext());

            db.close();
            cursor.close();
        }
        return listadoHorario;
    }

    public List<HorarioDocenteModel> getHorarioDocentePorDia(String dia_) {
        String query ;
        query = "SELECT  * FROM " + TABLE_NAME_DOCENTE + " WHERE dia='"+dia_+"'";
        List<HorarioDocenteModel> listadoHorario = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        HorarioDocenteModel horarioEstudianteModel;
        if (cursor.moveToFirst()) {
            do {
                horarioEstudianteModel = new HorarioDocenteModel();
                horarioEstudianteModel.setCurso(cursor.getString(cursor.getColumnIndex(COLUMN_CURSOS)));
                horarioEstudianteModel.setDia(cursor.getString(cursor.getColumnIndex(COLUMN_DIA_DOCENTE)));
                horarioEstudianteModel.setHoraInicial(cursor.getString(cursor.getColumnIndex(COLUMN_HORA_INI_DOCENTE)));
                horarioEstudianteModel.setHoraFinal(cursor.getString(cursor.getColumnIndex(COLUMN_HORA_FIN_DOCENTE)));
                horarioEstudianteModel.setMateria(cursor.getString(cursor.getColumnIndex(COLUMN_MATERIA_DOCENTE)));
                listadoHorario.add(horarioEstudianteModel);
            } while (cursor.moveToNext());

            db.close();
            cursor.close();
        }
        return listadoHorario;
    }


    public Cursor get_horario_estudiante(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME_ESTUDIANTE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;


    }

}