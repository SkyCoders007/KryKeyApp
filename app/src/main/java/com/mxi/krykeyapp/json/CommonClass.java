package com.mxi.krykeyapp.json;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonClass {

	private  Context _context;

	public CommonClass(Context context) {
		this._context = context;
	}

	 public  boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	 
	 public String getCurrentDate(){

			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, 0);
			dt = c.getTime();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			String nowDate = formatter.format(dt);
			return nowDate;
			 
		 }
		 
		 public String getdateAfterThreeMonths(){
			 
			 Date dt = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(dt);
				c.add(Calendar.DATE, 90);
				dt = c.getTime();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				String nowDate = formatter.format(dt);
				return nowDate;
			 
		 }

}
