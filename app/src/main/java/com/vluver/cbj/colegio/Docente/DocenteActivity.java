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
import com.vluver.cbj.colegio.Login;
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.SearchInEducarPlus;

import java.util.ArrayList;
import java.util.List;

public class DocenteActivity extends AppCompatActivity {
    private String curso= null;
    DatabaseHandler db;
    //EstadoSesion estadoSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente_main);
        db = new DatabaseHandler (this);
        //estadoSesion = new EstadoSesion(DocenteActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(""+estadoSesion.getDocente());
        toolbar.inflateMenu(R.menu.menu_docente);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.logout) {
                    db.borrarHorarioDocente(DocenteActivity.this);
                    //estadoSesion.setLoginTeacher(false);
                    startActivity(new Intent(DocenteActivity.this, Login.class));
                    overridePendingTransition(0, 0);
                    finish();
                }else if (id == R.id.search_bar){
                    startActivity(new Intent(DocenteActivity.this, SearchInEducarPlus.class));
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
