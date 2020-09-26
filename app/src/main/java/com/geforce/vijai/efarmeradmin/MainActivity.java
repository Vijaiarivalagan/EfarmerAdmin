// RA2
package com.geforce.vijai.efarmeradmin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




//setContentView(R.layout.activity_pd_result_view);

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL2 = "http://vijai1.eu5.org/efarmer/user_list.php";
    String JSON_IMAGE_TITLE_NAME2 = "name";
    String JSON_IMAGE_PHONE2= "phone";
    String JSON_IMAGE_USERID2 = "id";
    String JSON_IMAGE_DISTRICT2 = "district";
    String JSON_IMAGE_CITY2 = "city";
    String JSON_IMAGE_POSTAL2 = "postal";
    String loc="";
    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //sp=getSharedPreferences("user",MODE_PRIVATE);
        //loc=sp.getString("district","");

        //Intent i=getIntent();
        //String imgname=i.getStringExtra("img_name");

        getSupportActionBar().setTitle("Users list");


        //Toast.makeText(this,imgname,Toast.LENGTH_SHORT).show();
        //String data="?product="+imgname;
        //data+="&location="+loc;

        //GET_JSON_DATA_HTTP_URL2+=data;
        GetDataAdapter1 = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        JSON_DATA_WEB_CALL();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true; }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.help:
                Intent intent1=new Intent(this,Help.class);
                startActivity(intent1);
            default:
                break;
        }

        return true;

    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL2,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME2));

                //GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL2));

                //GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL2));
                //GetDataAdapter2.setImageDesc(json.getString(JSON_IMAGE_DESC2));
                //GetDataAdapter2.setImagePrice(json.getString(JSON_IMAGE_PRICE2));
                GetDataAdapter2.setImageUserId(json.getString(JSON_IMAGE_USERID2));
                GetDataAdapter2.setImagePhone(json.getString(JSON_IMAGE_PHONE2));
                GetDataAdapter2.setImageDistrict(json.getString(JSON_IMAGE_DISTRICT2));
                GetDataAdapter2.setImageCity(json.getString(JSON_IMAGE_CITY2));
                GetDataAdapter2.setImagePostal(json.getString(JSON_IMAGE_POSTAL2));
                //GetDataAdapter2.setImageId(json.getString(JSON_IMAGE_PID2));
                //GetDataAdapter2.setImageUpdate(json.getString(JSON_IMAGE_UPDATE2));
                //GetDataAdapter2.setImageRem(json.getString(JSON_IMAGE_REM2));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter2(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}

