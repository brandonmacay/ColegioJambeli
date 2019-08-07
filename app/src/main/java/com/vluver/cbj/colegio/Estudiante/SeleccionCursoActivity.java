package com.vluver.cbj.colegio.Estudiante;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SeleccionCursoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;
    Button listo;
    int curso_seleccionadoint;
    String curso_seleccionado;
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
        categories.add(0,"1 “A” INFORMÁTICA");
        categories.add(1,"1 “A” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(2,"1 “A” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(3,"1 “B” INFORMÁTICA");
        categories.add(4,"1 “B” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(5,"1 “B” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(6,"1 “C” INFORMÁTICA");
        categories.add(7,"1 “C” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(8,"1 “D” INFORMÁTICA");
        categories.add(9,"2 “A” INFORMÁTICA");
        categories.add(10,"2 “A” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(11,"2 “A” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(12,"2 “B” INFORMÁTICA");
        categories.add(13,"2 “B” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(14,"2 “B” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(15,"2 “C” INFORMÁTICA");
        categories.add(16,"2 “C” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(17,"2 “D” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(18,"3 “A” APLICACIONES INFORMÁTICAS");
        categories.add(19,"3 “A” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(20,"3 “A” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(21,"3 “B” APLICACIONES INFORMÁTICAS");
        categories.add(22,"3 “B” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");
        categories.add(23,"3 “B” PECES, MOLUSCOS Y CRUSTÁCEOS");
        categories.add(24,"3 “C” INSTALACIONES, EQUIPOS Y MÁQUINAS ELÉCTRICAS");

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
        curso_seleccionadoint = i;
        curso_seleccionado = adapterView.getItemAtPosition(i).toString();

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
        String url = "http://jambeli.hostingerapp.com/apirestAndroid/get_schedule.php?course_id="+curso_seleccionadoint;
        VolleySingleton.
                getInstance(SeleccionCursoActivity.this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET, url, (JSONObject) null,
                                new Response.Listener<JSONObject>() {
                                    @SuppressLint("DefaultLocale")
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        try {
                                            boolean error = response.getBoolean("error");
                                            if (!error){
                                                DatabaseHandler db = new DatabaseHandler (SeleccionCursoActivity.this);
                                                JSONObject h_JSON = response.getJSONObject("horario");
                                                JSONArray horarios = h_JSON.getJSONArray("dia");
                                                for (int i = 0; i < horarios.length(); i++){
                                                    JSONArray arrayDocente = h_JSON.getJSONArray("docente");
                                                    JSONArray arrayDia = h_JSON.getJSONArray("dia");

                                                    //Toast.makeText(getContext(), ""+hours+":"+minutes, Toast.LENGTH_SHORT).show();
                                                    JSONArray arrayHoraIni = h_JSON.getJSONArray("hora_ini");
                                                    JSONArray arrayHoraFin = h_JSON.getJSONArray("hora_fin");
                                                    JSONArray arrayMateria = h_JSON.getJSONArray("materia");
                                                    db.insertar_horario_estudiante(arrayDocente.getString(i),arrayDia.getString(i),
                                                            arrayHoraIni.getString(i).substring(0,5),arrayHoraFin.getString(i).substring(0,5),
                                                            arrayMateria.getString(i));

                                                }

                                                JSONObject DPC_JSON = response.getJSONObject("docentes_del_curso");
                                                JSONArray DPC_horarios = DPC_JSON.getJSONArray("docente");
                                                for (int i = 0; i < DPC_horarios.length(); i++){
                                                    JSONArray arrayDocenteDPC = DPC_JSON.getJSONArray("docente");
                                                    JSONArray arrayMateriaDPC = DPC_JSON.getJSONArray("materias_del_docente");
                                                    db.insertar_docente_por_curso(arrayDocenteDPC.getString(i),arrayMateriaDPC.getString(i));
                                                }
                                                estadoSesion.setLoginStudent(true);
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(SeleccionCursoActivity.this, EstudianteActivity.class);
                                                estadoSesion.setCourseStudent(curso_seleccionado);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }else{
                                                progressDialog.dismiss();
                                                String errorMsg = response.getString("error_msg");
                                                Toast.makeText(SeleccionCursoActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(SeleccionCursoActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
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
