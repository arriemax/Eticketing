package com.example.onlineTicketChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends Activity
{
	 private ProgressDialog pDialog;
	    TextView ticketcity,date,noft,source,destination,id;
	    ImageView qrcode;
	    // Creating JSON Parser object
	    JSONParser jParser = new JSONParser();
	    HashMap<String, String> HistoryDetail;
	    RadioGroup radio;
	 
	    // url to get all products list
	    private static String url = "recharge.php";	// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
		public static final String KEY_USERNAME = "username";
	 
	    // JSON Node names
	 
	    private static final String TAG_PRODUCTS = "products";
	    private static final String TAG_PID = "pid";
	    private static final String TAG_NAME = "name";
	 
	    // products JSONArray
	    JSONArray products = null;

	Button chngPass,logout,creditRecharge;
	TextView username;
	TextView credits,name;
	HashMap<String, String> userDetail;
   String rechargecredits,rechargeamount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		setContentView(R.layout.myaccount);
		username=(TextView)findViewById(R.id.myaccount_usernametxt);
		credits = (TextView)findViewById(R.id.myaccount_creditstxt);
		name = (TextView)findViewById(R.id.myaccount_nametxt);
		AccountCheck a =new AccountCheck(this, username, credits);
		a.execute();
		userDetail =a.getAccountInfo();
		name.setText(userDetail.get("name").toUpperCase());
		credits.setText(userDetail.get("credits").toString());
		SessionManager session = new SessionManager(getApplicationContext());
		HashMap<String, String> user =session.getUserDetails();
		username.setText(user.get("username"));
		addListnerOnButton();
	}
	
	
	public void addListnerOnButton(){
	
	 
	chngPass=(Button)findViewById(R.id.myaccount_chnagepassword);
	logout=(Button)findViewById(R.id.myaccount_logout); 
    creditRecharge=(Button)findViewById(R.id.creditrecharge);
    radio=(RadioGroup)findViewById(R.id.A_radioCredits);
	 //on signupbutton click this method execute

	 
	 //on refreshbutton click this method execute
	 chngPass.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			
				
			}
		});
	
	 
	 logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				 SessionManager session=new SessionManager(getApplicationContext());
				session.logoutUser();
				 
				
			}
		});
	
	
	 creditRecharge.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 if(radio.getCheckedRadioButtonId()!=-1){
				RadioButton selectRadio = (RadioButton) findViewById(radio.getCheckedRadioButtonId());
				rechargeamount = selectRadio.getText().toString();
	 
	            Toast.makeText(getApplicationContext(), "Your Selected amount is : " + rechargeamount,Toast.LENGTH_LONG).show();

				
				 new LoadDatad().execute();
				 
				 }
				 else{
					 Toast.makeText(getApplicationContext(), "Please select an amount" + rechargeamount,Toast.LENGTH_LONG).show();

				 }
			}
		});
}
	
    class LoadDatad extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyAccount.this);
            pDialog.setMessage("Recharging...  Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
			SessionManager session = new SessionManager(MyAccount.this);
			HashMap<String, String> user=session.getUserDetails();
			params.add(new BasicNameValuePair("name", user.get("username").toString()));
			params.add(new BasicNameValuePair("credits",rechargeamount));
          
          
			// getting JSON string from URL
		
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);
			
			
			Log.d("account: ", json.toString());
            try {
               
            	
            	
            	HistoryDetail = new HashMap<String,String>();
				JSONArray details= json.getJSONArray("recharge");
		        for(int i = 0; i < details.length(); i++){
		            
		        	rechargecredits = details.getJSONObject(i).getString("credits");
						Log.d("Rechage Amount", rechargeamount);
		
			           
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	credits.setText(rechargecredits);
                	   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyAccount.this);
        			   alertDialogBuilder.setTitle("A/C Information");
        			   
        				// set dialog message
        				alertDialogBuilder
        					.setMessage("Recharge Successful \n Your Main Credit is : "+rechargecredits)
        					.setCancelable(false)
        					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
        						public void onClick(DialogInterface dialog,int id) {
        							// if this button is clicked, close
        							// current activity
        							//MainActivity.this.finish();
        						}
        					  });
        				AlertDialog alertDialog = alertDialogBuilder.create();
        				 
        				// show it
        				alertDialog.show();
                	
                }
            });
 
        }
 
    }
	
}
