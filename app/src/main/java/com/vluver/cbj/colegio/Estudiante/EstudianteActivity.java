package com.vluver.cbj.colegio.Estudiante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.vluver.cbj.colegio.R;

import java.util.Objects;

public class EstudianteActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Estudiante");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listo = findViewById(R.id.btn_listo);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EstudianteActivity.this,MainEstudianteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


}
