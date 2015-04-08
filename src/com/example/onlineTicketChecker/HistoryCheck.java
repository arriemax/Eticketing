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
public class HistoryCheck extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "history.php";	// JSON Node names

	
	//nal String TAG_NAME = "name";

	// products JSONArray
	JSONObject json;
	private Context parent;
	private TextView username,credits;
	private ProgressDialog pDialog;
	public Spinner cityCmbo;
	public ArrayAdapter<String> dataAdapter;
	public ArrayList<String> tickets;
	JSONArray user = null;
	int i = 0;
	public HistoryCheck(Context parent) {
				
	            this.parent=parent;        
	}

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(this.parent);
				pDialog.setMessage("Wait.. Content Loading");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				SessionManager session = new SessionManager(this.parent);
				HashMap<String, String> user=session.getUserDetails();
				params.add(new BasicNameValuePair("name", user.get("username")));
				 json = jParser.makeHttpRequest(url, "POST", params);
				try {
					
                  
              	
           
				} catch (Exception e) {
					e.printStackTrace();
				}
				i=1;
				return null;
			}
			
			public JSONObject getTicketJSON()
			{
				while(i==0);
			
			    return json;
			}
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				
				
				pDialog.dismiss();

			}

		

		

	
		
	

}
