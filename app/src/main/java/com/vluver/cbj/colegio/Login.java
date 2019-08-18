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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.vluver.cbj.colegio.Docente.DocenteActivity;
import com.vluver.cbj.colegio.Estudiante.EstudianteActivity;

public class Login extends AppCompatActivity {

    //Button docente,estudiante;
    private ProgressDialog progreso;
    EditText co,cla;
    Button iniciar;
    TextView registrar,recuperarclave;
    private FirebaseAuth mAuth;
    private RelativeLayout divError;
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
        EstadoSesion estadoSesion = new EstadoSesion(this);
        if (estadoSesion.isLoggedInStudent()) {

            Intent intent = new Intent(Login.this, EstudianteActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else if (estadoSesion.isLoggedInTeacher()){
            Intent intent = new Intent(Login.this, DocenteActivity.class);
            startActivity(intent);
            finish();
        }
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
                            progreso.dismiss();
                            updateUI();


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
                }).addOnFailureListener(Login.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
    private void updateUI(){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }
}
