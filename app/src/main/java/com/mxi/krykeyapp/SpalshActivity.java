package com.mxi.krykeyapp;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mxi.krykeyapp.json.CommonClass;
import com.mxi.krykeyapp.json.GetJsonData;


public class SpalshActivity extends Activity {

    TextView sp_tv_krykey, sp_tv_copy;
    Typeface faceReg, faceBold;

    GetJsonData gjdata;
    CommonClass cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        sp_tv_copy = (TextView) findViewById(R.id.sp_tv_copy);
        sp_tv_krykey = (TextView) findViewById(R.id.sp_tv_krykey);

        faceReg = Typeface.createFromAsset(this.getAssets(),
                "fonts/champagne_limousines.ttf");
        faceBold = Typeface.createFromAsset(this.getAssets(),
                "fonts/champagne_limousines_bold.ttf");

        sp_tv_krykey.setTypeface(faceBold);
        sp_tv_copy.setTypeface(faceReg);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        sp_tv_copy.setText("Copyright © " + year + " KryKey. All rights reserved.");

        gjdata = new GetJsonData();
        cc = new CommonClass(SpalshActivity.this);

        if (cc.isConnectingToInternet())
            new Asncy().execute();
        else
            Toast.makeText(SpalshActivity.this,
                    "Please connect your internet!", Toast.LENGTH_SHORT)
                    .show();
    }

    public class Asncy extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            gjdata.GetAllFrequencyList("GetAllFrequencyList");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


//		
//			for (int i = 0; i < 30; i++) {
//				Frequency mal = new Frequency();
//				mal.freq = String.valueOf(i);
//				GetJsonData.frequency.add(mal);			
//				
//			}


            SharedPreferences prefConfi = getApplicationContext()
                    .getSharedPreferences("MyPref", MODE_PRIVATE);

            if (GetJsonData.frequency.size() != 0) {
                if (prefConfi.getString("email", "").equals("")) {
                    Intent intent = new Intent(getApplicationContext(), Signup.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                if (cc.isConnectingToInternet())
                    new Asncy().execute();
                else
                    Toast.makeText(SpalshActivity.this,
                            "Please connect your internet!", Toast.LENGTH_SHORT)
                            .show();
            }

        }
    }


}
