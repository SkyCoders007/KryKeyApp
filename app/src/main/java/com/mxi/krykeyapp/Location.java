package com.mxi.krykeyapp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mxi.krykeyapp.adapter.FrqNameAdapter;
import com.mxi.krykeyapp.json.CommonClass;
import com.mxi.krykeyapp.json.GetJsonData;

public class Location extends Activity implements OnClickListener {

    ListView location_listview;
    GetJsonData gjdata;
    ImageView location_main, location_search, location_location, location_setting;
    TextView header_name, location_tv_no_radio;
    CommonClass cc;
    String method, name_of_object, name_method;
    Typeface faceReg, faceBold;
    GPSTracker gps;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        setContentView(R.layout.activity_location);

        gjdata = new GetJsonData();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        faceReg = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/champagne_limousines.ttf");
        faceBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/champagne_limousines_bold.ttf");

        location_listview = (ListView) findViewById(R.id.location_listview);
        location_main = (ImageView) findViewById(R.id.location_main);
        location_main.setOnClickListener(this);
        location_search = (ImageView) findViewById(R.id.location_search);
        location_search.setOnClickListener(this);
        location_location = (ImageView) findViewById(R.id.location_location);
        location_location.setOnClickListener(this);
        location_setting = (ImageView) findViewById(R.id.location_setting);
        location_setting.setOnClickListener(this);
        location_tv_no_radio = (TextView) findViewById(R.id.location_tv_no_radio);
        location_tv_no_radio.setVisibility(View.GONE);
        location_tv_no_radio.setTypeface(faceReg);
        header_name = (TextView) findViewById(R.id.header_name);
        header_name.setTypeface(faceReg);

        cc = new CommonClass(getApplicationContext());


        gps = new GPSTracker(Location.this);


//		displayPromptForEnablingGPS(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.location_main:
                finish();
                break;
            case R.id.location_search:
                Intent locaIntent = new Intent(getApplicationContext(), Search.class);
                startActivity(locaIntent);
                finish();
                break;
            case R.id.location_setting:
                Intent setttingIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(setttingIntent);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

    public class getRadioName extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(Location.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progressdialog);
            progressDialog.setCancelable(false);
        }


        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub

            gjdata.FindStationBy(method, name_of_object, name_method);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (GetJsonData.FindStationByArray.size() == 0) {

                location_tv_no_radio.setVisibility(View.VISIBLE);

            } else {
                FrqNameAdapter adapter = new FrqNameAdapter(
                        getApplicationContext(), 0, GetJsonData.FindStationByArray);
                location_listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                location_listview.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        MediaPlayerDemo_Audio medioPlayer = new MediaPlayerDemo_Audio();
                        medioPlayer.stopReadio();
                        //					Toast.makeText(getApplicationContext(),
                        //							GetJsonData.FindStationByArray.get(position).radio,
                        //							1).show();
                        MainActivity.radio_frq = GetJsonData.FindStationByArray
                                .get(position).freq;
                        MainActivity.h_name = GetJsonData.FindStationByArray
                                .get(position).radio;
                        MainActivity.h_gener = GetJsonData.FindStationByArray
                                .get(position).genre;
                        MainActivity.h_city = GetJsonData.FindStationByArray
                                .get(position).city;
                        MainActivity.tag_play = "1";
                        finish();
                    }
                });
            }


        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (statusOfGPS) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            if (latitude != 0) {
                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());

                    method = "FindStationByCity";
                    name_method = "city";
                    name_of_object = addresses.get(0).getLocality();
                    header_name.setText("SEARCH : YOUR CITY, " + addresses.get(0).getLocality().toUpperCase());
                    new getRadioName().execute();
                } else {
                    location_tv_no_radio.setVisibility(View.VISIBLE);
                    location_tv_no_radio.setText("Location not found");
                }
            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            location_tv_no_radio.setVisibility(View.VISIBLE);
            location_tv_no_radio.setText("Location not found");
            gps.showSettingsAlert();
        }
    }

}

