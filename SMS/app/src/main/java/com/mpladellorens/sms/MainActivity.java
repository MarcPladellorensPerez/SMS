package com.mpladellorens.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int  PERMISSIONS_REQUEST=100;
    EditText telefon, missatge;
    Button btndirecte, btnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telefon=findViewById(R.id.telefon);
        missatge=findViewById(R.id.missatge);
        btndirecte=findViewById(R.id.btndirecte);
        btndirecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(telefon.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Introduir un num de telef",
                            Toast.LENGTH_SHORT).show();
                } else {
                    enviarSMS(telefon.getText().toString(), missatge.getText().toString());
                }
            }
        });
        btnapp=findViewById(R.id.btnapp);
        btnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obrirAppSms(telefon.getText().toString(), missatge.getText().toString());
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_SMS
            }, PERMISSIONS_REQUEST);
        }
    }

    private void enviarSMS(String tel, String msg) {
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(tel, null, msg, null, null);
        Toast.makeText(this, "Missatge enviat!", Toast.LENGTH_SHORT).show();
    }

    private void obrirAppSms(String tel, String msg) {
        Toast.makeText(this, "Obrint SMS App", Toast.LENGTH_SHORT).show();
        Intent intentSms = new Intent(Intent.ACTION_SENDTO);
        intentSms.setData(Uri.parse("smsto: " + Uri.encode(tel)));
        startActivity(intentSms);
    }

}