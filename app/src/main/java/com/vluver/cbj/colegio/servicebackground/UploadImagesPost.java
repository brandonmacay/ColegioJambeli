package com.vluver.cbj.colegio.servicebackground;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vluver.cbj.colegio.VolleyMultipartRequest;
import com.vluver.cbj.colegio.VolleySingleton;
import com.vluver.cbj.colegio.utilidades.ConvertBitmapToByte;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UploadImagesPost extends IntentService {
    RequestQueue mQueue;
    String userId;

    private static VolleyMultipartRequest multipartRequest;
    public UploadImagesPost() {
        super("UploadImagesPost");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {



        mQueue = Volley.newRequestQueue(UploadImagesPost.this);

        userId = null;
        if (intent != null) {
            userId = intent.getStringExtra("userId");
        }
        ArrayList<Uri> mPhotos = new ArrayList<>();
        if (intent != null) {
            mPhotos = intent.getParcelableArrayListExtra("mPhotos");
        }
        assert intent != null;
        String descripcion = intent.getStringExtra("descripcion");
        String randomuuid = UUID.randomUUID().toString();
        long imagename = System.currentTimeMillis();
        ArrayList<Uri> finalMPhotos = mPhotos;
         multipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://mrsearch.000webhostapp.com/apirestAndroid/insert/api_post.php?apicall=uploadpic", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    Toast.makeText(UploadImagesPost.this, obj.getString("message") , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadImagesPost.this, "VolleyError: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", userId);
                params.put("foldername",randomuuid);
                params.put("itemfolder", String.valueOf(imagename));
                params.put("description",descripcion);
                // params.put("tags", tags);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                //for now just get bitmap data from ImageView
                for (int i = 0; i < finalMPhotos.size(); i++) {
                    params.put("fileToUpload[" + i + "]",
                            new DataPart(imagename + i + ".jpg",
                                    ConvertBitmapToByte
                                            .getfilebytefromuri(UploadImagesPost.this,finalMPhotos.get(i)),
                                    "image/*"));

                }
                return params;
            }
        };

        VolleySingleton.getInstance(UploadImagesPost.this).addToRequestQueue(multipartRequest);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }


}
