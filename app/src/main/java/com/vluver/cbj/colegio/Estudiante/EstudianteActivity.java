package com.vluver.cbj.colegio.Estudiante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.vluver.cbj.colegio.R;

import java.util.ArrayList;
import java.util.List;

public class EstudianteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    Button listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Estudiante");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(EstudianteActivity.this);
        ArrayList categories=new ArrayList();
        categories.add("1");
        categories.add("2");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        categories.add("1");
        ArrayAdapter dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);

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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
