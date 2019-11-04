package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.vluver.cbj.colegio.Adaptador.HorarioTemporalDocenteAdaptador;
import com.vluver.cbj.colegio.Adaptador.PerfilHorarioDocenteAdaptador;
import com.vluver.cbj.colegio.Docente.Adaptador.HorarioDocenteAdaptador;
import com.vluver.cbj.colegio.model.HorarioTemporalDocenteModel;
import com.vluver.cbj.colegio.model.ScheduleModel;
import com.vluver.cbj.colegio.model.SearchUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PerfilDocente extends AppCompatActivity implements View.OnClickListener{
    Intent dataa;
    ProgressDialog progressDialog;
    Button lu,ma,mi,ju,vi;
    TextView setDay;
    public static RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    HorarioTemporalDocenteAdaptador adapter;
    private int dayOfWeek;
    DataUser dataUser;
    TextView horariode;
    String nombres = null;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_docente);
        dataa = this.getIntent();
        progressDialog = new ProgressDialog(this);
        setUserData();

    }
    private void getSchedule(){
        horariode = findViewById(R.id.horariode);
        lu = findViewById(R.id.btnlunes);
        ma = findViewById(R.id.btnmartes);
        mi = findViewById(R.id.btnmiercoles);
        ju = findViewById(R.id.btnjueves);
        vi = findViewById(R.id.btnviernes);
        setDay =findViewById(R.id.daytxt);
        dataUser = new DataUser(PerfilDocente.this);
        if (dataUser.getTipodeusuario().equals("1")){
            horariode.setText("Horario de: " + nombres);
        }else{
            horariode.setText("Horario de: " + nombres);
        }
        recyclerView = findViewById(R.id.rvHorario);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(PerfilDocente.this);
        recyclerView.setLayoutManager(mLayoutManager);
        lu.setOnClickListener(this);
        ma.setOnClickListener(this);
        mi.setOnClickListener(this);
        ju.setOnClickListener(this);
        vi.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == dayOfWeek) {
            setCurretDay(0);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            setCurretDay(1);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            setCurretDay(2);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            setCurretDay(3);
        } else if (Calendar.FRIDAY == dayOfWeek) {
            setCurretDay(4);
        } else if (Calendar.SATURDAY == dayOfWeek) {
            setCurretDay(0);
        } else if (Calendar.SUNDAY == dayOfWeek) {
            setCurretDay(0);
        }
    }
    private void setUserData(){

        String cedula = null;
        int statefollow = 0;
        if (dataa != null) {
            cedula = dataa.getStringExtra("userCedula");
            nombres = dataa.getStringExtra("userName");
        }
        //getSchedule();
        getScheduleTeacher(cedula);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlunes:
                setCurretDay(0);
                break;
            case R.id.btnmartes:
                setCurretDay(1);
                break;
            case R.id.btnmiercoles:
                setCurretDay(2);
                break;
            case R.id.btnjueves:
                setCurretDay(3);
                break;
            case R.id.btnviernes:
                setCurretDay(4);
                break;
        }
    }
    private void setCurretDay(int stateint){
        switch (stateint){
            case 0:
                lu.setSelected(true);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.blanco));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                setSchedule("LUNES");
                break;
            case 1:
                lu.setSelected(false);
                ma.setSelected(true);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.blanco));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                setSchedule("MARTES");
                break;
            case 2:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(true);
                ju.setSelected(false);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.blanco));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.negro));
                setSchedule("MIERCOLES");
                break;
            case 3:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(true);
                vi.setSelected(false);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.blanco));
                vi.setTextColor(getResources().getColor(R.color.negro));
                setSchedule("JUEVES");
                break;
            case 4:
                lu.setSelected(false);
                ma.setSelected(false);
                mi.setSelected(false);
                ju.setSelected(false);
                vi.setSelected(true);
                lu.setTextColor(getResources().getColor(R.color.negro));
                ma.setTextColor(getResources().getColor(R.color.negro));
                mi.setTextColor(getResources().getColor(R.color.negro));
                ju.setTextColor(getResources().getColor(R.color.negro));
                vi.setTextColor(getResources().getColor(R.color.blanco));
                setSchedule("VIERNES");
                break;
        }
    }

    void setSchedule(String dia){
        String diadehoy;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                diadehoy = "LUNES";
                break;
            case Calendar.TUESDAY:
                diadehoy = "MARTES";
                break;
            case Calendar.WEDNESDAY:
                diadehoy = "MIERCOLES";
                break;
            case Calendar.THURSDAY:
                diadehoy = "JUEVES";
                break;
            case Calendar.FRIDAY:
                diadehoy = "VIERNES";
                break;
            default:
                diadehoy = "NOTHING";
                break;
        }
        setDay.setText(dia);
        databaseHandler = new DatabaseHandler(PerfilDocente.this);
        adapter = new HorarioTemporalDocenteAdaptador(databaseHandler.getHorarioTemporalDocentePorDia(dia), PerfilDocente.this,diadehoy);
        recyclerView.setAdapter(adapter);
    }
    private void getScheduleTeacher(String cedula){

        progressDialog.setMessage("Cargando");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/get/get_schedule_teacher.php?number_cedula="+cedula;
        VolleySingleton.getInstance(PerfilDocente.this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.GET, url, (JSONObject) null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            if (!error){
                                DatabaseHandler db = new DatabaseHandler (PerfilDocente.this);
                                JSONObject h_JSON = response.getJSONObject("horario");
                                JSONArray horarios = h_JSON.getJSONArray("dia");
                                for (int i = 0; i < horarios.length(); i++){
                                    JSONArray arrayCursos = h_JSON.getJSONArray("curso");
                                    JSONArray arrayDia = h_JSON.getJSONArray("dia");
                                    JSONArray arrayHoraIni = h_JSON.getJSONArray("hora_ini");
                                    JSONArray arrayHoraFin = h_JSON.getJSONArray("hora_fin");
                                    JSONArray arrayMateria = h_JSON.getJSONArray("materia");
                                    db.insertar_horario_temporal_docente(arrayCursos.getString(i),arrayDia.getString(i),
                                            arrayHoraIni.getString(i).substring(0,5),arrayHoraFin.getString(i).substring(0,5),
                                            arrayMateria.getString(i));

                                }
                                getSchedule();
                                //estadoSesion.setLoginTeacher(true);
                                progressDialog.dismiss();

                               }else{
                                progressDialog.dismiss();
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(PerfilDocente.this, errorMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(PerfilDocente.this, ""+e, Toast.LENGTH_SHORT).show();
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
        ));
    }
    private void checkerror(VolleyError error){
        if( error instanceof NetworkError) {
            Toast.makeText(PerfilDocente.this, "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(PerfilDocente.this, "Servidores fallando", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(PerfilDocente.this, "Error 404", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(PerfilDocente.this, "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(PerfilDocente.this, "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        databaseHandler.borrarHorarioTemporalDocente();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHandler.borrarHorarioTemporalDocente();
    }
}
