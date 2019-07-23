package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vluver.cbj.colegio.Docente.DocenteActivity;
import com.vluver.cbj.colegio.Estudiante.EstudianteActivity;

public class MainActivity extends AppCompatActivity {

    Button docente,estudiante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        docente = findViewById(R.id.btn_docente);
        estudiante = findViewById(R.id.btn_estudiante);
        docente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DocenteActivity.class));
            }
        });
        estudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EstudianteActivity.class));
            }
        });
    }
}
