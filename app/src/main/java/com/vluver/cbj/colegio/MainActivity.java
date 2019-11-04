package com.vluver.cbj.colegio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vluver.cbj.colegio.Estudiante.EstudianteActivity;
import com.vluver.cbj.colegio.utilidades.FragmentHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    MaterialSearchBar searchBar;
    public static BottomNavigationViewPager mBottomNavigationViewPager;
    public static BottomMenuItemAdapter mBottomMenuItemAdapter;
    private FragmentHistory fragmentHistory;
    private FirebaseAuth mAuth;
    DatabaseHandler db;
    Fragment frag;
    BottomNavigationView navView;
    DataUser dataUser;
    TextView titulo;
    int s =0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int numero = 0;
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    titulo.setVisibility(View.GONE);
                    searchBar.setVisibility(View.VISIBLE);
                    searchBar.setPlaceHolder("Buscar usuarios...");
                    mBottomNavigationViewPager.setCurrentItem(0, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    s = numero;
                    return true;

                case R.id.navigation_home:
                    numero = 1;
                    titulo.setVisibility(View.GONE);
                    searchBar.setVisibility(View.VISIBLE);
                    searchBar.setPlaceHolder("Buscar en EducarPlus...");
                    mBottomNavigationViewPager.setCurrentItem(1, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    s = numero;
                    return true;
                case R.id.navigation_dashboard:
                    numero = 2;
                    /*if (dataUser.getTipodeusuario().equals("1")){
                        titulo.setText("Horario de " + dataUser.getCurso());
                    }else{
                        titulo.setText("Horario de " + dataUser.getNombres());
                    }*/
                    titulo.setVisibility(View.GONE);
                    searchBar.setVisibility(View.VISIBLE);
                    searchBar.setPlaceHolder("Buscar Docentes");
                    mBottomNavigationViewPager.setCurrentItem(2, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    s = numero;
                    return true;
                /*case R.id.navigation_notifications:
                    titulo.setVisibility(View.VISIBLE);
                    titulo.setText("Novedades");
                    searchBar.setVisibility(View.GONE);
                    numero = 3;
                    mBottomNavigationViewPager.setCurrentItem(3, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    s = numero;
                    return true;*/
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titulo = (TextView) findViewById(R.id.txttitulo);
        navView = findViewById(R.id.nav_view);
        db = new DatabaseHandler (this);
        dataUser = new DataUser(this);
        searchBar = findViewById(R.id.searchBar);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        searchBar.setOnSearchActionListener(this);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s == 0){
                    Intent intento = new Intent(MainActivity.this,SearchPerson.class);
                    startActivity(intento);
                    overridePendingTransition(0,0);
                }else if (s == 1){
                    Intent intent = new Intent(MainActivity.this, SearchInEducarPlus.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }else if (s == 2){
                    Intent intent = new Intent(MainActivity.this, SearchDocente.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }

            }
        });
        searchBar.setPlaceHolder("Buscar usuarios...");
        searchBar.inflateMenu(R.menu.menu_estudiante);
        searchBar.getMenu().setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logout){
                    logout();
                }

                return false;
            }
        });
        fragmentHistory = new FragmentHistory();
        mBottomNavigationViewPager = (BottomNavigationViewPager) findViewById(R.id.contentContainer);
        mBottomMenuItemAdapter = new BottomMenuItemAdapter(getSupportFragmentManager());
        mBottomNavigationViewPager.setAdapter(mBottomMenuItemAdapter);
        mBottomNavigationViewPager.setPagingEnable(false);
        frag = mBottomMenuItemAdapter.getCurrentFragment();
        fragmentHistory.push(0);
        mBottomNavigationViewPager.setOffscreenPageLimit(3);
    }
    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onBackPressed() {
        if (fragmentHistory.getStackSize() > 1){
            int position = fragmentHistory.popPrevious();
            fragmentHistory.pop();
            switch (position) {
                case 0:
                    searchBar.setPlaceHolder("Buscar personas...");
                    navView.setSelectedItemId(R.id.navigation_inicio);
                    break;
                case 1:
                    searchBar.setPlaceHolder("Buscar en EducarPlus...");
                    navView.setSelectedItemId(R.id.navigation_home);
                    break;
                case 2:
                    navView.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 3:
                    //navView.setSelectedItemId(R.id.navigation_notifications);
                    break;
                default:
                    navView.setSelectedItemId(R.id.navigation_inicio);
                    break;
            }
        }else{
            super.onBackPressed();

        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    public void logout(){
        db.borrarHorarioTemporalDocente();
        db.borrarHorarioEstudiante(MainActivity.this);
        db.borrarHorarioDocente(MainActivity.this);
        FirebaseAuth.getInstance().signOut();
        deleteCache(this);
        salir();
        // Intent get = getIntent();
        //String typelogin = get.getStringExtra("login_type");
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void salir(){
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
