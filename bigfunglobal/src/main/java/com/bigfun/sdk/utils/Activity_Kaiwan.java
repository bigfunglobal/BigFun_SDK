package com.bigfun.sdk.utils;

import static com.bigfun.sdk.BigFunSDK.SUT_BF;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigfun.sdk.R;

public class Activity_Kaiwan extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajsa);
        Dialog dialog=new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.jlsaodj, null);

        dialog.setContentView(view, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,
                ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        view.findViewById(R.id.oekacx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(intent,SUT_BF);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.urjasod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SUT_BF){
            Log.e("12312313","nfikals;ghjkl");
        }
    }
}
