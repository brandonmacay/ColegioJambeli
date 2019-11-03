package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vluver.cbj.colegio.Adaptador.SearchUserAdapter;
import com.vluver.cbj.colegio.model.SearchUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchPerson extends AppCompatActivity implements  MaterialSearchBar.OnSearchActionListener {
    MaterialSearchBar searchBar;
    public RecyclerView mRVFish;
    public SearchUserAdapter mAdapter;
    List<SearchUser> data;
    //ProgressDialog finding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person);
        searchBar = findViewById(R.id.searchBarMain);
        searchBar.setOnSearchActionListener(this);
        searchBar.enableSearch();
        data=new ArrayList<>();
        mRVFish = (RecyclerView) findViewById(R.id.rv_resultados);
        mAdapter = new SearchUserAdapter(SearchPerson.this, data);
        mRVFish.setAdapter(mAdapter);
        mRVFish.setLayoutManager(new LinearLayoutManager(SearchPerson.this));
        //finding = new ProgressDialog(SearchPerson.this);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
                if (charSequence.toString().isEmpty()){
                    if (data != null){
                        data.clear();
                        mAdapter.clear();
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUser(charSequence.toString());
                if (charSequence.toString().isEmpty()){
                    if (data != null){
                        data.clear();
                        mAdapter.clear();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled){
            finish();
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        searchUser(text.toString());
        if (text.toString().isEmpty()){
            if (data != null){
                data.clear();
                mAdapter.clear();
            }
        }
    }
    private void searchUser(String text){
        //finding.setMessage("Buscando...");
        //finding.setCancelable(false);
        //finding.show();
        String url = "http://mrsearch.000webhostapp.com/apirestAndroid/searchPerson.php?searchQuery="+text+"&usuario="+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        VolleySingleton.
                getInstance(SearchPerson.this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET, url, (JSONObject) null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                       // finding.dismiss();
                                        if (data != null){
                                            data.clear();
                                            mAdapter.clear();
                                        }

                                        //Toast.makeText(SearchPerson.this, "Internet no disponible:\t"+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                );

    }
    private void procesarRespuesta(JSONObject response) {
        try {

            if (data != null){
                data.clear();
                mAdapter.clear();
            }
            boolean error = response.getBoolean("error");

            if (!error) {
                JSONObject postt = response.getJSONObject("user");

                JSONArray pray = postt.getJSONArray("fullnames");
                for (int i = 0; i < pray.length(); i++) {


                    JSONArray objPid = postt.getJSONArray("fullnames");
                    String fullnames = objPid.getString(i);

                    SearchUser userData = new SearchUser();

                    userData.userName = fullnames;
                    data.add(userData);
                }

                mAdapter.notifyDataSetChanged();
                //finding.dismiss();
                mRVFish.setAdapter(mAdapter);
                mRVFish.setLayoutManager(new LinearLayoutManager(SearchPerson.this));


            } else {
                //finding.dismiss();
                if (data != null){
                    data.clear();
                    mAdapter.clear();
                }

                String errorMsg = response.getString("error_msg");
                //Toast.makeText(SearchPerson.this, ""+errorMsg, Toast.LENGTH_SHORT).show();

            }

        } catch (JSONException e) {
            //finding.dismiss();
            if (data != null){
                data.clear();
                mAdapter.clear();
            }
            //Toast.makeText(SearchPerson.this, ""+e, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                finish();
                break;
        }
    }
}
