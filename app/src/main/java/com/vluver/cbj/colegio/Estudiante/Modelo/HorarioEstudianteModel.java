package com.vluver.cbj.colegio.Estudiante.Modelo;

public class HorarioEstudianteModel {
    String docente;
    String dia;
    String horaInicial;
    String horaFinal;
    String materia;
    public HorarioEstudianteModel(){

    }
    public HorarioEstudianteModel(String docente,String dia,String horaInicial,String horaFinal,String materia){
        this.docente = docente;
        this.dia = dia;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.materia = materia;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
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
