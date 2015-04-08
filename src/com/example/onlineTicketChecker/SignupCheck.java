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
import android.widget.Toast;
import android.content.Context;


public class SignupCheck extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "signup.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	//nal String TAG_NAME = "name";

	// products JSONArray
	private String credits;
	private EditText username,password,name,address,phone,dob,email;
	private Context parent;
	private ProgressDialog pDialog;
	Activity mActivity;
	JSONArray user = null;
	Boolean error=false;
	public SignupCheck(Activity activity,Context parent, EditText username,EditText password, EditText name, EditText dob, EditText address, EditText email, EditText phone,String credits) {
				
	            this.parent=parent;
	            this.username=username;
	            this.password=password;
	            this.name=name;
	            this.email=email;
	            this.phone=phone;
	            this.dob=dob;
	            this.address=address;
	            this.mActivity=activity;
				this.credits=credits;
				
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
				params.add(new BasicNameValuePair("username", this.username.getText().toString()));
	            params.add(new BasicNameValuePair("password", this.password.getText().toString()));
	            params.add(new BasicNameValuePair("name",this.name.getText().toString()));
	            params.add(new BasicNameValuePair("email", this.email.getText().toString()));
	            params.add(new BasicNameValuePair("dob", this.dob.getText().toString()));
	            params.add(new BasicNameValuePair("address", this.address.getText().toString()));
	            params.add(new BasicNameValuePair("phone", this.phone.getText().toString()));
	            params.add(new BasicNameValuePair("credits", this.credits));
	            Log.d("phone",this.phone.getText().toString());
	          
	          
				// getting JSON string from URL
			Log.d("check","i m here ");
			
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				
				Log.d("login: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);
					String msg =json.getString("message");
                    
					if (success == 1) {
						
				        // Session Manager
				        SessionManager session = new SessionManager(this.parent);
				        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(this.username.getText().toString());
                        HashMap<String, String> user=session.getUserDetails();
        				Log.d("name", user.get("username"));
        	           
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
				

				pDialog.dismiss();
				if(error)
				{
					
					Toast.makeText(this.parent,"ooops! an Error Occured", Toast.LENGTH_LONG).show();
				}

			}

		

		

	
		
	

}
