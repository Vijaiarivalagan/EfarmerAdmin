
//Login page

package com.geforce.vijai.efarmeradmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText lphone,lotp;
    Button login,lcheck;
    private ProgressBar progressBar;
    private String verificationId;
    private FirebaseAuth mAuth;
    String url="http://vijai1.eu5.org/efarmer/adminlogin.php";
    String data="",sharedid="0",sharedphone="",user_name="";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lphone=(EditText)findViewById(R.id.login_phone);
        lotp=(EditText)findViewById(R.id.lotp);
        lcheck=(Button)findViewById(R.id.lcheck);
        login=(Button)findViewById(R.id.llogin);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        login.setVisibility(View.INVISIBLE);
        //for get otp
        lcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = lphone.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    lphone.setError("Valid number is required");
                    lphone.requestFocus();
                    return;
                }
                else if(number.length()==10) {
                    String phoneNumber = "+91" + number;
                    sendVerificationCode(phoneNumber);
                    login.setVisibility(View.VISIBLE);
                }

            }
        });
        // for click login

        findViewById(R.id.llogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = lotp.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    lotp.setError("Enter code...");
                    lotp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    //
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            checkdb();
                            // return;


                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }




    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        closekeyboard();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private void closekeyboard() {
        View view=this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                lotp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };




    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    //onstart finished
    //to check db
    private void checkdb() {

        String phone_no=lphone.getText().toString();
        data="?phone="+phone_no;
        url+=data;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("message");
                    boolean error=jsonObject.getBoolean("error");
                    if(!error){
                        sharedid=jsonObject.getString("id");
                        user_name=jsonObject.getString("username");

                        sp =getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor et=sp.edit();
                        et.putString("phone",lphone.getText().toString());
                        et.putString("id",sharedid);
                        et.putString("name",user_name);
                        et.commit();

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"login as "+user_name,Toast.LENGTH_SHORT).show();

                        Intent i =new Intent(getApplicationContext(),MainActivity.class);
                        //i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),""+message.toString(),Toast.LENGTH_LONG).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"error"+e.toString(),Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),""+error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //Toast.makeText(getApplicationContext(),"user login destroy",Toast.LENGTH_SHORT).show();
        url="http://vijai1.eu5.org/efarmer/adminlogin.php";



    }

}

