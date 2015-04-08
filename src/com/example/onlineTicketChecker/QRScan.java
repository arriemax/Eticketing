package com.example.onlineTicketChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentResult;
public class QRScan extends Activity
{
	Button scanTicket;
	Button searchTicket,myaccount;
	Boolean enter,leave,notvalid;
	
	HashMap<String, String> userDetail;
	JSONParser jParser = new JSONParser();
    HashMap<String, String> HistoryDetail;
  
    TextView ticket_id,name,noft,date,source,destination,city,status;
    String qr_uid,qr_ticket_id,qr_name,qr_noft,qr_date,qr_source,qr_destination,qr_city;
    int qr_flag,qr_validity;
    
    

    // url to get all products list
    private static String url = "ticketdetail.php";	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		ticket_id=(TextView)findViewById(R.id.qr_ticketid);
		name=(TextView)findViewById(R.id.qr_username);
		noft=(TextView)findViewById(R.id.qr_noft);
		date=(TextView)findViewById(R.id.qr_date);
		source=(TextView)findViewById(R.id.qr_source);
		destination=(TextView)findViewById(R.id.qr_destination);
		city=(TextView)findViewById(R.id.qr_city);
		status=(TextView)findViewById(R.id.qr_status);
		enter=false;
		leave=false;
		notvalid=false;
		
		setContentView(R.layout.qrscan);
		new getDetail().execute();
	}
	
	
	
	

	
	
	
	
	// Event on buttons click
	

	
	
	
	// for deatil of ticket
	
	
	 /**
     * Background Async Task to Load all product by making HTTP Request
     * */
	
	
	 /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class getDetail extends AsyncTask<String, String, String>  {
 
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
			SessionManager session = new SessionManager(QRScan.this);
			HashMap<String, String> user=session.getUserDetails();
			
			params.add(new BasicNameValuePair("ticket_id",getIntent().getStringExtra("scancontent"))); 
			Log.d("scancontent",getIntent().getStringExtra("scancontent"));
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);
			
			
			Log.d("account: ", json.toString());
            try {
             
            	
            	HistoryDetail = new HashMap<String,String>();
				JSONArray details= json.getJSONArray("ticket_detail");
		        for(int i = 0; i < details.length(); i++){
		            
		        	
		        	//tickets Details 
		        	   qr_ticket_id = details.getJSONObject(i).getString("ticket_id");
			            qr_source = details.getJSONObject(i).getString("source");
			            qr_destination = details.getJSONObject(i).getString("destination");
			            qr_city = details.getJSONObject(i).getString("city");
			            qr_date = details.getJSONObject(i).getString("journey_date");
			            qr_noft = details.getJSONObject(i).getString("no_of_passengers");
			            qr_flag = details.getJSONObject(i).getInt("flag");
			            qr_validity = details.getJSONObject(i).getInt("validity");
			            qr_uid = details.getJSONObject(i).getString("uid");
			            qr_name = details.getJSONObject(i).getString("username");
			          
			            
			            
			            
			            
			            
			       
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
           
          
           
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                 
                	GPSTracker  gps = new GPSTracker(QRScan.this);
                	// gps.showSettingsAlert();
	                // check if GPS enabled    
	                if(gps.canGetLocation()){
	                     
	                    double latitude = gps.getLatitude();
	                    double longitude = gps.getLongitude();
	                    
	                   
	                    Log.d("hgsjhdfi",""+qr_flag);
	                    
	                    if(qr_source.equalsIgnoreCase(qr_source) && qr_flag==0 && qr_validity==1 )
	                      {
	                       enter=true;
	                       new setFlag().execute();
	                       }
	                    else if(qr_destination.equalsIgnoreCase(qr_destination)  && qr_flag==1  && qr_validity==1)
	                    {
	                    	
	                    	// calculate time between source and destination 
	                    	int totaltime=2;
	                    	int spendtime=3;
	                    	int time_lag=4;
	                    	
	                    	if(spendtime<totaltime+time_lag)
	                    	{  leave=true;
	                    		new validation().execute();
	                    		
	                    	}
	                    	else
	                    	{    
	                    		notvalid=true;
	                    		/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QRScan.this);
	         			   alertDialogBuilder.setTitle("Information");
	        			   
	       				// set dialog message
	       				alertDialogBuilder
	       					.setMessage("Your Ticket Validation Experied ")
	       					.setCancelable(false)
	       					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	       						public void onClick(DialogInterface dialog,int id) {
	       							// if this button is clicked, close
	       							// current activity
	       							//MainActivity.this.finish();
	       						}
	       					  });
	       				AlertDialog alertDialog = alertDialogBuilder.create();
	       				 */
	       			/*	// show it
	       				alertDialog.show();
	                    		ticket_id.setText(qr_ticket_id);
		  	                    name.setText(qr_name);
		  	                    noft.setText(qr_noft);
		  	                    date.setText(qr_date);
		  	                    source.setText(qr_source);
		  	                    destination.setText(qr_destination);
		  	                    city.setText(qr_city);
		  	                    status.setText("In Valid Ticket ");
		  	                    status.setTextColor(330000);
		  	                    */
	                    	}
	                    	
	                    }
	                  
	                    
	                    
	                    
	                    // \n is for new line
	                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
	                }else{
	                    // can't get location
	                    // GPS or Network is not enabled
	                
	                    gps.showSettingsAlert();
	                }
                	
                	
                }
            });
         
            
            
            
            if(enter)
            {     

          	   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QRScan.this);
 			   alertDialogBuilder.setTitle("Information");
 			   
 				// set dialog message
 				alertDialogBuilder
 					.setMessage(qr_noft +" User Allowed to Enter ")
 					.setCancelable(false)
 					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 						public void onClick(DialogInterface dialog,int id) {
 							QRScan.this.finish();
 						}
 					  });
 				AlertDialog alertDialog = alertDialogBuilder.create();
 				 
 				// show it
 				alertDialog.show();
             	
//            	   ticket_id.setText(qr_ticket_id);
//                   name.setText(qr_name);
//                   noft.setText(qr_noft);
//                   date.setText(qr_date);
//                   source.setText(qr_source);
//                   destination.setText(qr_destination);
//                   city.setText(qr_city);
//                   status.setText("Valid Ticket");
//                   status.setTextColor(34455);
            }	
            else if(leave)
            {
            	
            	
//      	   ticket_id.setText(qr_ticket_id);
//                name.setText(qr_name);
//                noft.setText(qr_noft);
//               date.setText(qr_date);
//                source.setText(qr_source);
//                destination.setText(qr_destination);
//                city.setText(qr_city);
//                status.setText("Valid Ticket ");
//                status.setTextColor(34455);
        	
        	
    	   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QRScan.this);
		   alertDialogBuilder.setTitle("Information");
		   
			// set dialog message
			alertDialogBuilder
				.setMessage(qr_noft +" User Allowed to leave ")
			.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
					QRScan.this.finish();
					}
				  });
			AlertDialog alertDialog = alertDialogBuilder.create();
			 
			// show it
			alertDialog.show();
            }
            else if (notvalid)
            {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QRScan.this);
			   alertDialogBuilder.setTitle("Information");
        	alertDialogBuilder
				.setMessage("Your Ticket Validation Experied ")
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						QRScan.this.finish();
					}
				  });
			AlertDialog alertDialog = alertDialogBuilder.create();
			 
		
			alertDialog.show();
			
//			ticket_id.setText(qr_ticket_id);
//            name.setText(qr_name);
//            noft.setText(qr_noft);
//           date.setText(qr_date);
//            source.setText(qr_source);
//            destination.setText(qr_destination);
//            city.setText(qr_city);
//            status.setText("InValid Ticket ");
//            status.setTextColor(34455);
			
			
			
            }
            
        }
    }
 
    
    class setFlag extends AsyncTask<String, String, String>  {
    	 
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
			SessionManager session = new SessionManager(QRScan.this);
			HashMap<String, String> user=session.getUserDetails();
			String url1="setflag.php";
			params.add(new BasicNameValuePair("ticket_id",getIntent().getStringExtra("scancontent"))); 
			Log.d("scancontent",getIntent().getStringExtra("scancontent"));
			JSONObject json = jParser.makeHttpRequest(url1, "POST", params);
			
			
			Log.d("setflag: ", json.toString());
            
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
           
          
           
            runOnUiThread(new Runnable() {
                public void run() {
                    
                 
                	
                }
            });
 
        }
    }
    
    class validation extends AsyncTask<String, String, String>  {
   	 
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
			SessionManager session = new SessionManager(QRScan.this);
			HashMap<String, String> user=session.getUserDetails();
			String url2="validation.php";
			params.add(new BasicNameValuePair("ticket_id",getIntent().getStringExtra("scancontent"))); 
			Log.d("scancontent",getIntent().getStringExtra("scancontent"));
			JSONObject json = jParser.makeHttpRequest(url2, "POST", params);
			
			
			Log.d("validation: ", json.toString());
            try {
             
            	
            	int success = json.getInt("success");
				String msg =json.getString("message");
			   
		      
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
           
          
           
            runOnUiThread(new Runnable() {
                public void run() {
                    
                 

                	
                	
                }
            });
 
        }
    }
  }
