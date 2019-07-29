package com.vluver.cbj.colegio.Estudiante.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.vluver.cbj.colegio.R;
import com.vluver.cbj.colegio.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorarioFrag extends Fragment implements View.OnClickListener{
    View view;
    Button lu,ma,mi,ju,vi;
    RecyclerView recyclerView;
    public HorarioFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_horario, container, false);
        lu = view.findViewById(R.id.btnlunes);
        ma = view.findViewById(R.id.btnmartes);
        mi = view.findViewById(R.id.btnmiercoles);
        ju = view.findViewById(R.id.btnjueves);
        vi = view.findViewById(R.id.btnviernes);
        recyclerView = view.findViewById(R.id.rvHorario);
        lu.setOnClickListener(this);
        ma.setOnClickListener(this);
        mi.setOnClickListener(this);
        ju.setOnClickListener(this);
        vi.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
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
        getschedule();
        return view;
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
                break;
        }
    }

    private void getschedule(){
        String url = "https://jambeli.000webhostapp.com/rest/consultar.php?cedula=0703733287";
        VolleySingleton.
                getInstance(getContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET, url, (JSONObject) null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        try {

                                            Toast.makeText(getContext(), ""+response.getString("cedula"), Toast.LENGTH_SHORT).show();

                                            /*boolean error = response.getBoolean("error");
                                            if (GetDataAdapter1 != null){
                                                GetDataAdapter1.clear();
                                                mAdapter.clear();
                                            }

                                            if (!error) {
                                                /*JSONObject postt = response.getJSONObject("post");

                                                JSONArray pray = postt.getJSONArray("pid");
                                                fromId = response.getInt("nmen");
                                                case3= response.getInt("nmay");
                                                for (int i = 0; i < pray.length(); i++) {
                                                    JSONArray objPid = postt.getJSONArray("pid");
                                                    final String pid = objPid.getString(i);

                                                    JSONArray objname = postt.getJSONArray("name");
                                                    String name = objname.getString(i);

                                                    JSONArray objuid = postt.getJSONArray("unique_id");
                                                    final String uid = objuid.getString(i);

                                                    JSONArray objuserimage = postt.getJSONArray("avatar");
                                                    String userimage = objuserimage.getString(i);

                                                    JSONArray objcontent = postt.getJSONArray("content");
                                                    final String content = objcontent.getString(i);

                                                    JSONArray totalimg = postt.getJSONArray("totalimg");
                                                    int ttimg = totalimg.getInt(i);
                                                    JSONArray pathimgJSON = postt.getJSONArray("pathimg");
                                                    ArrayList<String> pathimg = new ArrayList<>();
                                                    JSONArray nameimgJSON = postt.getJSONArray("nameimg");
                                                    ArrayList<String> nameimg = new ArrayList<>();
                                                    pathimg.clear();
                                                    nameimg.clear();
                                                    if (ttimg > 0){
                                                        for (int con = 0; con < ttimg;con++){
                                                            JSONArray path = pathimgJSON.getJSONArray(i);
                                                            final String pathString = path.getString(con);
                                                            pathimg.add(pathString);

                                                            JSONArray nm = nameimgJSON.getJSONArray(i);
                                                            final String nmString = nm.getString(con);
                                                            nameimg.add(nmString);
                                                        }

                                                    }
                                                    JSONArray objdate = postt.getJSONArray("fecha");
                                                    String date = (String) objdate.get(i);
                                                    JSONArray objlike = postt.getJSONArray("estadolike");
                                                    final boolean mylike = objlike.getBoolean(i);
                                                    JSONArray objNlikes = postt.getJSONArray("likes");
                                                    final int n_likes = objNlikes.getInt(i);
                                                    JSONArray objNcomments = postt.getJSONArray("numcomments");
                                                    final int n_comments = objNcomments.getInt(i);
                                                    Posts posts = new Posts();
                                                    posts.set_post_id(uid);
                                                    posts.setUser(name);
                                                    posts.setAvatar(userimage);
                                                    posts.setPathimg(pathimg);
                                                    posts.setNameimg(nameimg);
                                                    posts.setDescription(content);
                                                    posts.setDate(date);
                                                    posts.set_our_like(mylike);
                                                    posts.setNum_imgs(ttimg);
                                                    posts.set_num_likes(n_likes);
                                                    GetDataAdapter1.add(posts);
                                                    //mAdapter.notifyItemInserted(GetDataAdapter1.size());
                                                }
                                                mAdapter.notifyDataSetChanged();
                                                mAdapter.setLoaded();
                                                recyclerView.setVisibility(View.VISIBLE);
                                                recyclerView.setVisibility(View.VISIBLE);
                                                mSwipe.setRefreshing(false);

                                            } else {
                                                String errorMsg = response.getString("error_msg");
                                                recyclerView.setVisibility(View.VISIBLE);
                                                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                            }*/

                                        } catch (JSONException e) {
                                            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        checkerror(error);

                                    }
                                }
                        )
                );
    }

    private void checkerror(VolleyError error){
        if( error instanceof NetworkError) {
            Toast.makeText(getContext(), "Sin  coneccion a internet", Toast.LENGTH_SHORT).show();
        } else if( error instanceof ServerError) {
            Toast.makeText(getContext(), "Servidores fallado", Toast.LENGTH_LONG).show();
        } else if( error instanceof AuthFailureError) {
            Toast.makeText(getContext(), "Error desconocido", Toast.LENGTH_LONG).show();
        } else if( error instanceof ParseError) {
            Toast.makeText(getContext(), "Error al parsear datos...", Toast.LENGTH_LONG).show();
        } else if( error instanceof TimeoutError) {
            Toast.makeText(getContext(), "Tiempo agotado!", Toast.LENGTH_LONG).show();
        }
    }
}
