package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PerfilPersona extends AppCompatActivity {
    Intent data;
    TextView txtnombres;
    TextView txtTipo;
    TextView txtArea;
    Button btnFollow;
    ImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_persona);
        data = this.getIntent();
        exit = (ImageView) findViewById(R.id.back);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtnombres = (TextView) findViewById(R.id.name_user);
        txtTipo = (TextView) findViewById(R.id.tipo);
        txtArea = (TextView) findViewById(R.id.area);
        btnFollow = (Button) findViewById(R.id.btnseguir);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFollow.getText().toString().equals("Seguir")){
                    btnFollow.setEnabled(false);
                    sendRequestFollow(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),data.getStringExtra("userUID"), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                }
            }
        });
        setUser();
    }
    private void setUser(){
        String nombres = null;
        String tipo = null;
        String UID = null;
        String area = null;
        int statefollow = 0;
        if (data != null) {
            nombres = data.getStringExtra("userName");
            tipo = data.getStringExtra("userTipo");
            UID = data.getStringExtra("userUID");
            area = data.getStringExtra("userArea");
        }
        txtnombres.setText(nombres);
        txtTipo.setText(tipo);
        txtArea.setText(area);
    }

    private void sendRequestFollow(String user_sender, String user_receiver,String nameuser){
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/sendaction/followuser.php?user_sender="+user_sender+"&&user_receiver="+user_receiver+"&&accepted="+1+"&&username="+nameuser;
        VolleySingleton.
                getInstance(PerfilPersona.this).
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
                                                btnFollow.setText("Siguiendo");
                                                btnFollow.setEnabled(true);
                                            }else{
                                                btnFollow.setText("Siguiendo");
                                                btnFollow.setEnabled(true);
                                                String errorMsg = response.getString("error_msg");
                                                Toast.makeText(PerfilPersona.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(PerfilPersona.this, "Algo anda mal"+e, Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // finding.dismiss();
                                        Toast.makeText(getApplicationContext(),
                                                error.getMessage(), Toast.LENGTH_LONG).show();
                                        //Toast.makeText(SearchPerson.this, "Internet no disponible:\t"+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                );
    }
}
