package com.vluver.cbj.colegio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.vluver.cbj.colegio.Docente.DocenteActivity;
import com.vluver.cbj.colegio.Docente.SesionDocente;
import com.vluver.cbj.colegio.Estudiante.SeleccionCursoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText correo,clave,nombres,cedula;
    Button registro;
    int curso_seleccionadoint;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        progressDialog = new ProgressDialog(this);
        mQueue = Volley.newRequestQueue(Registrar.this);

        mAuth = FirebaseAuth.getInstance();
        nombres = findViewById(R.id.name);
        correo=findViewById(R.id.correo);
        clave=findViewById(R.id.contra);
        registro= findViewById(R.id.registrar);
        cedula = findViewById(R.id.cedula);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final LinearLayout mensaje = (LinearLayout) findViewById(R.id.cntd_estudiante);
        final RadioGroup opcionesCliente = (RadioGroup)findViewById(R.id.groupid);
        final RadioGroup opcionesgenero = (RadioGroup)findViewById(R.id.groupidgenero);

        opcionesCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.docente_radio:
                        cedula.setVisibility(View.VISIBLE);
                        mensaje.setVisibility(View.GONE);
                        break;
                    case R.id.estudiante_radio:
                        cedula.setText("");
                        mensaje.setVisibility(View.VISIBLE);
                        cedula.setVisibility(View.GONE);
                        break;
                }
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opcionesCliente.getCheckedRadioButtonId() == R.id.docente_radio){
                    if (nombres.getText().toString().isEmpty()){
                        nombres.setError("Ingrese sus nombres");
                    } else if (cedula.getText().toString().isEmpty()){
                        cedula.setError("Ingrese su cedula");
                    }else if (correo.getText().toString().isEmpty()){
                        correo.setError("Ingrese su correo");
                    }else if (clave.getText().toString().isEmpty()){
                        clave.setError("Ingrese su clave");
                    }else {
                        if (opcionesgenero.getCheckedRadioButtonId()== R.id.masculino){
                            register_user_firebase(correo.getText().toString(),clave.getText().toString(),0,0);

                        }else if (opcionesgenero.getCheckedRadioButtonId() == R.id.femenino){
                            register_user_firebase(correo.getText().toString(),clave.getText().toString(),1,0);

                        }
                    }

                }else if (opcionesCliente.getCheckedRadioButtonId() == R.id.estudiante_radio){
                    if (nombres.getText().toString().isEmpty()){
                        nombres.setError("Ingrese sus nombres");
                    }else if (correo.getText().toString().isEmpty()){
                        correo.setError("Ingrese su correo");
                    }else if (clave.getText().toString().isEmpty()){
                        clave.setError("Ingrese su clave");
                    }else {
                        if (opcionesgenero.getCheckedRadioButtonId()== R.id.masculino){
                            register_user_firebase(correo.getText().toString(),clave.getText().toString(),0,1);

                        }else if (opcionesgenero.getCheckedRadioButtonId() == R.id.femenino){
                            register_user_firebase(correo.getText().toString(),clave.getText().toString(),1,1);

                        }

                    }
                }



            }
        });
        spinner.setOnItemSelectedListener(Registrar.this);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        curso_seleccionadoint = i;
    }

    private void register_user_firebase (String mEmail, String mPassword, final int genero_xd,final int tipodeusuario) {
        progressDialog.setMessage("Registrando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(Registrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nombres.getText().toString())
                                        //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                        .build();
                                user.sendEmailVerification();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    registerUserToDB(user.getUid(),user.getEmail(),nombres.getText().toString(),tipodeusuario,cedula.getText().toString(),curso_seleccionadoint,genero_xd);
                                                }
                                            }
                                        });
                            }


                        }
                        else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Registrar.this, "No fuiste registrado",
                                    Toast.LENGTH_LONG).show();

                            // FirebaseAuth.getInstance().signOut();
                        }


                    }
                });
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void registerUserToDB(final String uid, final String email, final String names, final int tipo_usuario,
                                  final String num_cedula, final int curso, final int gender){
        String url = "http://jambeli.hostingerapp.com/apirestAndroid/register.php";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        progressDialog.dismiss();
                        Intent intent = new Intent(Registrar.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(Registrar.this, errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(Registrar.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                checkerror(error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);
                params.put("email",email);
                params.put("names",names);
                params.put("tipo_usuario", String.valueOf(tipo_usuario));
                params.put("gender", String.valueOf(gender));
                params.put("num_cedula",num_cedula);
                params.put("curso", String.valueOf(curso));
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(strReq);
    }
    private void checkerror(VolleyError error){
        if( error instanceof NetworkError) {
            Toast.makeText(Registrar.this, "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(Registrar.this, "Servidores fallando", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(Registrar.this, "Error 404", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(Registrar.this, "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(Registrar.this, "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }

}
