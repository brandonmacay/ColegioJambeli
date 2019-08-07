package com.vluver.cbj.colegio.Estudiante.Modelo;

public class DocentesPorCursoModel {
    String materia;
    String nombredocente;

    public DocentesPorCursoModel(){
    }

    DocentesPorCursoModel(String materia,String nombredocente){
        this.materia = materia;
        this.nombredocente = nombredocente;

    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setNombredocente(String nombredocente) {
        this.nombredocente = nombredocente;
    }

    public String getMateria() {
        return materia;
    }

    public String getNombredocente() {
        return nombredocente;
    }
}
