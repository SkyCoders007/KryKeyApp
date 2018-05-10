package com.mxi.krykeyapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mxi.krykeyapp.json.GetJsonData;


public class Signup extends Activity {

    TextView sett_change_tv_header, sett_change_tv_name, sett_change_tv_email, sett_change_tv_verify,
            sett_change_tv_city, sett_change_tv_country, sett_change_tv_timezone,
            sett_change_tv_timezone_value, sett_change_tv_installed, sett_change_tv_installed_value,
            sett_change_tv_Updated, sett_change_tv_updated_value, sett_change_tv_version,
            sett_change_tv_version_value, sett_change_tv_copyright_value;

    TextView header_name;
    EditText sett_change_et_name, sett_change_et_email, sett_change_et_verify, sett_change_et_city;

    Button signup_btn, signup_update;
    Typeface faceReg, faceBold;
    SharedPreferences prefConfi;
    GetJsonData gjdata;
    String name, email, verify, city, country, timezone, device, manufacturer, myVersion, membershipkey;
    Spinner signup_country;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_change_info);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        faceReg = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/champagne_limousines.ttf");
        faceBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/champagne_limousines_bold.ttf");

        prefConfi = getApplicationContext()
                .getSharedPreferences("MyPref", MODE_PRIVATE);
        gjdata = new GetJsonData();

        signup_btn = (Button) findViewById(R.id.signup_btn);
        signup_update = (Button) findViewById(R.id.signup_update);


        sett_change_tv_header = (TextView) findViewById(R.id.sett_change_tv_header);
        sett_change_tv_header.setTypeface(faceBold);
        sett_change_tv_name = (TextView) findViewById(R.id.sett_change_tv_name);
        sett_change_tv_name.setTypeface(faceBold);
        sett_change_tv_email = (TextView) findViewById(R.id.sett_change_tv_email);
        sett_change_tv_email.setTypeface(faceBold);
        sett_change_tv_verify = (TextView) findViewById(R.id.sett_change_tv_verify);
        sett_change_tv_verify.setTypeface(faceBold);
        sett_change_tv_city = (TextView) findViewById(R.id.sett_change_tv_city);
        sett_change_tv_city.setTypeface(faceBold);
        sett_change_tv_country = (TextView) findViewById(R.id.sett_change_tv_country);
        sett_change_tv_country.setTypeface(faceBold);
        sett_change_tv_timezone = (TextView) findViewById(R.id.sett_change_tv_timezone);
        sett_change_tv_timezone.setTypeface(faceBold);

        sett_change_tv_timezone_value = (TextView) findViewById(R.id.sett_change_tv_timezone_value);
        sett_change_tv_timezone_value.setTypeface(faceBold);
        sett_change_tv_installed = (TextView) findViewById(R.id.sett_change_tv_installed);
        sett_change_tv_installed.setTypeface(faceBold);
        sett_change_tv_installed_value = (TextView) findViewById(R.id.sett_change_tv_installed_value);
        sett_change_tv_installed_value.setTypeface(faceBold);
        sett_change_tv_Updated = (TextView) findViewById(R.id.sett_change_tv_Updated);
        sett_change_tv_Updated.setTypeface(faceBold);
        sett_change_tv_updated_value = (TextView) findViewById(R.id.sett_change_tv_updated_value);
        sett_change_tv_updated_value.setTypeface(faceBold);
        sett_change_tv_version = (TextView) findViewById(R.id.sett_change_tv_version);
        sett_change_tv_version.setTypeface(faceBold);
        sett_change_tv_version_value = (TextView) findViewById(R.id.sett_change_tv_version_value);
        sett_change_tv_version_value.setTypeface(faceBold);
        sett_change_tv_copyright_value = (TextView) findViewById(R.id.sett_change_tv_copyright_value);
        sett_change_tv_copyright_value.setTypeface(faceBold);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        sett_change_tv_copyright_value.setText("Copyright © 2006-" + year);

        sett_change_et_name = (EditText) findViewById(R.id.sett_change_et_name);
        sett_change_et_name.setTypeface(faceBold);
        sett_change_et_email = (EditText) findViewById(R.id.sett_change_et_email);
        sett_change_et_email.setTypeface(faceBold);
        sett_change_et_verify = (EditText) findViewById(R.id.sett_change_et_verify);
        sett_change_et_verify.setTypeface(faceBold);
        sett_change_et_city = (EditText) findViewById(R.id.sett_change_et_city);
        sett_change_et_city.setTypeface(faceBold);


        ArrayList<String> country_list = new ArrayList<String>();
        country_list = gjdata.getCountry(getApplicationContext());
        for (int i = 0; i < country_list.size(); i++) {
            Log.e("cccc", country_list.get(i));
        }
//		 sett_change_et_country.setTypeface(faceBold);
        signup_country = (Spinner) findViewById(R.id.signup_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Signup.this,
                android.R.layout.simple_list_item_1, country_list);
        signup_country.setAdapter(adapter);
        signup_country.setPrompt("Select your country");
        signup_country.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                ((TextView) view).setTextColor(Color.parseColor("#FFFFFF"));
                country = signup_country.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        TimeZone timezon1 = TimeZone.getDefault();
        String timezone1 = timezon1.getDisplayName().toString();
        String[] separated = timezone1.split(" ");
//			sett_change_et_country.setText(separated[0]);

        header_name = (TextView) findViewById(R.id.header_name);
        header_name.setTypeface(faceReg);
        header_name.setText("Setting");

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        sett_change_tv_version_value.setText(version);
        sett_change_tv_updated_value.setText(version);

        if (prefConfi.getString("currentDateandTime", "").equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String currentDateandTime = sdf.format(new Date());
            Editor editorConfi = prefConfi.edit();
            editorConfi.putString("currentDateandTime", currentDateandTime);
            editorConfi.commit();
            sett_change_tv_installed_value.setText(currentDateandTime);
        } else {
            sett_change_tv_installed_value.setText(prefConfi.getString("currentDateandTime", ""));
        }

        //Get Data from shered Pref
        name = prefConfi.getString("name", "");
        email = prefConfi.getString("email", "");
        city = prefConfi.getString("city", "");
        country = prefConfi.getString("country", "");

        membershipkey = prefConfi.getString("membershipkey", "");

        if (!name.equals("")) {
            sett_change_et_name.setText(name);
            sett_change_et_email.setText(email);
            sett_change_et_verify.setText(email);
            sett_change_et_city.setText(city);
//			 sett_change_et_country.setText(country);

            signup_btn.setVisibility(View.GONE);
            signup_update.setVisibility(View.VISIBLE);
        }


        // Get TimeZone of Device
        TimeZone timezon = TimeZone.getDefault();
        timezone = timezon.getDisplayName().toString();

        //Get Device Name
        device = Build.MODEL;
        manufacturer = Build.MANUFACTURER;

        // Get OS version
        myVersion = Build.VERSION.RELEASE;

        sett_change_tv_timezone_value.setText(timezone);


    }


    public void saveData(View v) {
        name = sett_change_et_name.getText().toString().trim();
        email = sett_change_et_email.getText().toString().trim();
        verify = sett_change_et_verify.getText().toString().trim();
        city = sett_change_et_city.getText().toString().trim();
//		country = sett_change_et_country.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(Signup.this, "Please enter your name", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(Signup.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
        } else if (!verify.equals(email)) {
            Toast.makeText(Signup.this, "Please enter same email address", Toast.LENGTH_SHORT).show();
        } else if (city.equals("")) {
            Toast.makeText(Signup.this, "Please enter your city", Toast.LENGTH_SHORT).show();
        } else if (country.equals("")) {
            Toast.makeText(Signup.this, "Please enter your country", Toast.LENGTH_SHORT).show();
        } else {
            new AsyncSaveData().execute();
        }
    }

    public class AsyncSaveData extends AsyncTask<String, Void, Void> {
        String responce;
        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(Signup.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progressdialog);
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            responce = gjdata.SaveUserData(name, email, city, country, timezone, "Android", device, myVersion);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
//			Toast.makeText(getApplicationContext(), responce, 1).show();
            Editor editorConfi = prefConfi.edit();
            editorConfi.putString("name", name);
            editorConfi.putString("email", email);
            editorConfi.putString("city", city);
            editorConfi.putString("country", country);
            editorConfi.putString("membershipkey", responce);

            editorConfi.commit();

            if (SettingsActivity.tag_setting == 1) {
                finish();
                SettingsActivity.tag_setting = 0;
            } else {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
                finish();
            }


        }
    }

    public void skip(View v) {
        if (SettingsActivity.tag_setting == 1) {
            finish();
            SettingsActivity.tag_setting = 0;
        } else {
            Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mIntent);
            finish();
        }
    }

    public void updateData(View v) {
        name = sett_change_et_name.getText().toString().trim();
        email = sett_change_et_email.getText().toString().trim();
        verify = sett_change_et_verify.getText().toString().trim();
        city = sett_change_et_city.getText().toString().trim();
//		country = sett_change_et_country.getText().toString().trim();


        if (name.equals("")) {
            Toast.makeText(Signup.this, "Please enter your name", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            Toast.makeText(Signup.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
        } else if (!verify.equals(email)) {
            Toast.makeText(Signup.this, "Please enter same email address", Toast.LENGTH_SHORT).show();
        } else if (city.equals("")) {
            Toast.makeText(Signup.this, "Please enter your city", Toast.LENGTH_SHORT).show();
        } else if (country.equals("")) {
            Toast.makeText(Signup.this, "Please enter your country", Toast.LENGTH_SHORT).show();
        } else {
            new AsyncUpdateData().execute();
        }
    }

    public class AsyncUpdateData extends AsyncTask<String, Void, Void> {
        String responce;
        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(Signup.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progressdialog);
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            responce = gjdata.UpdateUserData(name, email, city, country, timezone, membershipkey);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
//			Toast.makeText(getApplicationContext(), responce, 1).show();
            Editor editorConfi = prefConfi.edit();
            editorConfi.putString("name", name);
            editorConfi.putString("email", email);
            editorConfi.putString("city", city);
            editorConfi.putString("country", country);
//			Toast.makeText(getApplicationContext(), responce, 1).show();

            editorConfi.commit();

            if (SettingsActivity.tag_setting == 1) {
                finish();
                SettingsActivity.tag_setting = 0;
            } else {
                Intent mIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mIntent);
                finish();
            }


        }
    }

    private boolean isValidEmail(String email) {
        // TODO Auto-generated method stub
        String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}