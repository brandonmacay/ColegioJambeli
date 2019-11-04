package com.vluver.cbj.colegio.model;

public class HorarioTemporalDocenteModel {
    String curso;
    String dia;
    String horaInicial;
    String horaFinal;
    String materia;
    public HorarioTemporalDocenteModel(){

    }

    public HorarioTemporalDocenteModel(String curso, String dia, String horaInicial, String horaFinal, String materia){
        this.curso = curso;
        this.dia = dia;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.materia = materia;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String docente) {
        this.curso = docente;
    }
    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public String getHoraInicial() {
        return horaInicial;
    }
    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }
    public String getHoraFinal() {
        return horaFinal;
    }
    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }
}
