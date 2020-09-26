package com.geforce.vijai.efarmeradmin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

    }

    public void call(View view) {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+"7010630367"));
        startActivity(intent);

    }

    /*public void mail(View view) {
        String sub=subj.getText().toString().trim();
        String txt=text.getText().toString().trim();
        Intent maili=new Intent(Intent.ACTION_SEND);
        maili.setData(Uri.parse("mailto:"));
        maili.putExtra(Intent.EXTRA_EMAIL,"arivalagan588@gmail.com");
        maili.putExtra(Intent.EXTRA_SUBJECT,sub);
        maili.putExtra(Intent.EXTRA_TEXT,txt);
        try {
            startActivity(Intent.createChooser(maili,"Send mail"));
            finish();

        }
        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this,"not send",Toast.LENGTH_SHORT).show();
        }
        }*/


}
