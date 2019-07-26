package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vluver.cbj.colegio.Docente.SesionDocente;
import com.vluver.cbj.colegio.Estudiante.EstudianteActivity;
import com.vluver.cbj.colegio.Estudiante.SeleccionCursoActivity;

public class MainActivity extends AppCompatActivity {

    Button docente,estudiante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EstadoSesion estadoSesion = new EstadoSesion(this);
        if (estadoSesion.isLoggedInStudent()) {

            Intent intent = new Intent(MainActivity.this, EstudianteActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else if (estadoSesion.isLoggedInTeacher()){
            Intent intent = new Intent(MainActivity.this, SesionDocente.class);
            startActivity(intent);
            finish();
        }
        docente = findViewById(R.id.btn_docente);
        estudiante = findViewById(R.id.btn_estudiante);
        docente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SesionDocente.class));
            }
        });
        estudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SeleccionCursoActivity.class));
            }
        });
    }
}
