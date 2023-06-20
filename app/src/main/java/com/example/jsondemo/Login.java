package com.example.jsondemo;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    EditText email1,pass1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email1 = (EditText) findViewById(R.id.email1);
        pass1 = (EditText) findViewById(R.id.pass1);
    }
    public  void login(View view)
    {
        String email = email1.getText().toString();
        String pass = pass1.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://192.168.1.17/Jsondemo.php/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                        try {
                             String name = null,email=null,gender=null;

                            JSONObject jsonObject = new JSONObject(response);//for total object of json{
                            JSONArray jsonArray = jsonObject.optJSONArray("responce_obj");//responce_obj is array name -> for array[
                            //for loop is use to triverse the array
                            for(int i = 0 ; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                name = jsonObject1.optString("name").toString();
                                email = jsonObject1.optString("email").toString();
                                gender = jsonObject1.optString("gender").toString();
                            }
                            Toast.makeText(Login.this, "Name : "+name+"\nEmail : "+email+"\nGender : "+gender, Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
//                            throw new RuntimeException(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hm = new HashMap<>();
                hm.put("key_email",email);
                hm.put("key_pass",pass);
                return hm;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void openGetAllUsersData(View view)
    {
        startActivity(new Intent(Login.this,GetAllUsersData.class));
    }
}