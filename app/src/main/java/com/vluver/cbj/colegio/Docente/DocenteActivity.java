package com.vluver.cbj.colegio.Docente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.vluver.cbj.colegio.DatabaseHandler;
import com.vluver.cbj.colegio.Docente.fragments.HorarioDocenteFrag;
import com.vluver.cbj.colegio.EstadoSesion;
import com.vluver.cbj.colegio.Estudiante.fragments.DocentesFragment;
import com.vluver.cbj.colegio.MainActivity;
import com.vluver.cbj.colegio.R;

import java.util.ArrayList;
import java.util.List;

public class DocenteActivity extends AppCompatActivity {
    private String curso= null;
    DatabaseHandler db;
    EstadoSesion estadoSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_main);
        db = new DatabaseHandler (this);
        estadoSesion = new EstadoSesion(DocenteActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(""+estadoSesion.getDocente());
        toolbar.inflateMenu(R.menu.menu_estudiante);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.logout) {
                    db.borrarHorarioDocente(DocenteActivity.this);
                    estadoSesion.setLoginTeacher(false);
                    startActivity(new Intent(DocenteActivity.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                }
                return DocenteActivity.super.onOptionsItemSelected(item);
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        DocenteActivity.ViewPagerAdapter adapter = new DocenteActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HorarioDocenteFrag(), "Horario");
        adapter.addFragment(new DocentesFragment(), "Configuracion");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
