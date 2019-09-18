package com.vluver.cbj.colegio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vluver.cbj.colegio.utilidades.FragmentHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    MaterialSearchBar searchBar;
    public static BottomNavigationViewPager mBottomNavigationViewPager;
    public static BottomMenuItemAdapter mBottomMenuItemAdapter;
    private FragmentHistory fragmentHistory;
    private FirebaseAuth mAuth;
    Fragment frag;
    BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int numero = 0;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    searchBar.setPlaceHolder("Buscar en EducarPlus...");
                    mBottomNavigationViewPager.setCurrentItem(0, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    return true;
                case R.id.navigation_dashboard:
                    numero = 1;
                    mBottomNavigationViewPager.setCurrentItem(1, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    return true;
                case R.id.navigation_notifications:
                    numero = 2;
                    mBottomNavigationViewPager.setCurrentItem(2, true);
                    frag = mBottomMenuItemAdapter.getCurrentFragment();
                    fragmentHistory.push(numero);
                    return true;
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        searchBar = findViewById(R.id.searchBar);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        searchBar.setOnSearchActionListener(this);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        searchBar.setPlaceHolder("Buscar en EducarPlus...");
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
                    searchBar.setPlaceHolder("Buscar en EducarPlus...");
                    navView.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    navView.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 2:
                    navView.setSelectedItemId(R.id.navigation_notifications);
                    break;
                default:
                    navView.setSelectedItemId(R.id.navigation_home);
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
