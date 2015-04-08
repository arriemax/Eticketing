package com.example.onlineTicketChecker;

//library uesd by this class
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Context;


public class BookTicketPHP extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "bookTicket.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	//nal String TAG_NAME = "name";

	// products JSONArray
	private EditText username,password,name,address,phone,dob,email;
	private Context parent;
	String credits;
	private ProgressDialog pDialog;
	String city,source,destination,noft,data;
	JSONArray user = null;
	int success=0;
	int i=0;


			public BookTicketPHP(Context applicationContext, String city,String source, String destination, String noft) {
		   // TODO Auto-generated constructor stub
	           this.parent=applicationContext;
	           this.city=city;
	           this.source=source;
	           this.destination=destination;
	           this.noft=noft;
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
				params.add(new BasicNameValuePair("city", this.city));
	            params.add(new BasicNameValuePair("source", this.source));
	            params.add(new BasicNameValuePair("destination",this.destination));
	            params.add(new BasicNameValuePair("noft", this.noft));
	            SessionManager session = new SessionManager(this.parent);
				HashMap<String, String> p=session.getUserDetails();
				params.add(new BasicNameValuePair("name", p.get("username")));
	         
	           
	          
	          
				// getting JSON string from URL
			Log.d("check","i m here ");
			
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				
				Log.d("login: ", json.toString());

				try {
					success=json.getInt("success");
					Log.d("login: ", success + "ds");
					if(success==1)
						{JSONArray details= json.getJSONArray("ticketdetail");
			        for(int i = 0; i < details.length(); i++){
			            Log.e("in", "msg");
			        	this.data = details.getJSONObject(i).getString("ticket_id");
			        }
					credits  = json.getString("credits");
                    Log.d("credits:",credits);
						}
					else
					{
						 credits  = json.getString("credits");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
                i=1;
				return null;
			}
			public String getData()
			{
				while(i==0);
				return this.data;
			}
			
			public String getCredits()
			{
				while(i==0);
				return credits;
			}
			public int getSuccess()
			{
				while(i==0);
				return success;
			}
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog after getting all products
				
				

				
			}

		

		

	
		
	

}
