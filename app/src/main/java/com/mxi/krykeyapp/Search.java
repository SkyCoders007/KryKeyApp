package com.mxi.krykeyapp;

import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxi.krykeyapp.adapter.CityAdapter;
import com.mxi.krykeyapp.adapter.CountryAdapter;
import com.mxi.krykeyapp.adapter.FrqNameAdapter;
import com.mxi.krykeyapp.adapter.GenreAdapter;
import com.mxi.krykeyapp.adapter.RadioNameAdapter;
import com.mxi.krykeyapp.json.CommonClass;
import com.mxi.krykeyapp.json.GetJsonData;


public class Search extends Activity implements OnClickListener {

	ListView search_listview;
	GetJsonData gjdata;
	ImageView search_name, search_genre, search_city, search_country,
			search_main, search_search, search_location, search_setting;
	int tag_load = 1;
	CommonClass cc;
	TextView header_name;
	Typeface faceReg;

	String method, name_of_object, name_method;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().hide();
		setContentView(R.layout.activity_search);

		gjdata = new GetJsonData();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		search_listview = (ListView) findViewById(R.id.search_listview);
		search_name = (ImageView) findViewById(R.id.search_name);
		search_name.setOnClickListener(this);
		search_genre = (ImageView) findViewById(R.id.search_genre);
		search_genre.setOnClickListener(this);
		search_city = (ImageView) findViewById(R.id.search_city);
		search_city.setOnClickListener(this);
		search_country = (ImageView) findViewById(R.id.search_country);
		search_country.setOnClickListener(this);
		search_main = (ImageView) findViewById(R.id.search_main);
		search_main.setOnClickListener(this);
		search_location = (ImageView) findViewById(R.id.search_location);
		search_location.setOnClickListener(this);
		search_setting = (ImageView) findViewById(R.id.search_setting);
		search_setting.setOnClickListener(this);
		header_name = (TextView) findViewById(R.id.header_name);
		faceReg = Typeface.createFromAsset(this.getAssets(),
				"fonts/champagne_limousines.ttf");
		header_name.setTypeface(faceReg);
		

		
		cc = new CommonClass(getApplicationContext());
		if (tag_load == 1) {

			header_name.setText("SEARCH : RADIO");
			if (GetJsonData.LoadRadioArray.size() == 0) {
				if (cc.isConnectingToInternet())
					new getRadioName().execute();
				else
					Toast.makeText(Search.this,
							"Please connect your internet!", Toast.LENGTH_SHORT)
							.show();
			} else {
				radioname();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_name:
			tag_load = 1;
			header_name.setText("SEARCH : RADIO");
			if (GetJsonData.LoadRadioArray.size() == 0) {
				if (cc.isConnectingToInternet())
					new getRadioName().execute();
				else
					Toast.makeText(Search.this,
							"Please connect your internet!", Toast.LENGTH_SHORT)
							.show();
			} else {
				radioname();
			}

			break;
		case R.id.search_genre:
			tag_load = 2;
			header_name.setText("SEARCH : GENRE");

			if (GetJsonData.LoadGenreArray.size() == 0) {
				if (cc.isConnectingToInternet())
					new getRadioName().execute();
				else
					Toast.makeText(Search.this,
							"Please connect your internet!", Toast.LENGTH_SHORT)
							.show();
			} else {
				genre();
			}

			break;
		case R.id.search_city:
			tag_load = 3;

			header_name.setText("SEARCH : CITY");
			if (GetJsonData.LoadCityArray.size() == 0) {
				if (cc.isConnectingToInternet())
					new getRadioName().execute();
				else
					Toast.makeText(Search.this,
							"Please connect your internet!", Toast.LENGTH_SHORT)
							.show();
			} else {
				city();
			}

			break;
		case R.id.search_country:
			tag_load = 4;
			header_name.setText("SEARCH : COUNTRY");
			if (GetJsonData.LoadCountryArryTeam.size() == 0) {
				if (cc.isConnectingToInternet())
					new getRadioName().execute();
				else
					Toast.makeText(Search.this,
							"Please connect your internet!", Toast.LENGTH_SHORT)
							.show();
			} else {
				country();
			}

			break;
		case R.id.search_main:
			finish();
			
			break;
		
		case R.id.search_location:
			Intent location = new Intent(getApplicationContext(), Location.class);
			startActivity(location);
			finish();

			break;
		case R.id.search_setting:
			Intent setting = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(setting);
			finish();

			break;

		}
	}

	public class getRadioName extends AsyncTask<String, Void, Void> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(Search.this);
			progressDialog.show();
			progressDialog.setContentView(R.layout.custom_progressdialog);
			progressDialog.setCancelable(false);
		}

	

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			switch (tag_load) {
			case 1:
				gjdata.LoadRadio("LoadRadio");
				break;
			case 2:
				gjdata.LoadGenre("LoadGenre");
				break;
			case 3:
				gjdata.LoadCity("LoadCity");

				break;
			case 4:
				gjdata.LoadCountry("LoadCountry");

				break;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();

			switch (tag_load) {
			case 1:
				radioname();
				break;
			case 2:
				genre();

				break;
			case 3:
				city();

				break;
			case 4:
				country();
				break;
			}
		}

	}

	public class getFrq extends AsyncTask<String, Void, Void> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(Search.this);
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

			if(GetJsonData.FindStationByArray.size()==0) {
				Toast.makeText(Search.this, "No radio found", Toast.LENGTH_SHORT).show();
			} else {
				Collections.reverse(GetJsonData.FindStationByArray);
			FrqNameAdapter adapter = new FrqNameAdapter(
					getApplicationContext(), 0, GetJsonData.FindStationByArray);
			search_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			search_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					MediaPlayerDemo_Audio medioPlayer = new MediaPlayerDemo_Audio();
					medioPlayer.stopReadio();
//					Toast.makeText(getApplicationContext(),
//							GetJsonData.FindStationByArray.get(position).radio,
//							1).show();	
					MainActivity.radio_frq = GetJsonData.FindStationByArray.get(position).freq;
					MainActivity.h_name = GetJsonData.FindStationByArray.get(position).radio;
					MainActivity.h_gener = GetJsonData.FindStationByArray.get(position).genre;
					MainActivity.h_city = GetJsonData.FindStationByArray.get(position).city;
					if (GetJsonData.FindStationByArray.get(0).rtmpmic.equals("null") || GetJsonData.FindStationByArray.get(0).rtmpmic.equals("")) {
						MainActivity.rtmp_url = GetJsonData.FindStationByArray.get(0).rtmp_url;
						MainActivity.rtmp_extnsn = "dj1";
					}else {
						MainActivity.rtmp_url = GetJsonData.FindStationByArray.get(0).rtmpmic;
						MainActivity.rtmp_extnsn = "mic1";
					}
					MainActivity.rtmp_id = GetJsonData.FindStationByArray.get(0).id;
					MainActivity.tag_play = "1";
					finish();
				}
			});
			}
		}

	}

	public void radioname() {

		if (GetJsonData.LoadRadioArray.size() != 0) {
			RadioNameAdapter adapter = new RadioNameAdapter(
					getApplicationContext(), 0, GetJsonData.LoadRadioArray);
			search_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			search_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(),
//							GetJsonData.LoadRadioArray.get(position).radio, 1)
//							.show();
					method = "FindStationByRadio";
					name_method = "radioid";
					name_of_object = GetJsonData.LoadRadioArray.get(position).id;
					new getFrq().execute();
				}
			});
		} else {
			Toast.makeText(Search.this, "Please try again later",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void genre() {

		if (GetJsonData.LoadGenreArray.size() != 0) {
			GenreAdapter adapter = new GenreAdapter(getApplicationContext(), 0,
					GetJsonData.LoadGenreArray);
			search_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			search_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(),GetJsonData.LoadGenreArray.get(position).name,
					// 1).show();
					header_name.setText("SEARCH : GENRE, "+GetJsonData.LoadGenreArray.get(position).name.toUpperCase());
					method = "FindStationByGenre";
					name_method = "genre";
					name_of_object = GetJsonData.LoadGenreArray.get(position).name;
					new getFrq().execute();

				}
			});
		} else {
			Toast.makeText(Search.this, "Please try again later",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void country() {
		if (GetJsonData.LoadCountryArryTeam.size() != 0) {
			CountryAdapter adapter = new CountryAdapter(
					getApplicationContext(), 0, GetJsonData.LoadCountryArryTeam);
			search_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			search_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(),
//							GetJsonData.LoadCountryArry.get(position).country,
//							1).show();
					method = "FindStationByCountry";
					name_method = "country";
					name_of_object = GetJsonData.LoadCountryArryTeam.get(position).country;
					header_name.setText("SEARCH : COUNTRY, "+GetJsonData.LoadCountryArryTeam.get(position).country.toUpperCase());
					new getFrq().execute();
				}
			});
		} else {
			Toast.makeText(Search.this, "Please try again later",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void city() {

		if (GetJsonData.LoadCityArray.size() != 0) {
			CityAdapter adapter = new CityAdapter(getApplicationContext(), 0,
					GetJsonData.LoadCityArray);
			search_listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			search_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(),
//							GetJsonData.LoadCityArray.get(position).city, 1)
//							.show();
					method = "FindStationByCity";
					name_method = "city";
					name_of_object = GetJsonData.LoadCityArray.get(position).city;
					header_name.setText("SEARCH : CITY, "+GetJsonData.LoadCityArray.get(position).city.toUpperCase());
					new getFrq().execute();
				}
			});
		} else {
			Toast.makeText(Search.this, "Please try again later",
					Toast.LENGTH_SHORT).show();
		}

	}

}
