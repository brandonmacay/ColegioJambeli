package com.vluver.cbj.colegio;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUser {
    private static final String TAG = DataUser.class.getSimpleName();
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    Context _context;

    // modo shared preference
    int PRIVATE_MODE = 0;

    // nombre del archivo shared
    private static final String PREF_NAME = "userlogin";
    private static final String NOMBRES = "nombres";
    private static final String CORREO = "correo";
    private static final String TIPODEUSUARIO = "tipo";
    private static final String CEDULA = "cedula";
    private static final String CURSO = "curso";
    private static final String CURSOID = "cursoid";
    private static final String GENERO = "genero";
    private static final String FECHANACIMIENTO = "nacimiento";
    private static final String CIUDAD = "ciudad";
    private static final String NOMBREUSUARIO = "usuario";

    //declaracion de uso de sesion
    public DataUser(Context context){
        this._context = context;
        sPref  = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sPref.edit();
    }


    //METODO SET SHAREDPREFERENCES

    void setNombres(String nombres){
        editor.putString(NOMBRES, nombres);
        editor.commit();
    }

    void setCorreo(String correo){
        editor.putString(CORREO, correo);
        editor.commit();
    }
    void setTipodeusuario(String tipodeusuario){
        editor.putString(TIPODEUSUARIO, tipodeusuario);
        editor.commit();
    }
    void setCedula(String cedula){
        editor.putString(CEDULA, cedula);
        editor.commit();
    }
    public void setCurso(String curso){
        editor.putString(CURSO, curso);
        editor.commit();
    }
    public void setCursoid(String cursoid){
        editor.putString(CURSOID, cursoid);
        editor.commit();
    }

    void setGenero(String genero){
        editor.putString(GENERO, genero);
        editor.commit();
    }
    void setFechanacimiento(String fechanacimiento){
        editor.putString(FECHANACIMIENTO, fechanacimiento);
        editor.commit();
    }
    void setCiudad(String ciudad){
        editor.putString(CIUDAD, ciudad);
        editor.commit();
    }

    void setNombreusuario(String nombreusuario){
        editor.putString(NOMBREUSUARIO, nombreusuario);
        editor.commit();
    }



    //METODO GET SHAREDPREFERENCES
    public String getNombres(){
        return sPref.getString(NOMBRES,NOMBRES);
    }
    public String getCorreo(){
        return sPref.getString(CORREO,CORREO);
    }
    public String getTipodeusuario(){
        return sPref.getString(TIPODEUSUARIO,TIPODEUSUARIO);
    }
    public String getCedula(){
        return sPref.getString(CEDULA,CEDULA);
    }
    public String getCurso(){
        return sPref.getString(CURSO,CURSO);
    }

    public  String getCURSOID() {
        return sPref.getString(CURSOID,CURSOID);

    }
    public String getGenero(){
        return sPref.getString(GENERO,GENERO);
    }
    public String getFechanacimiento(){
        return sPref.getString(FECHANACIMIENTO,FECHANACIMIENTO);
    }
    public String getCiudad(){
        return sPref.getString(CIUDAD,CIUDAD);
    }
    public String getNombreusuario(){
        return sPref.getString(NOMBREUSUARIO,NOMBREUSUARIO);
    }

}
