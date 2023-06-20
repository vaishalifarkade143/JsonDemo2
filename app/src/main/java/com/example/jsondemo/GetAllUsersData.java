package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllUsersData extends AppCompatActivity
{
    RecyclerView rv_data;

    ArrayList al_name,al_email,al_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_users_data);

        rv_data = (RecyclerView) findViewById(R.id.rv_data);
        al_name =  new ArrayList();
        al_email = new ArrayList();
        al_gender = new ArrayList();

        getUserData();
    }

    //for getting all the data thru volley lib

    void getUserData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://192.168.1.17/Jsondemo.php/getallusersdata",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(GetAllUsersData.this, ""+response, Toast.LENGTH_SHORT).show();
                        //we are parsing the json responce to string
                        if (response.equals("fail"))
                        {
                            Toast.makeText(GetAllUsersData.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String name,email,gender;
                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.optJSONArray("responce_obj");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                                    name = jsonObject1.optString("name");
                                    email =  jsonObject1.optString("email");
                                    gender = jsonObject1.optString("gender");

                                    al_name.add(name);
                                    al_email.add(email);
                                    al_gender.add(gender);
                                }
                                MyAdapter myAdapter = new MyAdapter(al_name,al_email,al_gender);
                                rv_data.setLayoutManager(new LinearLayoutManager(GetAllUsersData.this));
                                rv_data.setAdapter(myAdapter);

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GetAllUsersData.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                }
        );

        requestQueue.add(stringRequest);
    }
}