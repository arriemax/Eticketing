package com.example.onlineTicketChecker;

//library uesd by this class
import java.io.ByteArrayOutputStream;
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
import android.util.Base64;
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
import android.graphics.Bitmap;


public class QRCodePHP extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "qrcode.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	//nal String TAG_NAME = "name";

	// products JSONArray
	private EditText username,password,name,address,phone,dob,email;
	private Context parent;
	String credits;
	private ProgressDialog pDialog;
	String city,source,destination,noft,data;
	Bitmap qrcode;
	JSONArray user = null;
	int i=0;


			public QRCodePHP(Context parent,Bitmap  qrcode) {
		   // TODO Auto-generated constructor stub
	           this.parent=parent;
	           this.qrcode=qrcode;
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
				
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
				this.qrcode.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
				byte[] byteArray = byteArrayOutputStream .toByteArray();
				
		       
				String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
				
				 
				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("qrcode",encoded));
	            SessionManager session = new SessionManager(this.parent);
				HashMap<String, String> p=session.getUserDetails();
				params.add(new BasicNameValuePair("name", p.get("username")));
	         
	           
	          
	          
				// getting JSON string from URL
			Log.d("check","i m in Qrcodr pgp ");
			
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				this.data=json.toString();
				Log.d("login: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					 credits  = json.getString("sucess");
                    Log.d("credits:",credits);
					
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
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog after getting all products
				
				

				
			}

		

		

	
		
	

}

