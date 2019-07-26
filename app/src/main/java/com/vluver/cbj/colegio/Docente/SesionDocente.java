package com.vluver.cbj.colegio.Docente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.vluver.cbj.colegio.R;

public class SesionDocente extends AppCompatActivity {

    Toolbar toolbar;
    EditText cedula,clave;
    Button iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        toolbar= findViewById(R.id.toolbar);
        cedula = (EditText) findViewById(R.id.cedula);
        clave = (EditText) findViewById(R.id.clave);
        iniciar = findViewById(R.id.btn_iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cedula.getText().toString().isEmpty()){
                    cedula.setError("Ingrese la cedula");
                }else if (clave.getText().toString().isEmpty()){
                    clave.setError("Ingrese la clave");
                }else{
                    startActivity(new Intent(SesionDocente.this,DocenteActivity.class));
                }
            }
        });
        toolbar.setTitle("Docente");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
