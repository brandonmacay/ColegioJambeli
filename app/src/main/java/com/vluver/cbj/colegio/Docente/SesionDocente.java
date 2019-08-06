package com.vluver.cbj.colegio.Docente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.vluver.cbj.colegio.Estudiante.EstudianteActivity;
import com.vluver.cbj.colegio.Estudiante.SeleccionCursoActivity;
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SesionDocente extends AppCompatActivity {

    Toolbar toolbar;
    EditText cedula;
    Button iniciar;
    ProgressDialog progressDialog;
    EstadoSesion estadoSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        toolbar= findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(this);
        estadoSesion = new EstadoSesion(this);
        cedula = (EditText) findViewById(R.id.cedula);
        iniciar = findViewById(R.id.btn_iniciar);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cedula.getText().toString().isEmpty()){
                    cedula.setError("Ingrese la cedula");
                }else{
                    loginTeacher();
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
    private void loginTeacher(){
        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "http://jambeli.hostingerapp.com/apirestAndroid/loginTeacher.php?number_cedula="+cedula.getText().toString();
        VolleySingleton.
                getInstance(SesionDocente.this).
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
                                                DatabaseHandler db = new DatabaseHandler (SesionDocente.this);
                                                JSONObject h_JSON = response.getJSONObject("horario");
                                                JSONArray horarios = h_JSON.getJSONArray("dia");
                                                for (int i = 0; i < horarios.length(); i++){
                                                    JSONArray arrayCursos = h_JSON.getJSONArray("curso");
                                                    JSONArray arrayDia = h_JSON.getJSONArray("dia");
                                                    JSONArray arrayHoraIni = h_JSON.getJSONArray("hora_ini");
                                                    JSONArray arrayHoraFin = h_JSON.getJSONArray("hora_fin");
                                                    JSONArray arrayMateria = h_JSON.getJSONArray("materia");
                                                    db.insertar_horario_docente(arrayCursos.getString(i),arrayDia.getString(i),
                                                            arrayHoraIni.getString(i).substring(0,5),arrayHoraFin.getString(i).substring(0,5),
                                                            arrayMateria.getString(i));

                                                }
                                                estadoSesion.setLoginTeacher(true);
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(SesionDocente.this, DocenteActivity.class);
                                                estadoSesion.setCedulaTeacher(cedula.getText().toString());
                                                estadoSesion.setNameTeacher(response.getString("docente"));
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }else{
                                                progressDialog.dismiss();
                                                String errorMsg = response.getString("error_msg");
                                                Toast.makeText(SesionDocente.this, errorMsg, Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(SesionDocente.this, ""+e, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SesionDocente.this, "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(SesionDocente.this, "Servidores fallando", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(SesionDocente.this, "Error 404", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(SesionDocente.this, "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(SesionDocente.this, "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }
}
