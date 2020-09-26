//show seller of clicked products -result for products
//RA
package com.geforce.vijai.efarmeradmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class pd_result_view extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://vijai1.eu5.org/efarmer/user_products.php";
    String JSON_IMAGE_TITLE_NAME = "pname";
    String JSON_IMAGE_URL = "pphoto";
    String JSON_IMAGE_PID = "pid";
    String JSON_IMAGE_UPDATE = "updated_at";
    String JSON_IMAGE_PRICE = "pprice";
    String JSON_IMAGE_DESC = "pdesc";
    String JSON_IMAGE_PHONE= "phone";
    String JSON_IMAGE_USERID = "id";
    String JSON_IMAGE_DISTRICT = "district";
    String JSON_IMAGE_CITY = "city";
    String JSON_IMAGE_POSTAL = "postal";
    String JSON_IMAGE_REM="prem";
    String phone;
    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;
    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pd_result_view);

        Intent i=getIntent();
        String id=i.getStringExtra("product_userid");
         phone=i.getStringExtra("user_phone");

        getSupportActionBar().setTitle(id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String data="?id="+id;

        GET_JSON_DATA_HTTP_URL+=data;
        GetDataAdapter1 = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        JSON_DATA_WEB_CALL();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_eample, menu);
        return true; }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // action with ID action_refresh was selected
                case R.id.call:
                    Intent intent=new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phone));
                    startActivity(intent);
                    break;
                case R.id.help:
                    Intent intent1=new Intent(this,Help.class);
                    startActivity(intent1);
                default:
                    break;
            }

            return true;

    }
        /*
    @Override
    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_example, menu);
    }*/

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

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

                GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME));

                GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));

                //GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));
                GetDataAdapter2.setImageDesc(json.getString(JSON_IMAGE_DESC));
                GetDataAdapter2.setImagePrice(json.getString(JSON_IMAGE_PRICE));
                //GetDataAdapter2.setImageUserId(json.getString(JSON_IMAGE_USERID));
                GetDataAdapter2.setImagePhone(json.getString(JSON_IMAGE_PHONE));
                GetDataAdapter2.setImageDistrict(json.getString(JSON_IMAGE_DISTRICT));
                //GetDataAdapter2.setImageCity(json.getString(JSON_IMAGE_CITY));
                //GetDataAdapter2.setImagePostal(json.getString(JSON_IMAGE_POSTAL));
                //GetDataAdapter2.setImageId(json.getString(JSON_IMAGE_ID));
                //GetDataAdapter2.setImageUpdate(json.getString(JSON_IMAGE_UPDATE));
                GetDataAdapter2.setImageRem(json.getString(JSON_IMAGE_REM));




            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}