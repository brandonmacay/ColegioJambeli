package com.vluver.cbj.colegio.Estudiante;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vluver.cbj.colegio.DatabaseHandler;
import com.vluver.cbj.colegio.EstadoSesion;
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeleccionCursoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    Button listo;
    int curso_seleccionado;
    ProgressDialog progressDialog;
    EstadoSesion estadoSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        progressDialog = new ProgressDialog(this);
        estadoSesion = new EstadoSesion(this);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Estudiante");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(SeleccionCursoActivity.this);
        ArrayList<String> categories=new ArrayList<String>();
        categories.add(0,"1 A ACUACULTURA");
        categories.add(1,"1 A ACUACULTURA");
        categories.add(2,"1 B ACUACULTURA");
        categories.add(3,"1 A INFORMATICA");
        categories.add(4,"1 B INFORMATICA");
        categories.add(5,"1 C INFORMATICA");
        categories.add(6,"1 D INFORMATICA");
        categories.add(7,"1 A ELECTRICIDAD");
        categories.add(8,"1 B ELECTRICIDAD");
        categories.add(9,"1 C ELECTRICIDAD");
        categories.add(10,"2 A ACUACULTURA");
        categories.add(11,"2 B ACUACULTURA");
        categories.add(12,"2 A INFORMATICA");
        categories.add(13,"2 B INFORMATICA");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);

        listo = findViewById(R.id.btn_listo);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getschedule();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        curso_seleccionado = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getschedule(){
        progressDialog.setTitle("Horario Estudiante");
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "http://jambeli.hostingerapp.com/apirestAndroid/get_schedule.php?course_id="+curso_seleccionado;
        VolleySingleton.
                getInstance(SeleccionCursoActivity.this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET, url, (JSONObject) null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        try {
                                            boolean error = response.getBoolean("error");
                                            if (!error){
                                                DatabaseHandler db = new DatabaseHandler (SeleccionCursoActivity.this);
                                                JSONObject h_JSON = response.getJSONObject("horario");
                                                JSONArray horarios = h_JSON.getJSONArray("docente");
                                                for (int i = 0; i < horarios.length(); i++){
                                                    JSONArray arrayDocente = h_JSON.getJSONArray("docente");
                                                    JSONArray arrayGrado = h_JSON.getJSONArray("grado");
                                                    JSONArray arrayDia = h_JSON.getJSONArray("dia");
                                                    JSONArray arrayHoraIni = h_JSON.getJSONArray("hora_ini");
                                                    JSONArray arrayHoraFin = h_JSON.getJSONArray("hora_fin");
                                                    JSONArray arrayMateria = h_JSON.getJSONArray("materia");

                                                    db.insertar_horario(arrayGrado.getString(i),
                                                            arrayDocente.getString(i),arrayDia.getString(i),
                                                            arrayHoraIni.getString(i),arrayHoraFin.getString(i),arrayMateria.getString(i));
                                                }
                                                estadoSesion.setLoginStudent(true);
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(SeleccionCursoActivity.this, EstudianteActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("curso_estudiante",curso_seleccionado);
                                                startActivity(intent);
                                            }else{
                                                String errorMsg = response.getString("error_msg");
                                                Toast.makeText(SeleccionCursoActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(SeleccionCursoActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        checkerror(error);

                                    }
                                }
                        )
                );
    }

    private void checkerror(VolleyError error){
        if( error instanceof NetworkError) {
            Toast.makeText(SeleccionCursoActivity.this, "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(SeleccionCursoActivity.this, "Servidores fallando", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(SeleccionCursoActivity.this, "Error 404", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(SeleccionCursoActivity.this, "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(SeleccionCursoActivity.this, "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }



}
