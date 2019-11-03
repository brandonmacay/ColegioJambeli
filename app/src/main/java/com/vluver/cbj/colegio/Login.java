package com.vluver.cbj.colegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    //Button docente,estudiante;
    private ProgressDialog progreso;
    EditText co,cla;
    Button iniciar;
    TextView registrar,recuperarclave;
    private FirebaseAuth mAuth;
    private RelativeLayout divError;
    DataUser dataUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeMain);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        divError = findViewById(R.id.divError);
        progreso = new ProgressDialog(Login.this);
        co = findViewById(R.id.correo);
        cla = findViewById(R.id.clave);
        iniciar = findViewById(R.id.btn_login);
        registrar = findViewById(R.id.txtRegistrar);
        dataUser = new DataUser(this);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (co.getText().toString().isEmpty()){
                    co.setError("Ingrese ambos campos");
                }else if (cla.getText().toString().isEmpty()){
                    cla.setError("Ingrese ambos campos");
                }else {
                    login_user(co.getText().toString(),cla.getText().toString());
                }
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registrar.class));
            }
        });
       /* docente = findViewById(R.id.btn_docente);
        estudiante = findViewById(R.id.btn_estudiante);
        docente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SesionDocente.class));
            }
        });
        estudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SeleccionCursoActivity.class));
            }
        });*/
    }
    private void login_user (String mEmail, String mPassword) {
        progreso.setMessage("Iniciando...");
        progreso.setCancelable(false);
        progreso.show();
        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            getUser(mAuth.getUid());


                        } else {
                            progreso.dismiss();
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {
                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(Login.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(Login.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(Login.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(Login.this, "Ingresa bien tu correo electronico.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    divError.setVisibility(View.VISIBLE);
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(Login.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(Login.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(Login.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(Login.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(Login.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(Login.this, "Usuario desactivado por el administrador.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(Login.this, "Usuario no encontrado,puede ser que este eliminado.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(Login.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    divError.setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    Toast.makeText(Login.this, "Error desconocido.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            //  updateUI(null);
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            updateUI();
        }
    }

    private void getUser(String uid){
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/get/getUser.php?uid="+uid;
        VolleySingleton.getInstance(Login.this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.GET, url, (JSONObject) null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            if (!error) {
                                JSONObject user_JSON = response.getJSONObject("user");
                                dataUser.setNombres(user_JSON.getString("nombres"));
                                dataUser.setCorreo(user_JSON.getString("correo"));
                                dataUser.setTipodeusuario(user_JSON.getString("tipo de usuario"));
                                dataUser.setCedula(user_JSON.getString("cedula"));
                                dataUser.setCurso(user_JSON.getString("curso"));
                                dataUser.setCursoid(user_JSON.getString("curso_id"));
                                dataUser.setGenero(user_JSON.getString("genero"));
                                dataUser.setFechanacimiento(user_JSON.getString("fecha nacimiento"));
                                dataUser.setCiudad(user_JSON.getString("ciudad"));
                                dataUser.setNombreusuario(user_JSON.getString("nombre de usuario"));

                                if (dataUser.getTipodeusuario().equals("1")){
                                    saveInDB(dataUser.getCURSOID());
                                }else {
                                    saveInDBdocente(dataUser.getCedula());

                                }
                            } else {
                                progreso.dismiss();
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(Login.this, errorMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            progreso.dismiss();
                            Toast.makeText(Login.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progreso.dismiss();
                        checkerror(error);
                    }
                }
        ));
    }

    private void checkerror(VolleyError error){
        if( error instanceof NetworkError) {
            Toast.makeText(Login.this, "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(Login.this, "Servidores fallando", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(Login.this, "Error 404", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(Login.this, "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(Login.this, "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }

    private void saveInDB(String idcurso){
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/get/get_schedule.php?course_id="+idcurso;
        VolleySingleton.getInstance(Login.this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.GET, url, (JSONObject) null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            if (!error){
                                DatabaseHandler db = new DatabaseHandler (Login.this);
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
                                progreso.dismiss();
                                updateUI();
                                /*
                                JSONObject DPC_JSON = response.getJSONObject("docentes_del_curso");
                                JSONArray DPC_horarios = DPC_JSON.getJSONArray("docente");
                                for (int i = 0; i < DPC_horarios.length(); i++){
                                    JSONArray arrayDocenteDPC = DPC_JSON.getJSONArray("docente");
                                    JSONArray arrayMateriaDPC = DPC_JSON.getJSONArray("materias_del_docente");
                                    db.insertar_docente_por_curso(arrayDocenteDPC.getString(i),arrayMateriaDPC.getString(i));
                                }
                                progressDialog.dismiss();
                                Intent intent = new Intent(Login.this, EstudianteActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                            }else{
                                progreso.dismiss();
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(Login.this, errorMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            progreso.dismiss();
                            Toast.makeText(Login.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progreso.dismiss();
                        checkerror(error);
                    }
                }
        ));
    }

    private void saveInDBdocente(String cedula){
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/get/get_schedule_teacher.php?number_cedula="+cedula;
        VolleySingleton.getInstance(Login.this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.GET, url, (JSONObject) null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean error = response.getBoolean("error");
                            if (!error){
                                DatabaseHandler db = new DatabaseHandler (Login.this);
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
                                //estadoSesion.setLoginTeacher(true);
                                progreso.dismiss();
                                updateUI();
                            }else{
                                progreso.dismiss();
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(Login.this, errorMsg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            progreso.dismiss();
                            Toast.makeText(Login.this, ""+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progreso.dismiss();
                        checkerror(error);
                    }
                }
        ));
    }
    private void updateUI(){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }
}
