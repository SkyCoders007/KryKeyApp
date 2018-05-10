package com.mxi.krykeyapp.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.mxi.krykeyapp.R;
import com.mxi.krykeyapp.bean.FindStationBy;
import com.mxi.krykeyapp.bean.Frequency;
import com.mxi.krykeyapp.bean.LoadCity;
import com.mxi.krykeyapp.bean.LoadCountry;
import com.mxi.krykeyapp.bean.LoadCountryTeam;
import com.mxi.krykeyapp.bean.LoadGenre;
import com.mxi.krykeyapp.bean.LoadRadio;


public class GetJsonData {

    // Ref. from http://programmerguru.com/
    private final String NAMESPACE = "http://tempuri.org/";
    private final String URL = "http://www.krykey.com/krykey.asmx";
    private String SOAP_ACTION = "http://tempuri.org/";
    private String METHOD_NAME;

    public static ArrayList<LoadCountry> LoadCountryArry = new ArrayList<LoadCountry>();
    public static ArrayList<LoadCountryTeam> LoadCountryArryTeam = new ArrayList<LoadCountryTeam>();

    public static ArrayList<FindStationBy> FindStationByArray = new ArrayList<FindStationBy>();
    public static ArrayList<LoadCity> LoadCityArray = new ArrayList<LoadCity>();
    public static ArrayList<Frequency> frequency = new ArrayList<Frequency>();
    public static ArrayList<LoadRadio> LoadRadioArray = new ArrayList<LoadRadio>();
    public static ArrayList<LoadGenre> LoadGenreArray = new ArrayList<LoadGenre>();


    public void LoadCountry(String method) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            LoadCountryArry.clear();
            LoadCountryArryTeam.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                LoadCountry mal = new LoadCountry();
                JSONObject c = jsonArray.getJSONObject(i);
                mal.country = c.getString("country");
                LoadCountryArry.add(mal);
            }

            TimeZone timezon = TimeZone.getDefault();
            String timezone = timezon.getDisplayName().toString();
            String[] separated = timezone.split(" ");

//			LoadCountry mal = LoadCountryArry.get(i);
            for (int i = 0; i < LoadCountryArry.size(); i++) {
                if (separated[0].equals(LoadCountryArry.get(i).country)) {
                    LoadCountryTeam mal1 = new LoadCountryTeam();
                    mal1.country = LoadCountryArry.get(i).country;
                    LoadCountryArryTeam.add(mal1);
                }
            }
            for (int i = 0; i < LoadCountryArry.size(); i++) {
                if (!separated[0].equals(LoadCountryArry.get(i).country)) {
                    LoadCountryTeam mal1 = new LoadCountryTeam();
                    mal1.country = LoadCountryArry.get(i).country;
                    LoadCountryArryTeam.add(mal1);
                }
            }

//			Log.e("LoadCountryArry", LoadCountryArry.size()+"");
        } catch (Exception e) {
//			Log.e("LoadCountry", e.getMessage());
        }
    }


    public void FindStationBy(String method, String object, String name) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName(name);
        celsiusPI.setValue(object);
        celsiusPI.setType(double.class);
        request.addProperty(celsiusPI);
//		Log.e("Logg", name+" "+method);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            FindStationByArray.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                FindStationBy mal = new FindStationBy();
                JSONObject c = jsonArray.getJSONObject(i);
                mal.id = c.getString("id");
                mal.rtmp_url = c.getString("rtmp");
                //mal.freq = c.getString("freq");

                if (c.getString("freq").contains(".")) {
                    mal.freq = c.getString("freq");
                } else {
                    String f = c.getString("freq");
                    f = f + ".0";
                    mal.freq = f;
                }

                mal.radio = c.getString("radio");
                mal.genre = c.getString("genre");
                mal.ranking = Integer.parseInt(c.getString("ranking"));
                mal.city = c.getString("city");
                mal.rtmpmic = c.getString("rtmpmic");
                FindStationByArray.add(mal);
                Collections.sort(FindStationByArray);
            }
//			Log.e("FindStationByCountryArray", FindStationByArray.size()+"");
        } catch (Exception e) {
//			Log.e("FindStationByCountry", e.getMessage());
        }
    }

    public void LoadCity(String method) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            LoadCityArray.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                LoadCity mal = new LoadCity();
                JSONObject c = jsonArray.getJSONObject(i);
                mal.city = c.getString("city");
                LoadCityArray.add(mal);
            }
//			Log.e("LoadCityArray", LoadCityArray.size()+"");
        } catch (Exception e) {
//			Log.e("LoadCity", e.getMessage());
        }
    }

    public void LoadRadio(String method) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            LoadRadioArray.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                LoadRadio mal = new LoadRadio();
                JSONObject c = jsonArray.getJSONObject(i);
                mal.id = c.getString("id");
                mal.radio = c.getString("radio");
                LoadRadioArray.add(mal);
            }
//			Log.e("LoadRadioArray", LoadRadioArray.size()+"");
        } catch (Exception e) {
//			Log.e("LoadRadio", e.getMessage());
        }
    }

    public void LoadGenre(String method) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            LoadGenreArray.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                LoadGenre mal = new LoadGenre();
                JSONObject c = jsonArray.getJSONObject(i);
                mal.id = c.getString("id");
                mal.name = c.getString("name");
                LoadGenreArray.add(mal);
            }
//			Log.e("LoadGenreArray", LoadGenreArray.size()+"");
        } catch (Exception e) {
            Log.e("LoadGenre", e.getMessage());
        }
    }

    public String SaveUserData(String name, String email, String city,
                               String country, String timezone, String devicetype, String devicemodel,
                               String osversion) {
        String res = null;
        SOAP_ACTION = "http://tempuri.org/SaveUser";
        METHOD_NAME = "SaveUser";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(name);
        celsiusPI.setName("email");
        celsiusPI.setValue(email);
        celsiusPI.setName("city");
        celsiusPI.setValue(city);
        celsiusPI.setName("country");
        celsiusPI.setValue(country);
        celsiusPI.setName("timezone");
        celsiusPI.setValue(timezone);
        celsiusPI.setName("devicetype");
        celsiusPI.setValue(devicetype);
        celsiusPI.setName("devicemodel");
        celsiusPI.setValue(devicemodel);
        celsiusPI.setName("osversion");
        celsiusPI.setValue(osversion);

        celsiusPI.setType(double.class);
        request.addProperty(celsiusPI);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();
            res = jsonStr;
//			Log.e("FindStationByGenreArray", FindStationByGenreArray.size()+"");

        } catch (Exception e) {
            Log.e("FindStationByGenre", e.getMessage());
        }
        return res;
    }

    public void GetAllFrequencyList(String method) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            String jsonStr = response.toString();

            // Parse JSON String
            frequency.clear();
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                Frequency mal = new Frequency();
                JSONObject c = jsonArray.getJSONObject(i);

                Log.e("Freq", c.getString("freq"));
                if (c.getString("freq").contains(".")) {
                    mal.freq = c.getString("freq");
                } else {
                    String f = c.getString("freq");
                    mal.freq = f + ".0";
                }
                frequency.add(mal);
            }
//			Log.e("GetAllFrequencyList", frequency.size()+"");
        } catch (Exception e) {
//			Log.e("LoadCity", e.getMessage());
        }
    }

    public String UpdateUserData(String name, String email, String city,
                                 String country, String timezone, String membershipkey) {
        try {
            String res = null;
            SOAP_ACTION = "http://tempuri.org/UpdateUser";
            METHOD_NAME = "UpdateUser";
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo celsiusPI = new PropertyInfo();
            celsiusPI.setName("name");
            celsiusPI.setValue(name);
            celsiusPI.setName("email");
            celsiusPI.setValue(email);
            celsiusPI.setName("city");
            celsiusPI.setValue(city);
            celsiusPI.setName("country");
            celsiusPI.setValue(country);
            celsiusPI.setName("timezone");
            celsiusPI.setValue(timezone);
            celsiusPI.setName("membershipkey");
            celsiusPI.setValue(membershipkey);


            celsiusPI.setType(double.class);
            request.addProperty(celsiusPI);

            //Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                //Get the response
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String jsonStr = response.toString();
                res = jsonStr;
//			Log.e("FindStationByGenreArray", FindStationByGenreArray.size()+"");


            } catch (Exception e) {
                Log.e("UpdateUserData", e.getMessage());
            }
            return res;
        } catch (Exception e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
        }
        return null;
    }

    public void SaveListen(String method, String song_id, String member_id) {
        SOAP_ACTION = "http://tempuri.org/" + "" + method;
        METHOD_NAME = method;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("id");
        celsiusPI.setValue(song_id);
        celsiusPI.setName("membershipkey");
        celsiusPI.setValue(member_id);
        celsiusPI.setType(double.class);
        request.addProperty(celsiusPI);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            try {
                String jsonStr = response.toString();
//				Log.e("SaveListen", jsonStr+"");
            } catch (Exception e) {
                // TODO Auto-generated catch block

            }
        } catch (Exception e) {

        }
    }

    public ArrayList<String> getCountry(Context mContext) {

        String country = mContext.getResources().getString(R.string.country);
        ArrayList<String> cont = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(country);
            JSONArray jsonArray = jsonObject.getJSONArray("country");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                cont.add(jsonObject1.getString("name") + " (" + jsonObject1.getString("code") + ")");

            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cont;

    }

}
