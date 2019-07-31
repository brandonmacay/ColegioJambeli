package com.vluver.cbj.colegio;

import android.content.Context;
import android.content.SharedPreferences;

public class EstadoSesion {
    private static final String TAG = EstadoSesion.class.getSimpleName();

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    Context _context;

    // modo shared preference
    int PRIVATE_MODE = 0;

    // nombre del archivo shared
    private static final String PREF_NAME = "userlogin";

    private static final String KEY_IS_LOGGED_IN_Student = "isLoggedInStudent";
    private static final String KEY_IS_LOGGED_IN_TEACHER = "isLoggedInTeacher";
    private static final String COURSE = "";
    //declaracion de uso de sesion
    public EstadoSesion(Context context){
        this._context = context;
        sPref  = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sPref.edit();
    }

    //como marcador: se utiliza cuando el usuario ha iniciado sesión
    public void setLoginStudent(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN_Student, isLoggedIn);
        editor.commit();
    }

    public void setCourseStudent(String courseStudent){
        editor.putString(COURSE, courseStudent);
        editor.commit();
    }

    public void setLoginTeacher(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN_TEACHER, isLoggedIn);
        editor.commit();
    }

    public String getCourse(){
        return sPref.getString(COURSE,COURSE);
    }

    boolean isLoggedInStudent(){
        return sPref.getBoolean(KEY_IS_LOGGED_IN_Student, false);
    }
    boolean isLoggedInTeacher(){
        return sPref.getBoolean(KEY_IS_LOGGED_IN_TEACHER, false);
    }
}
