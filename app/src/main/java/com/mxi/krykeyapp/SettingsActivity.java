package com.mxi.krykeyapp;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxi.krykeyapp.json.GetJsonData;

public class SettingsActivity extends Activity implements OnClickListener{

	LinearLayout setting_ll_show_info;
	ImageView setting_edit;
	Typeface faceReg,faceBold;
	TextView setting_tv_appinfo,setting_tv_name,setting_tv_city,setting_tv_country,header_name,
	setting_tv_time,setting_tv_installed,setting_tv_updated,setting_tv_version,setting_tv_copyright,
	setting_tv_time1,setting_tv_installed1,setting_tv_updated1,setting_tv_version1,
	setting_tv_mail,setting_tv_mail1;
	TextView setting_tv_name1,setting_tv_city1,setting_tv_country1,seting_tv_tap;
	GetJsonData gjdata;
	SharedPreferences prefConfi;
	String name,email,verify,city,country,install_device;
	ImageView search_main, search_search, search_location, search_setting
			,setting_1,setting_2,setting_3,setting_4;
	
	public static int tag_setting=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().hide();
		setContentView(R.layout.activity_settings);
		
		setting_ll_show_info = (LinearLayout)findViewById(R.id.setting_ll_show_info);
		setting_edit = (ImageView)findViewById(R.id.setting_edit);
		
		 faceReg = Typeface.createFromAsset(getApplicationContext().getAssets(),
					"fonts/champagne_limousines.ttf");
		 faceBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
					"fonts/champagne_limousines_bold.ttf");
		 
		 setting_tv_appinfo = (TextView)findViewById(R.id.setting_tv_appinfo);
		 setting_tv_appinfo.setTypeface(faceBold);
		 setting_tv_name = (TextView)findViewById(R.id.setting_tv_name);
		 setting_tv_name.setTypeface(faceBold);
		 setting_tv_city = (TextView)findViewById(R.id.setting_tv_city);
		 setting_tv_city.setTypeface(faceBold);
		 setting_tv_country = (TextView)findViewById(R.id.setting_tv_country);
		 setting_tv_country.setTypeface(faceBold);
		 setting_tv_time = (TextView)findViewById(R.id.setting_tv_time);
		 setting_tv_time.setTypeface(faceBold);
		 setting_tv_installed = (TextView)findViewById(R.id.setting_tv_installed);
		 setting_tv_installed.setTypeface(faceBold);
		 setting_tv_updated = (TextView)findViewById(R.id.setting_tv_updated);
		 setting_tv_updated.setTypeface(faceBold);
		 setting_tv_version = (TextView)findViewById(R.id.setting_tv_version);
		 setting_tv_version.setTypeface(faceBold);
		 setting_tv_copyright = (TextView)findViewById(R.id.setting_tv_copyright);
		 setting_tv_copyright.setTypeface(faceBold);
		 int year = Calendar.getInstance().get(Calendar.YEAR);
		 setting_tv_copyright.setText("Copyright © 2006-"+year);
		 
		 setting_tv_mail = (TextView)findViewById(R.id.setting_tv_mail);
		 setting_tv_mail.setTypeface(faceBold);
		 setting_tv_mail1 = (TextView)findViewById(R.id.setting_tv_mail1);
		 setting_tv_mail1.setTypeface(faceBold);
		 
		 setting_tv_time1 = (TextView)findViewById(R.id.setting_tv_time1);
		 setting_tv_time1.setTypeface(faceBold);
		 setting_tv_installed1 = (TextView)findViewById(R.id.setting_tv_installed1);
		 setting_tv_installed1.setTypeface(faceBold);
		 setting_tv_updated1 = (TextView)findViewById(R.id.setting_tv_updated1);
		 setting_tv_updated1.setTypeface(faceBold);
		 setting_tv_version1 = (TextView)findViewById(R.id.setting_tv_version1);
		 setting_tv_version1.setTypeface(faceBold);
		 
		 setting_1 = (ImageView) findViewById(R.id.setting_1);
		 setting_1.setOnClickListener(this);
		 setting_2 = (ImageView) findViewById(R.id.setting_2);
		 setting_2.setOnClickListener(this);
		 setting_3 = (ImageView) findViewById(R.id.setting_3);
		 setting_3.setOnClickListener(this);
		 setting_4 = (ImageView) findViewById(R.id.setting_4);
		 setting_4.setOnClickListener(this);
		 
		 header_name = (TextView)findViewById(R.id.header_name);
		 header_name.setTypeface(faceReg);
		 header_name.setText("Setting");
		 
		 seting_tv_tap = (TextView)findViewById(R.id.seting_tv_tap);
		 seting_tv_tap.setTypeface(faceReg);
		 
		search_main = (ImageView) findViewById(R.id.search_main);
		search_main.setOnClickListener(this);
		search_location = (ImageView) findViewById(R.id.search_location);
		search_location.setOnClickListener(this);
		search_search = (ImageView) findViewById(R.id.search_search);
		search_search.setOnClickListener(this);
			
		 
		 setting_tv_name1 = (TextView)findViewById(R.id.setting_tv_name1);
		 setting_tv_name1.setTypeface(faceBold);
		 setting_tv_city1 = (TextView)findViewById(R.id.setting_tv_city1);
		 setting_tv_city1.setTypeface(faceBold);
		 setting_tv_country1 = (TextView)findViewById(R.id.setting_tv_country1);
		 setting_tv_country1.setTypeface(faceBold);
		 		
		 prefConfi = getApplicationContext()
					.getSharedPreferences("MyPref", MODE_PRIVATE);
		 gjdata = new GetJsonData();
		 
		 setData();
			
		setting_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				tag_setting = 1;
				Intent mIntent = new Intent(getApplicationContext(), Signup.class);
				startActivity(mIntent);
			}
		});
	}
	
	public void setData(){
		//Get Data from shered Pref
		 name= prefConfi.getString("name", "");
		 email = prefConfi.getString("email", "");
		 city = prefConfi.getString("city", "");
		 country = prefConfi.getString("country", "");
		 install_device = prefConfi.getString("currentDateandTime", "");
		 
		 setting_tv_name1.setText(name);
		 setting_tv_city1.setText(city);
		 setting_tv_country1.setText(country);
		 setting_tv_mail1.setText(email);
		 setting_tv_installed1.setText(install_device);
		 
		 //version
		 PackageInfo pInfo = null;
		  try {
		   pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		  } catch (NameNotFoundException e) {
		   e.printStackTrace();
		  }
		 String version = pInfo.versionName;
		 setting_tv_updated1.setText(version);
		 setting_tv_version1.setText(version);
		 
			// Get TimeZone of Device
			TimeZone timezon = TimeZone.getDefault();
			String timezone = timezon.getDisplayName().toString();
			setting_tv_time1.setText(timezone);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences prefConfi;
		 prefConfi = getApplicationContext()
					.getSharedPreferences("MyPrefSave", MODE_PRIVATE);
		 
		switch (v.getId()) {
		
		case R.id.search_main:
			finish();
			
			break;
		
		case R.id.search_search:
			Intent location = new Intent(getApplicationContext(), Search.class);
			startActivity(location);
			finish();

			break;
		case R.id.search_location:
			Intent setting = new Intent(getApplicationContext(), Location.class);
			startActivity(setting);
			finish();

			break;
		case R.id.setting_1:
			
			String save_frq1 = prefConfi.getString("save_radio_frq1", "");
			if(save_frq1.equals("")) {
				NoAlret(1);
			} else {
				RemoveAlret(1, save_frq1);
			}

			break;
		case R.id.setting_2:
			
			String save_frq2 = prefConfi.getString("save_radio_frq2", "");
			if(save_frq2.equals("")) {
				NoAlret(2);
			} else {
				RemoveAlret(2, save_frq2);
			}
			

			break;
		case R.id.setting_3:
			
			String save_frq3 = prefConfi.getString("save_radio_frq3", "");
			if(save_frq3.equals("")) {
				NoAlret(3);
			} else {
				RemoveAlret(3, save_frq3);
			}

			break;
		case R.id.setting_4:
			
			String save_frq4 = prefConfi.getString("save_radio_frq4", "");
			if(save_frq4.equals("")) {
				NoAlret(4);
			} else {
				RemoveAlret(4, save_frq4);
			}
		
			break;

		}
	}
	
	public void RemoveAlret(final int btn,final String frq) {
		 AlertDialog.Builder alertDialogBuilder = new    AlertDialog.Builder(SettingsActivity.this);
		    alertDialogBuilder.setMessage("Frequency "+frq +" removed from "+btn+".");
		    alertDialogBuilder.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface arg0, int arg1) {
		        	
		        	String key_name = "save_radio_frq"+btn;
		        	SharedPreferences prefConfi = getApplicationContext()
		    				.getSharedPreferences("MyPrefSave", MODE_PRIVATE);
		    		Editor editorConfi = prefConfi.edit();
		    		editorConfi.putString(key_name, "");
		    		editorConfi.commit();
		    		arg0.dismiss();
		        }
		    });
		    alertDialogBuilder.setNegativeButton("cancel",
		        new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface arg0, int arg1) {
		        	arg0.dismiss();

		        }
		    });

		    AlertDialog alertDialog = alertDialogBuilder.create();
		    alertDialog.show();
		}
	public void NoAlret(final int btn) {
		 AlertDialog.Builder alertDialogBuilder = new    AlertDialog.Builder(SettingsActivity.this);
		    alertDialogBuilder.setMessage("No frequency set for "+btn+".");
		    alertDialogBuilder.setPositiveButton("Ok",
		        new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface arg0, int arg1) {
		        	
		        	arg0.dismiss();
		        }
		    });
		   

		    AlertDialog alertDialog = alertDialogBuilder.create();
		    alertDialog.show();
		}
	
}
