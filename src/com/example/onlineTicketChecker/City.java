package com.example.onlineTicketChecker;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
public class City extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "city.php";	// JSON Node names
	private static final String TAG_CITY = "city";
	
	//nal String TAG_NAME = "name";

	// products JSONArray
	
	private Context parent;
	private TextView username,credits;
	private ProgressDialog pDialog;
	public Spinner cityCmbo;
	public ArrayAdapter<String> dataAdapter;
	public List<String> cities;
	JSONArray user = null;
	int i = 0;
	public City(Context parent, Spinner cityCmbo) {
				
	            this.parent=parent;
	            this.cityCmbo=cityCmbo;	
	            System.out.println(this.cityCmbo.getId());
	}

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				
				Log.d("account: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					
                  JSONArray city= json.getJSONArray(TAG_CITY);
                  cities = new ArrayList<String>();
                JSONObject cityobj =null;
                  for(int i=0;i<city.length();i++)
                  {
                	  cityobj=city.getJSONObject(i);
                	 
                	  cities.add(cityobj.getString("city"));
                	  Log.d("cities",cities.get(i));
                	  // Creating adapter for spinner
                	
                  }
                  
//                   dataAdapter = new ArrayAdapter<String>(this.parent,android.R.layout.simple_spinner_item, cities);
//                  
//                  // Drop down layout style - list view with radio button
//                  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//           
//                  // attaching data adapter to spinner
//                  this.cityCmbo.setAdapter(dataAdapter);
           
				} catch (Exception e) {
					e.printStackTrace();
				}
				i=1;
				return null;
			}
			
			public List<String> getCities()
			{
				while(i==0);
				return cities;
			}
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				
				
				

			}

		

		

	
		
	

}
