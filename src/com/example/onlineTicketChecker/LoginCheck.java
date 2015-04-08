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

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class LoginCheck extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String login_url = "login.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	//nal String TAG_NAME = "name";

	// products JSONArray
	private TextView username,password;
	private Context parent;
	ProgressDialog pDialog ;
	JSONArray user = null;
	Activity mActivity;
    Boolean error=false;
	public LoginCheck(Activity activity,Context parent, TextView username,TextView password) {
	            this.parent=parent;
	            this.username=username;
	            this.password=password;
	            this.mActivity=activity;
				
				
	}

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				
				super.onPreExecute();
			
				
				
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", args[0]));
	            params.add(new BasicNameValuePair("pass", args[1]));
	          
				// getting JSON string from URL
		     	Log.d("check","i m here ");
				JSONObject json = jParser.makeHttpRequest(login_url, "POST", params);
				
				
				Log.d("login: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);
					String msg =json.getString(TAG_MESSAGE);
                    
					if (success == 1) {
						
				        // Session Manager
				        SessionManager session = new SessionManager(this.parent);
				        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(args[0]);
                        if(session.isLoggedIn())
                        {
						Intent i = new Intent(this.parent, Home.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						this.parent.startActivity(i);
                        this.mActivity.finish();
                       
						
                        }
                        else
                        {
                        	Intent i = new Intent(this.parent, MainActivity.class);
    						this.parent.startActivity(i);
    						
                        	
                        }
						// closing this screen
						
						
					} else {
					
						
						error=true;
				
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog after getting all products
				
				 if(error)
				 {
					 Toast.makeText(this.parent, "Wrong Username or Password", Toast.LENGTH_LONG).show();
				 }

			}

		

		

	
		
	

}
